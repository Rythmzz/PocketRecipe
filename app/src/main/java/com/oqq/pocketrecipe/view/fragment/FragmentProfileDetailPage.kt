package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.client.UserInfo
import com.oqq.pocketrecipe.databinding.FragmentProfileDetailPageBinding
import org.koin.android.ext.android.inject

class FragmentProfileDetailPage: Fragment() {
    private lateinit var binding:FragmentProfileDetailPageBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    private val mSecurePreferences: AppPreferences by inject()

    private lateinit var currentUser:UserInfo


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailPageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_dest_to_fragment_profile_detail_dest,null)
        }
    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        currentUser = mSecurePreferences.getUserInfo().user

        binding.editLastName.setText(currentUser.lastName)
        binding.editFirstName.setText(currentUser.firstName)

        Glide.with(activity!!).load(getString(R.string.url_connect)+currentUser.imgAvatar).into(binding.imageAvatar)

        if (currentUser.gender == 0) binding.rbMale.isChecked =true
        else binding.rbFemale.isChecked = true

        if (currentUser.email != null){
            binding.editEmail.setText(currentUser.email)
        }

        if (currentUser.phone != null){
            binding.editPhone.setText(currentUser.phone)
        }
        if (currentUser.address != null){
            binding.editAddress.setText(currentUser.address)
        }




    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}