package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentUpdateRestaurantDetailPageBinding
import org.koin.android.ext.android.inject

class FragmentUpdateRestaurantDetailPage: Fragment() {
    private lateinit var binding:FragmentUpdateRestaurantDetailPageBinding

    private val navOptions:NavOptions by inject()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateRestaurantDetailPageBinding.inflate(inflater,container,false)
        setIntialData()
        setBahavior()
        return binding.root
    }

    private fun setIntialData() {

    }

    private fun setBahavior() {
        binding.btnNo.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_detail_dest_to_fragment_update_restaurant_detail_dest,null)
        }

        binding.btnAddFood.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_update_restaurant_detail_dest_to_fragment_add_food_detail_dest,null,navOptions)
        }
    }
}