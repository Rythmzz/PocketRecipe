package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentFoodDetailPageBinding

class FragmentFoodDetailPage: Fragment() {
    private lateinit var binding:FragmentFoodDetailPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodDetailPageBinding.inflate(inflater,container,false)
        setBehavior()
        return binding.root
    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_menu_restaurant_dest_to_fragment_food_detail_dest,null)
        }
    }
}