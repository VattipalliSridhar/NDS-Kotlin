package com.apps.nemsapp.view.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.nemsapp.databinding.NalaDailyListAdapterLayoutBinding

class RecyclerDailyListAdapter(private val applicationContext: Context) : RecyclerView.Adapter<RecyclerDailyListAdapter.MyHolderClass>() {

    private lateinit var recyclerInnerDailyAdapter: RecyclerInnerDailyAdapter

    inner class MyHolderClass(var nalaDailyListAdapterLayoutBinding: NalaDailyListAdapterLayoutBinding) : RecyclerView.ViewHolder(nalaDailyListAdapterLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderClass {
        val view = NalaDailyListAdapterLayoutBinding.inflate(
            LayoutInflater.from(applicationContext),
            parent,
            false
        )
        return MyHolderClass(view)
    }

    override fun onBindViewHolder(holder: MyHolderClass, position: Int) {


        holder.nalaDailyListAdapterLayoutBinding.formToRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        holder.nalaDailyListAdapterLayoutBinding.formToRecyclerview.setHasFixedSize(true)
        recyclerInnerDailyAdapter = RecyclerInnerDailyAdapter(applicationContext)
        holder.nalaDailyListAdapterLayoutBinding.formToRecyclerview.adapter = recyclerInnerDailyAdapter


    }

    override fun getItemCount(): Int {

        return 5
    }
}