package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentRestaurantDetailPageBinding
import org.koin.android.ext.android.inject

class FragmentRestaurantDetailPage: Fragment() {
    private lateinit var binding:FragmentRestaurantDetailPageBinding
    private val navOptions:NavOptions by inject()

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailPageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_dest_to_fragment_restaurant_detail_dest,null)
        }
        binding.tvEdit.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_restaurant_detail_dest_to_fragment_update_restaurant_detail_dest,null,navOptions)
        }






    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE

    }
}