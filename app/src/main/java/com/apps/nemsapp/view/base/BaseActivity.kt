package com.apps.nemsapp.view.base

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {


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


    private var progressDialog: ProgressDialog? = null
    var activeNetworkInfo: NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetworkInfo = connectivityManager.activeNetworkInfo



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

    }


    fun isNetworkAvailable(): Boolean {
        return activeNetworkInfo != null && activeNetworkInfo!!.isConnected
    }

    fun showToastMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun snakeBarView(view: View?, msg: String?) {
        Snackbar.make(view!!, msg!!, Snackbar.LENGTH_SHORT).show()
    }

    fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        if (!progressDialog!!.isShowing) progressDialog!!.show()
    }

    fun dismissDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun setPreferLogin(TAG: String?, value: String?) {
        val preferences = applicationContext.getSharedPreferences(
            SharedPreferConstant.LOGIN_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putString(TAG, value)
        editor.apply()
    }

    fun getPreferLogin(TAG: String?): String? {
        val preferences = applicationContext.getSharedPreferences(
            SharedPreferConstant.LOGIN_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getString(TAG, "")
    }

    fun setPreference(TAG: String?, value: String?) {
        val preferences = applicationContext.getSharedPreferences(
            SharedPreferConstant.MY_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putString(TAG, value)
        editor.apply()
    }


    val dateTime: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }
    val date: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }

    fun getPreference(TAG: String?): String? {
        val preferences = applicationContext.getSharedPreferences(
            SharedPreferConstant.MY_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getString(TAG, "")
    }

    open fun clearAllPreferences() {
            val editor = this.applicationContext.getSharedPreferences(
                SharedPreferConstant.LOGIN_PREFERENCES,
                MODE_PRIVATE
            ).edit()
            editor.clear()
            editor.apply()
    }
    open fun clearPreferences() {
        val editor = this.applicationContext.getSharedPreferences(
            SharedPreferConstant.MY_PREFERENCES,
            MODE_PRIVATE
        ).edit()
        editor.clear()
        editor.apply()
    }



    //location
     fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                updateLocationUI()
            }
        }
    }

     fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        // Sets the fastest rate for active location updates. This interval is exact, and your
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

     fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }


     fun updateLocationUI() {
        if (mCurrentLocation != null) {
            setPreferLogin(SharedPreferConstant.LATTITUDE, mCurrentLocation!!.latitude.toString())
            setPreferLogin(SharedPreferConstant.LONGITUDE, mCurrentLocation!!.longitude.toString())
        }
    }


     fun startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(
                this!!,
                OnSuccessListener { // Log.i(TAG, "All location settings are satisfied.");
                    if (applicationContext != null) {
                        if (ContextCompat.checkSelfPermission(
                                applicationContext!!,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                                applicationContext!!,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@OnSuccessListener
                        }
                    }
                    mFusedLocationClient!!.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper()
                    )
                    updateUI()
                })
            .addOnFailureListener(this!!) { e ->
                val statusCode = (e as ApiException).statusCode
                when (statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        /*  Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");*/try {
                        // Show the dialog by calling startResolutionForResult(), and check the
                        // result in onActivityResult().
                        val rae = e as ResolvableApiException
                        rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                    } catch (sie: IntentSender.SendIntentException) {
                        // Log.i(TAG, "PendingIntent unable to execute request.");
                    }

                }
                updateUI()
            }
    }


     fun updateUI() {
        updateLocationUI()
    }


    open fun getVersionName(): String? {
        var packagename = ""
        val packageManager = this.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(this.packageName, 0)
            packagename = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packagename
    }



}