package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqq.pocketrecipe.databinding.FragmentAbout02Binding

class FragmentAbout02: Fragment() {
    private lateinit var binding : FragmentAbout02Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbout02Binding.inflate(inflater,container,false)
        return binding.root
    }
}