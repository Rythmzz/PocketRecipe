package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.FragmentManageOrderPageBinding
import org.koin.android.ext.android.inject

class FragmentManageOrderPage: Fragment() {
    private lateinit var binding:FragmentManageOrderPageBinding

    private val navOptions:NavOptions by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageOrderPageBinding.inflate(inflater,container,false)
        setBehavior()
        return binding.root
    }

    private fun setBehavior() {
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_order_dest_to_fragment_order_search_dest,null,navOptions)
        }
    }
}