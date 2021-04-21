package com.apps.nemsapp.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.nemsapp.databinding.ActivityNalaDailyListBinding
import com.apps.nemsapp.view.ui.adapters.RecyclerDailyListAdapter

class NalaDailyListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNalaDailyListBinding
    private lateinit var recyclerDailyListAdapter : RecyclerDailyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNalaDailyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        recyclerDailyListAdapter = RecyclerDailyListAdapter(applicationContext)
        binding.recyclerview.adapter = recyclerDailyListAdapter
    }
}