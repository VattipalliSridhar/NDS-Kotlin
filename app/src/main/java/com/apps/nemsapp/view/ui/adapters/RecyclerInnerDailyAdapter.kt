package com.apps.nemsapp.view.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.apps.nemsapp.databinding.NalaDailyInnerListAdapterLayoutBinding

class RecyclerInnerDailyAdapter(private val applicationContext: Context) :
    RecyclerView.Adapter<RecyclerInnerDailyAdapter.MyHolderClass>() {

    inner class MyHolderClass(var nalaDailyInnerListAdapterLayoutBinding:
                              NalaDailyInnerListAdapterLayoutBinding) :
        RecyclerView.ViewHolder(nalaDailyInnerListAdapterLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderClass {
        val view = NalaDailyInnerListAdapterLayoutBinding.inflate(
            LayoutInflater.from(applicationContext),
            parent,
            false
        )
        return MyHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyHolderClass, position: Int) {

    }

    override fun getItemCount(): Int {
       return 5
    }
}