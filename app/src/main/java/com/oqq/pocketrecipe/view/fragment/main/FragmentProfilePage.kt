package com.oqq.pocketrecipe.view.fragment.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.client.UserInfo
import com.oqq.pocketrecipe.listener.DialogResultListener
import com.oqq.pocketrecipe.databinding.FragmentProfilePageBinding
import com.oqq.pocketrecipe.view.activity.ActivityRestaurantOwnerPage
import com.oqq.pocketrecipe.view.dialog.CustomDialog
import org.koin.android.ext.android.inject

class FragmentProfilePage: Fragment() , DialogResultListener {
    private lateinit var binding:FragmentProfilePageBinding

    private val navOptions:NavOptions by inject()

    private val mSecurePreferences: AppPreferences by inject()

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var currentUser:UserInfo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }




    @SuppressLint("SetTextI18n")
    private fun setIntialData() {
        currentUser = mSecurePreferences.getUserInfo().user

        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        Glide.with(activity!!).load(getString(R.string.url_connect)+currentUser.imgAvatar).into(binding.imgAvatar)
        binding.name.text = currentUser.firstName + " "+ currentUser.lastName
        binding.secondName.text = "@${currentUser.username}"
        if (currentUser.phone!!.isNotEmpty()){
            binding.phone.text = currentUser.phone
        }
        else {
            binding.layoutPhone.visibility = View.GONE
        }

        if (currentUser.idMode == 0) binding.layoutModeAdmin.visibility = View.GONE

     


    }


    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivInfoProfile.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_dest_to_fragment_profile_detail_dest,null,navOptions)
        }

        binding.switchMode.setOnClickListener{
            val customDialog = CustomDialog(activity!!,this,"Trở thành quyền quản trị viên","Bạn có chắc chắn muốn thay đổi sang quyền quản trị viên không?")
            customDialog.show()
        }

        binding.aboutMe.setOnClickListener {
            val action =FragmentProfilePageDirections.actionFragmentProfileDestToFragmentInfoDest(
                getString(R.string.title_about_me), getString(R.string.about_me)
            )
            findNavController().navigate(action,navOptions)
        }

        binding.policy.setOnClickListener {
            val action = FragmentProfilePageDirections.actionFragmentProfileDestToFragmentInfoDest(
                getString(R.string.title_policy), getString(R.string.policy)
            )
            findNavController().navigate(action,navOptions)
        }
    }

    override fun onDialogResult(result: Boolean) {
        if (result){
            val intent = Intent(activity, ActivityRestaurantOwnerPage::class.java)
            startActivity(intent)
            activity!!.finish()
        }
        else {
            binding.switchMode.isChecked = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}