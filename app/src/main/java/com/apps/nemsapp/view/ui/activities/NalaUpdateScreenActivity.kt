package com.apps.nemsapp.view.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apps.nemsapp.BuildConfig
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.ActivityNalaUpdateScreenBinding
import com.apps.nemsapp.view.base.BaseActivity
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.viewmodel.StatusSubmitViewModel
import com.apps.nemsapp.viewmodel.StatusViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.zxy.tiny.Tiny
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class NalaUpdateScreenActivity : BaseActivity(), View.OnClickListener {


    //map
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null
    private val REQUEST_CHECK_SETTINGS = 0x1
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 100
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2


    private lateinit var binding: ActivityNalaUpdateScreenBinding
    var statusId: String? = null
    private lateinit var statusViewModel: StatusViewModel

    private var statusTypeArray: ArrayList<String> = ArrayList()
    private var statusIdArray: ArrayList<String> = ArrayList()


    private val cameraRequestCode = 100
    private val directoryName = "NEMS"
    private var latt = ""
    private var longg = ""
    private var outFile: File? = null
    private val multiplePermission = 101
    private lateinit var statusSubmitViewModel: StatusSubmitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNalaUpdateScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPreference(SharedPreferConstant.IMAGE_PATH, "")
        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)
        statusSubmitViewModel = ViewModelProvider(this).get(StatusSubmitViewModel::class.java)

        //map
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        mSettingsClient = LocationServices.getSettingsClient(applicationContext)
        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
        startLocationUpdates()
        updateUI()

        //binding.dateTimeTxt.text = "Date & Time : " + dateTime
        binding.statusNala.text = getPreference(SharedPreferConstant.nalaStatus)
        binding.photoClickLayout.setOnClickListener(this)
        binding.formSubBut.setOnClickListener(this)
        binding.locationCaptureLayout.setOnClickListener(this)
        binding.backButton.setOnClickListener {
            setPreference(SharedPreferConstant.IMAGE_PATH, "")
            finish()
        }
        binding.nalaTxt.text = getPreference(SharedPreferConstant.nalaName)

        if (isNetworkAvailable()) {
            getStatusData()
        } else {
            showToastMessage("Please Connect internet...............")
        }

        statusViewModel.statusLiveData.observe(this, Observer {
            if (it.statusCode == 200) {
                statusTypeArray.clear()
                statusIdArray.clear()
                if (it.data.size > 0) {
                    for (i in it.data.indices) {
                        statusTypeArray.add(it.data[i].statusDesc)
                        statusIdArray.add(it.data[i].id.toString())
                    }

                    val categoryAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        applicationContext,
                        android.R.layout.simple_dropdown_item_1line,
                        statusTypeArray
                    )
                    binding.spinnerStatus.adapter = categoryAdapter
                }
            } else {

            }
            dismissDialog()
        })


        binding.spinnerStatus.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                statusId = statusIdArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        statusSubmitViewModel.dashLiveData.observe(this, Observer {
            if (it.status_code == 200) {
                pop(it.status_message)
            } else {
                showToastMessage(it.status_message)
            }
            dismissDialog()
        })
        statusSubmitViewModel.messageShow.observe(this, Observer {
            showToastMessage(it)
            dismissDialog()
        })

    }


    private fun pop(msg: String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(msg)
        alertDialogBuilder.setPositiveButton("Ok",
            DialogInterface.OnClickListener { arg0, arg1 ->
                arg0.dismiss()
                resetForm()
            })
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun resetForm() {
        viewModelStore.clear()
        setPreferLogin(SharedPreferConstant.LATTITUDE, "")
        setPreferLogin(SharedPreferConstant.LONGITUDE, "")
        val intent = Intent(this@NalaUpdateScreenActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun getStatusData() {
        showDialog()
        statusViewModel.getStatusData(
            getPreferLogin(SharedPreferConstant.user_id).toString(),
            getPreferLogin(SharedPreferConstant.ulbId).toString(), BuildConfig.VERSION_CODE
        )
    }


    override fun onClick(v: View?) {
        if (v == binding.photoClickLayout) {
            try {
                val directory: File = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    directoryName
                )
                if (directory.exists()) {
                    deleteFile1(directory)
                }
                if (getPreference(SharedPreferConstant.IMAGE_PATH) != null) {
                    deleteTinyImages(getPreference(SharedPreferConstant.IMAGE_PATH).toString())
                }
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(Runnable { //Do something after 500ms
                    setPreference(SharedPreferConstant.IMAGE_PATH, "")
                    binding.filename.text = "File Name.jpg"
                    binding.imageViewLayout.visibility = View.GONE
                    capturePhoto()
                }, 500)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (v == binding.locationCaptureLayout) {
            binding.txtLatt.text =
                "Latitude: " + getPreferLogin(SharedPreferConstant.LATTITUDE).toString()
            binding.txtLong.text =
                "Longitude: " + getPreferLogin(SharedPreferConstant.LONGITUDE).toString()

            latt = getPreferLogin(SharedPreferConstant.LATTITUDE).toString()
            longg = getPreferLogin(SharedPreferConstant.LONGITUDE).toString()
            binding.dateTimeTxt.text = "Date & Time : " + dateTime
        }

        if (v == binding.formSubBut) {
            if (validation()) {
                if (isNetworkAvailable()) {
                    saveNalaStatusUpdate()
                }

            }
        }
    }

    private fun saveNalaStatusUpdate() {
        showDialog()
        statusSubmitViewModel.submitData(
            getPreferLogin(SharedPreferConstant.user_id).toString(),
            getPreferLogin(SharedPreferConstant.ulbId).toString(),
            getPreferLogin(SharedPreferConstant.circle_id).toString(),
            getPreferLogin(SharedPreferConstant.id).toString(),
            getPreference(SharedPreferConstant.nalaId).toString(),
            getPreferLogin(SharedPreferConstant.ward_id).toString(),
            statusId.toString(),
            getPreferLogin(SharedPreferConstant.zone_id).toString(),
            getPreferLogin(SharedPreferConstant.LATTITUDE).toString(),
            getPreferLogin(SharedPreferConstant.LONGITUDE).toString(),
            dateTime.toString(),
            "",
            "",
            "",
            getPreference(SharedPreferConstant.IMAGE_PATH).toString(),
            getPreference(SharedPreferConstant.Workid).toString()
            )
    }

    private fun validation(): Boolean {

        if (!validatePhoto()) {
            return false
        }
        if (binding.spinnerStatus.selectedItemPosition == 0) {
            showToastMessage("Please Select Status Type")
            return false
        }


        if (latt.isEmpty()) {
            showToastMessage("Please click on capture location");
            return false
        }
        return true
    }

    private fun validatePhoto(): Boolean {
        if (getPreference(SharedPreferConstant.IMAGE_PATH)!!.isNotEmpty()) {
            return true
        }
        snakeBarView(binding.imageViewLayout, "Please Capture  Image")
        return false
    }


    private fun capturePhoto() {
        if (ActivityCompat.checkSelfPermission(
                this@NalaUpdateScreenActivity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@NalaUpdateScreenActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@NalaUpdateScreenActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isDeviceSupportCamera()) {
                val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (camIntent.resolveActivity(packageManager) != null) {
                    var photoFile: File? = null
                    try {
                        photoFile = getOutputMediaFile()
                    } catch (ex2: java.lang.Exception) {
                        Log.d("Error", ex2.message.toString())
                    }
                    if (photoFile != null) {
                        val outputUri: Uri = FileProvider.getUriForFile(
                            this@NalaUpdateScreenActivity,
                            "$packageName.provider", photoFile
                        )
                        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
                        camIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivityForResult(camIntent, cameraRequestCode)
                    }
                }
                //File file = getOutputMediaFile();
            } else Toast.makeText(
                this@NalaUpdateScreenActivity,
                "Device Not supports Camera",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    multiplePermission
                )
            }
        }
    }

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private fun isDeviceSupportCamera(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    private fun getOutputMediaFile(): File? {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            directoryName
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val timestamp: String =
            SimpleDateFormat("ddMMMyyyy_HHmmss", Locale.getDefault()).format(Date())
        outFile =
            File(directory.toString() + File.separator + getPreferLogin(SharedPreferConstant.username) + "_" + timestamp + ".jpg")
        return outFile
    }

    private fun deleteTinyImages(tinyImage: String) {
        val target = File(tinyImage)
        Log.d("tiny_path", "" + tinyImage)
        if (target.exists() && target.isFile && target.canWrite()) {
            target.delete()
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(target)))
            Log.d("tiny_deleted", "" + target.name)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == multiplePermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // put your function here
                capturePhoto()
            } else {
                Toast.makeText(
                    this,
                    "Please allow the permission to utilize this feature.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === cameraRequestCode) {
            if (resultCode === RESULT_OK) {
                val options = Tiny.FileCompressOptions()
                Tiny.getInstance().source(outFile!!.absolutePath).asFile().withOptions(options)
                    .compress { isSuccess, outfile, t -> //return the compressed file path
                        setPhoto(outfile)
                        setPreference(SharedPreferConstant.IMAGE_PATH, outfile)
                    }
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outFile)))
            } else {
                Log.e("canceled", "camrea")
            }
        }

    }


    private fun setPhoto(absolutePath: String) {
        binding.imageViewLayout.visibility = View.VISIBLE
        Glide.with(this).load(Uri.fromFile(File(absolutePath)))
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_image_24).into(binding.imgView)
        val filename = absolutePath.substring(absolutePath.lastIndexOf("/") + 1)
        binding.filename.text = "" + filename
    }


    private fun deleteFile1(f: File) {
        val flist = f.list()
        if (flist != null && flist.isNotEmpty()) {
            for (s in flist) {
                println(" " + f.absolutePath)
                val temp = File(f.absolutePath + "/" + s)
                if (temp.isDirectory) {
                    deleteFile(temp.toString())
                    temp.delete()
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(temp)))
                    Log.d("main_dir_deleted", "" + temp.name)
                } else {
                    temp.delete()
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(temp)))
                    Log.d("main_file_deleted", "" + temp.name)
                }
            }
        } else {
            println("No Files")
        }

    }

}