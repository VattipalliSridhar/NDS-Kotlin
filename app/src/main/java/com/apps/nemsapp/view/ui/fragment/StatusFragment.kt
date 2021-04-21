package com.apps.nemsapp.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.nemsapp.R
import com.apps.nemsapp.databinding.FragmentStatusBinding


class StatusFragment : Fragment() {


    private lateinit var binding : FragmentStatusBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater, container, false)






        return binding.root
    }

}