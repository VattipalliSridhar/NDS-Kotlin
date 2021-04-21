package com.apps.nemsapp.view.ui.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.nemsapp.BuildConfig
import com.apps.nemsapp.databinding.ActivityViewReportBinding
import com.apps.nemsapp.model.NalaReportModel
import com.apps.nemsapp.view.base.BaseActivity
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.view.ui.adapters.RecyclerViewReportAdapter
import com.apps.nemsapp.viewmodel.NalaReportViewModel

class ViewReportActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityViewReportBinding

    private lateinit var recyclerViewReportAdapter: RecyclerViewReportAdapter
    private var arrayList : ArrayList<NalaReportModel.Detail> = ArrayList()
    private lateinit var nalaReportViewModel: NalaReportViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewReportBinding.inflate(layoutInflater)
        setContentView(binding.root)


        nalaReportViewModel = ViewModelProvider(this).get(NalaReportViewModel::class.java)

        binding.backButton.setOnClickListener(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        recyclerViewReportAdapter = RecyclerViewReportAdapter(applicationContext,arrayList)
        binding.recyclerview.adapter = recyclerViewReportAdapter

        if(isNetworkAvailable())
        {
            getReport();
        }else{
            showToastMessage("Please Connect internet............")
        }


        nalaReportViewModel.nalaReportData.observe(this, Observer {
            arrayList.clear()
            if (it.statusCode == 200) {
                if (it.details.size > 0) {
                    arrayList.addAll(it.details)
                }
                recyclerViewReportAdapter.notifyDataSetChanged()
            } else {
                showToastMessage(it.statusMessage)
            }
            dismissDialog()
        })
        nalaReportViewModel.messageShow.observe(this, Observer {
            showToastMessage(it)
            dismissDialog()
        })

    }

    private fun getReport() {
        showDialog()
        nalaReportViewModel.getViewReport(getPreferLogin(SharedPreferConstant.user_id).toString(),
            getPreferLogin(SharedPreferConstant.ulbId).toString(),
            getPreferLogin(SharedPreferConstant.circle_id).toString(),
            getPreferLogin(SharedPreferConstant.ward_id).toString(),
            BuildConfig.VERSION_CODE, getPreference(SharedPreferConstant.nalaId).toString())
    }

    override fun onClick(v: View?) {
        if (v == binding.backButton) {
            this.finish()
        }
    }
}