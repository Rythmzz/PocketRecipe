package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentRestaurantPageBinding
import org.koin.android.ext.android.inject

class FragmentRestaurantPage: Fragment() {
    private lateinit var binding:FragmentRestaurantPageBinding
    private val navOptions:NavOptions by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantPageBinding.inflate(inflater,container,false)
        setBehavior()



        return binding.root
    }

    private fun setBehavior() {
        binding.detailRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_dest_to_fragment_restaurant_detail_dest,null,navOptions)
        }
        binding.btnCreateRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_dest_to_fragment_add_restaurant_detail_dest,null,navOptions)
        }
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_dest_to_fragment_restaurant_search_dest,null,navOptions)
        }
    }
}