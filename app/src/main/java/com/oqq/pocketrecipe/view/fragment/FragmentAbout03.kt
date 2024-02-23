package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oqq.pocketrecipe.databinding.FragmentAbout03Binding

class FragmentAbout03: Fragment() {
    private lateinit var binding : FragmentAbout03Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbout03Binding.inflate(inflater,container,false)
        return binding.root
    }
}