package com.apps.nemsapp.view.ui.adapters

import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.DashboardAdapterBinding
import com.apps.nemsapp.model.DashBoardModel
import com.apps.nemsapp.view.ui.activities.MainActivity
import com.apps.nemsapp.view.ui.fragment.HomeFragment
import com.bumptech.glide.Glide
import java.io.File

class RecyclerPipeAdapter(
    private val activity: MainActivity?,
    private val arrayList: ArrayList<DashBoardModel.Detail>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerPipeAdapter.MyHolderClass>() {

    inner class MyHolderClass(var dashboardAdapterBinding: DashboardAdapterBinding) :
        RecyclerView.ViewHolder(dashboardAdapterBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerPipeAdapter.MyHolderClass {

        val view = DashboardAdapterBinding.inflate(
            LayoutInflater.from(activity),
            parent,
            false
        )
        return MyHolderClass(view)


    }

    override fun onBindViewHolder(holder: RecyclerPipeAdapter.MyHolderClass, position: Int) {

        holder.dashboardAdapterBinding.pipeTypeText.text = arrayList[position].statusDescription
        holder.dashboardAdapterBinding.countText.text = arrayList[position].statusCount.toString()
        holder.dashboardAdapterBinding.layout.setBackgroundColor(Color.parseColor(arrayList[position].statusColorCode))

        Glide.with((activity as MainActivity)).load(arrayList[position].statusIcon)
            .into(holder.dashboardAdapterBinding.icon)


        holder.dashboardAdapterBinding.bgCardLayout.setOnClickListener {
            val position = holder.adapterPosition
            Log.e("msg", "" + position)
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}