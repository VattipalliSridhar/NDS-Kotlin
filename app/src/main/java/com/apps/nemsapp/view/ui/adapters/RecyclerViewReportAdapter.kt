package com.apps.nemsapp.view.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.ViewReportAdapterLayoutBinding
import com.apps.nemsapp.model.NalaReportModel
import com.bumptech.glide.Glide

class RecyclerViewReportAdapter(
    private val applicationContext: Context,
    private val arrayList: ArrayList<NalaReportModel.Detail>
) :
    RecyclerView.Adapter<RecyclerViewReportAdapter.MyHolderClass>() {
    inner class MyHolderClass(var viewReportAdapterLayoutBinding: ViewReportAdapterLayoutBinding) :
        RecyclerView.ViewHolder(viewReportAdapterLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderClass {

        val view = ViewReportAdapterLayoutBinding.inflate(
            LayoutInflater.from(applicationContext),
            parent,
            false
        )
        return MyHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyHolderClass, position: Int) {

        Glide.with(applicationContext).load(arrayList[position].image)
            .placeholder(
                ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.loading_img, null
                )
            )
            .error(R.drawable.ic_broken_image) //6
            .fallback(R.drawable.ic_no_image) //7
            .into(holder.viewReportAdapterLayoutBinding.nalaImg)

        holder.viewReportAdapterLayoutBinding.nalaNameText.text = arrayList[position].nallaLocation
        holder.viewReportAdapterLayoutBinding.nalaUpdateTimeTxt.text = arrayList[position].dateTime
        holder.viewReportAdapterLayoutBinding.nalaUpdateDataTxt.text =
            arrayList[position].todayTotalQty + "(m3)"
        holder.viewReportAdapterLayoutBinding.nalaStatusText.text = arrayList[position].status

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}