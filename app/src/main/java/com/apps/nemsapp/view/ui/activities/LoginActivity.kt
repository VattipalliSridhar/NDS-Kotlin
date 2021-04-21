package com.apps.nemsapp.view.ui.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.apps.nemsapp.BuildConfig
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.ActivityLoginBinding
import com.apps.nemsapp.view.base.BaseActivity
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.view.ui.navigators.LoginNavigator
import com.apps.nemsapp.viewmodel.LoginViewModel

class LoginActivity : BaseActivity(), LoginNavigator {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionDialog()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.loginButton.setOnClickListener {
            hideKeyboard()
            if (validation()) {
                if (isNetworkAvailable()) {
                    getLoginData()
                } else {
                    showToastMessage("Please Connect Internet.....")
                }


            }
        }




        loginViewModel.loginLiveData.observe(this, {
            if (it.status_code == 200) {
                setPreferLogin(SharedPreferConstant.ulbId, it.ulbId)
                setPreferLogin(SharedPreferConstant.id, it.id.toString())
                setPreferLogin(SharedPreferConstant.username, it.username)
                setPreferLogin(SharedPreferConstant.user_id, it.user_id)
                setPreferLogin(SharedPreferConstant.circle_no, it.circle_no)
                setPreferLogin(SharedPreferConstant.user_type, it.user_type)
                setPreferLogin(SharedPreferConstant.ward_id, it.ward_id.toString())
                setPreferLogin(SharedPreferConstant.ward_name, it.ward_name)
                setPreferLogin(SharedPreferConstant.circle_id, it.circle_id.toString())
                setPreferLogin(SharedPreferConstant.zone_id, it.zone_id.toString())
                setPreferLogin(SharedPreferConstant.circle_name, it.circle_name)
                setPreferLogin(SharedPreferConstant.LOGIN_SUCCESS, "1")

                openMainActivity()

            } else {
                Toast.makeText(applicationContext, it.status_message, Toast.LENGTH_LONG).show()
            }
            dismissDialog()
        })


        loginViewModel.messageShow.observe(this, {
            showToastMessage(it)
            dismissDialog()
        })
    }


    private val requestPermissionCode = 1

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun permissionDialog() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
            }
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), requestPermissionCode
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == requestPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                permissionDialogBox()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun permissionDialogBox() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle("App requires permissions to work perfectly..!")
        builder.setPositiveButton("Ok") { dialog, which ->
            dialog.dismiss()
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton(
            "Exit"
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    private fun getLoginData() {
        showDialog()
        loginViewModel.getLogin(
            binding.etUser.text.toString().trim(),
            binding.etPassword.text.toString().trim(),
            BuildConfig.VERSION_CODE
        )
    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding.etUser.text.toString())) {
            showToastMessage("Enter User Name")
            binding.etUser.requestFocus()
            return false
        }
        if (TextUtils.isEmpty(binding.etPassword.text.toString())) {
            showToastMessage("Enter Password")
            binding.etPassword.requestFocus()
            return false
        }
        return true
    }

    override fun openMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


    override fun onBackPressed() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this@LoginActivity)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setTitle("Confirm")
        builder.setMessage("Do you want exit from the application?")
        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}