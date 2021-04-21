package com.apps.nemsapp.view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.nemsapp.BuildConfig
import com.apps.nemsapp.databinding.ActivityNallaListBinding
import com.apps.nemsapp.model.NalaListModel
import com.apps.nemsapp.view.base.BaseActivity
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.view.ui.adapters.RecyclerNallaAdapter
import com.apps.nemsapp.viewmodel.NalaListViewModel

class NallaListActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNallaListBinding
    private lateinit var nalaListViewModel: NalaListViewModel
    private var arrayList: ArrayList<NalaListModel.Detail> = ArrayList()
    private lateinit var recyclerNallaAdapter: RecyclerNallaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNallaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nalaListViewModel = ViewModelProvider(this).get(NalaListViewModel::class.java)
        val statusId = intent.getStringExtra("status_id")

        Log.e("msgss",""+statusId)

        binding.backButton.setOnClickListener(this)


        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)

        recyclerNallaAdapter = RecyclerNallaAdapter(applicationContext,arrayList,this)
        binding.recyclerview.adapter = recyclerNallaAdapter


        if (isNetworkAvailable()) {
            getNalaList(statusId)
        } else {

        }

        nalaListViewModel.nalaListData.observe(this, Observer {
            if (it.statusCode == 200) {
                if (it.details.size > 0) {
                    arrayList.addAll(it.details)
                }
                recyclerNallaAdapter.notifyDataSetChanged()

            } else {
                showToastMessage(it.statusMessage)
            }
            dismissDialog()
        })

        nalaListViewModel.messageShow.observe(this, Observer {
            showToastMessage(it)
            dismissDialog()
        })


    }

    private fun getNalaList(statusId: String?) {
        showDialog()

            nalaListViewModel.getNalaData(
                getPreferLogin(SharedPreferConstant.user_id).toString(),
                getPreferLogin(SharedPreferConstant.ulbId).toString(),
                getPreferLogin(SharedPreferConstant.circle_id).toString(),
                getPreferLogin(SharedPreferConstant.ward_id).toString(),
                BuildConfig.VERSION_CODE, getPreference(SharedPreferConstant.statusId).toString()
            )


    }

    override fun onClick(v: View?) {
        if (v == binding.backButton) {
            setPreference(SharedPreferConstant.IMAGE_PATH, "")
            this.finish()
        }

    }


    fun updateData(position: Int)
    {
        val intent = Intent(applicationContext, NalaUpdateScreenActivity::class.java)
        setPreference(SharedPreferConstant.nalaId,arrayList[position].nallaId.toString())
        setPreference(SharedPreferConstant.nalaName,arrayList[position].nallaLocation)
        setPreference(SharedPreferConstant.nalaStatus,arrayList[position].statusDescription)
        setPreference(SharedPreferConstant.nalaTotalQty,arrayList[position].totalQty)
        setPreference(SharedPreferConstant.nalaYesterDayQty,arrayList[position].yesterdayTotalQty)
        setPreference(SharedPreferConstant.nalaProgerssValue,arrayList[position].nallaProgress)
        setPreference(SharedPreferConstant.Workid,arrayList[position].workid)
        startActivity(intent)
    }

    fun viewReportData(position: Int) {

        val intent = Intent(applicationContext, ViewReportActivity::class.java)
        setPreference(SharedPreferConstant.nalaId,arrayList[position].nallaId.toString())
        startActivity(intent)
    }
}