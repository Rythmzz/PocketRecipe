package com.oqq.orderqquickly.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.orderqquickly.R
import com.oqq.orderqquickly.databinding.FragmentRestaurantSearchPageBinding

class FragmentRestaurantSearchPage: Fragment() {

    private lateinit var binding:FragmentRestaurantSearchPageBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantSearchPageBinding.inflate(inflater,container,false)
        setInitialData()
        return binding.root
    }

    private fun setInitialData() {
        binding.editSearch.requestFocus()
        binding.editSearch.showKeyboard()

        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}