package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentInfoPageBinding

class FragmentInfoPage: Fragment() {
    private lateinit var binding:FragmentInfoPageBinding
    private val safeArgs:FragmentInfoPageArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoPageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }


    private fun setIntialData() {
        binding.textTitle.setText(safeArgs.title)
        binding.textDescription.setText(safeArgs.description)

    }

    private fun setBehavior(){
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}