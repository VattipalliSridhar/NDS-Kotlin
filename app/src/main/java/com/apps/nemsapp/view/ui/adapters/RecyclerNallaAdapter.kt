package com.apps.nemsapp.view.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.nemsapp.databinding.NalaAdapterLayoutBinding
import com.apps.nemsapp.model.NalaListModel
import com.apps.nemsapp.view.ui.activities.NallaListActivity

class RecyclerNallaAdapter(
    private val applicationContext: Context,
    private val arrayList: ArrayList<NalaListModel.Detail>,
    private val nallaListActivity: NallaListActivity
) : RecyclerView.Adapter<RecyclerNallaAdapter.MyHolderClass>() {

    inner class MyHolderClass(var nalaAdapterLayoutBinding: NalaAdapterLayoutBinding) :
        RecyclerView.ViewHolder(nalaAdapterLayoutBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerNallaAdapter.MyHolderClass {
        val view = NalaAdapterLayoutBinding.inflate(
            LayoutInflater.from(applicationContext),
            parent,
            false
        )
        return MyHolderClass(view)
    }

    override fun onBindViewHolder(holder: RecyclerNallaAdapter.MyHolderClass, position: Int) {

        holder.nalaAdapterLayoutBinding.nallaNameText.text = arrayList[position].workno
        //holder.nalaAdapterLayoutBinding.wardText.text = arrayList[position].wardName
        holder.nalaAdapterLayoutBinding.statusText.text = arrayList[position].statusDescription
        holder.nalaAdapterLayoutBinding.nalaLocationTxt.text = arrayList[position].nallaLocation
        holder.nalaAdapterLayoutBinding.nalaFromTxt.text = arrayList[position].from
        holder.nalaAdapterLayoutBinding.nalaToTxt.text = arrayList[position].to

       /* holder.nalaAdapterLayoutBinding.updateButton.visibility=View.VISIBLE

        holder.nalaAdapterLayoutBinding.updateButton.setOnClickListener {
            nallaListActivity.updateData(position)
        }*/


        if(arrayList[position].status==3){
            holder.nalaAdapterLayoutBinding.updateButton.visibility=View.VISIBLE
            holder.nalaAdapterLayoutBinding.updateButton.text = arrayList[position].statusDescription
        }else{
            holder.nalaAdapterLayoutBinding.updateButton.visibility=View.VISIBLE

            holder.nalaAdapterLayoutBinding.updateButton.setOnClickListener {
                nallaListActivity.updateData(position)
            }

        }
/*
        if(arrayList[position].status==3){
            holder.nalaAdapterLayoutBinding.updateButton.visibility=View.GONE
        }else{
            holder.nalaAdapterLayoutBinding.updateButton.visibility=View.VISIBLE

            holder.nalaAdapterLayoutBinding.updateButton.setOnClickListener {
                nallaListActivity.updateData(position)
            }

        }
        holder.nalaAdapterLayoutBinding.viewButton.setOnClickListener {
            nallaListActivity.viewReportData(position)
        }*/



    }

    override fun getItemCount(): Int {

        return arrayList.size
    }
}