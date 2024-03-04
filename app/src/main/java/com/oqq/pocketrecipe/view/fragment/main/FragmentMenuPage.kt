package com.oqq.pocketrecipe.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentMenuPageBinding
import org.koin.android.ext.android.inject

class FragmentMenuPage: Fragment() {
    private lateinit var binding:FragmentMenuPageBinding

    private val navOptions:NavOptions by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuPageBinding.inflate(inflater,container,false)

        setIntialData()
        setBehavior()
        return binding.root

    }

    private fun setBehavior() {
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_menu_dest_to_fragment_menu_search_dest,null, navOptions)
        }

        binding.layoutMenu.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_menu_dest_to_fragment_menu_restaurant_dest,null,navOptions)
        }

        binding.textAddress.setOnClickListener {

        }

    }

    private fun setIntialData() {

    }
}