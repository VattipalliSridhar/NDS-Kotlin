package com.apps.nemsapp.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.ActivityMainBinding
import com.apps.nemsapp.model.MenuModel
import com.apps.nemsapp.view.base.BaseActivity
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.view.ui.adapters.RecyclerMenuAdapter
import com.apps.nemsapp.view.ui.fragment.HomeFragment
import com.apps.nemsapp.view.ui.fragment.StatusFragment

class MainActivity : BaseActivity(), RecyclerMenuAdapter.OnItemClickListener {


    lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var recyclerMenuAdapter: RecyclerMenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        defaultFragment()


        val menuModel = ArrayList<MenuModel>()
        menuModel.add(MenuModel("Home", "Home"))
        menuModel.add(MenuModel("Logout", "Logout"))

        //adding a layoutmanager
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        recyclerMenuAdapter = RecyclerMenuAdapter(applicationContext, menuModel, this)
        binding.recyclerview.adapter = recyclerMenuAdapter

        binding.menuIcon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    fun titleTextChange(title: String) {
        binding.title.text = title
    }

    private fun defaultFragment() {

        val homFrag = HomeFragment()
        putFragment(homFrag, SharedPreferConstant.DASH_FRG_TAG)
    }

    private fun putFragment(homFrag: Fragment?, dashFrgTag: String) {
        if (homFrag != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, homFrag, SharedPreferConstant.DASH_FRG_TAG)
            transaction.commit()
        }
    }


    override fun onBackPressed() {

        this@MainActivity.viewModelStore.clear()
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            Log.e("MainActivity", "popping backstack")
            fm.popBackStack()
        } else {
            //popupExist("Do you want exit from application?")
            finish()
        }

    }

    private fun Logout() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setTitle("Confirm")
        builder.setMessage("Are you sure you want to logout from the application?")
        builder.setPositiveButton("Yes") { dialog, which ->
            clearAllPreferences()
            val navigatorLogin = Intent(this@MainActivity, LoginActivity::class.java)
            navigatorLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(navigatorLogin)
            finish()
        }
        builder.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemClick(position: Int) {
        binding.drawerLayout.closeDrawers()

        if (position == 0) {
            this@MainActivity.viewModelStore.clear()
            removeAllFragmentsInBackStack()
            defaultFragment()
        }
        /* if (position == 1) {
             val fragment1: Fragment = StatusFragment()
             val beginTransaction1 = supportFragmentManager.beginTransaction()
             beginTransaction1.replace(R.id.frame_layout, fragment1)
             beginTransaction1.addToBackStack(null)
             beginTransaction1.commitAllowingStateLoss()
         }*/
        if (position == 1) {
            Logout()
        }
    }


    private fun removeAllFragmentsInBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }

    }

}