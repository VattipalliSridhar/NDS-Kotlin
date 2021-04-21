package com.apps.nemsapp.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.nemsapp.BuildConfig
import com.apps.nemsapp.databinding.FragmentHomeBinding
import com.apps.nemsapp.model.DashBoardModel
import com.apps.nemsapp.view.base.BaseFragment
import com.apps.nemsapp.view.base.SharedPreferConstant
import com.apps.nemsapp.view.ui.activities.MainActivity
import com.apps.nemsapp.view.ui.activities.NallaListActivity
import com.apps.nemsapp.view.ui.adapters.RecyclerPipeAdapter
import com.apps.nemsapp.view.ui.java.view.NalaDailyListActivityJava
import com.apps.nemsapp.viewmodel.DashBoardViewModel


class HomeFragment : BaseFragment(), RecyclerPipeAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerPipeAdapter: RecyclerPipeAdapter
    private var arrayList: ArrayList<DashBoardModel.Detail> = ArrayList()
    private lateinit var dashBoardViewModel: DashBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        dashBoardViewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)

        (activity as MainActivity?)!!.titleTextChange("Home")

        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        binding.recyclerview.setHasFixedSize(true)
        recyclerPipeAdapter = RecyclerPipeAdapter((activity as MainActivity), arrayList, this)
        binding.recyclerview.adapter = recyclerPipeAdapter

        if (isNetworkAvailable) {
            getDashBoardData()
        } else {
            showToastMessage("Please Connect Internet........")
        }

        dashBoardViewModel.dashLiveData.observe((activity as MainActivity), Observer {
            arrayList.clear()
            if (it.statusCode == 200) {
                if (it.details.size > 0) {
                    arrayList.addAll(it.details)
                }
                recyclerPipeAdapter.notifyDataSetChanged()

            } else {
                showToastMessage("${it.statusMessage}")
            }
            dismissDialog()
        })

        dashBoardViewModel.messageShow.observe((activity as MainActivity), Observer {
            showToastMessage(it)
        })




        return binding.root
    }

    private fun getDashBoardData() {
        showDialogs()
        dashBoardViewModel.getDashBoard(
            getPreferLogin(SharedPreferConstant.user_id).toString(),
            getPreferLogin(SharedPreferConstant.ulbId).toString(),
            getPreferLogin(SharedPreferConstant.circle_id).toString(),
            getPreferLogin(SharedPreferConstant.ward_id).toString(),
            BuildConfig.VERSION_CODE,
            getPreferLogin(SharedPreferConstant.id).toString()
        )
    }

    override fun onItemClick(position: Int) {

        if (arrayList[position].statusCount == 0) {
            showToastMessage(arrayList[position].statusDescription + " Count is Zero")
        } else {
            if (arrayList[position].statusId == 5) {
                val intent =
                    Intent((activity as MainActivity), NalaDailyListActivityJava::class.java)
                setPreference(
                    SharedPreferConstant.statusId,
                    arrayList[position].statusId.toString()
                )
                startActivity(intent)
            } else {
                val intent = Intent((activity as MainActivity), NallaListActivity::class.java)
                setPreference(
                    SharedPreferConstant.statusId,
                    arrayList[position].statusId.toString()
                )
                startActivity(intent)
            }
        }


    }

}