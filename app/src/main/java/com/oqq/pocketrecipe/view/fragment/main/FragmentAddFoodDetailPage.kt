package com.oqq.pocketrecipe.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentAddFoodDetailPageBinding

class FragmentAddFoodDetailPage: Fragment() {
    private lateinit var binding:FragmentAddFoodDetailPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodDetailPageBinding.inflate(inflater,container,false)

        setBehavior()
        return binding.root
    }

    private fun setBehavior() {
        binding.btnNo.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_update_restaurant_detail_dest_to_fragment_add_food_detail_dest,null)
        }
    }
}