package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentAddRestaurantDetailPageBinding

class FragmentAddRestaurantDetailPage: Fragment() {
    private lateinit var binding:FragmentAddRestaurantDetailPageBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRestaurantDetailPageBinding.inflate(inflater,container,false)
        setIntitalData()
        onBehavior()
        return binding.root
    }

    private fun onBehavior() {
        binding.btnNo.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_dest_to_fragment_add_restaurant_detail_dest,null)
        }
    }

    private fun setIntitalData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}