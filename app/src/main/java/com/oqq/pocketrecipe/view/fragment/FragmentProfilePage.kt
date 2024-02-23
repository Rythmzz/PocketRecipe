package com.oqq.pocketrecipe.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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

    private lateinit var currentUser:UserInfo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }

    private fun setIntialData() {
        currentUser = mSecurePreferences.getUserInfo().user

        Glide.with(activity!!).load(getString(R.string.url_connect)+currentUser.imgAvatar).into(binding.imgAvatar)
        binding.name.setText(currentUser.firstName + " "+ currentUser.lastName)
        binding.secondName.setText("@${currentUser.username}")
        if (currentUser.phone != null){
            binding.phone.setText(currentUser.phone)
        }
        else {
            binding.layoutPhone.visibility = View.GONE
        }

     


    }


    private fun setBehavior() {
        binding.ivInfoProfile.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_dest_to_fragment_profile_detail_dest,null,navOptions)
        }

        binding.switchMode.setOnClickListener{
            val customDialog = CustomDialog(activity!!,this,"Trở thành quyền quản trị viên","Bạn có chắc chắn muốn thay đổi sang quyền quản trị viên không?")
            customDialog.show()
        }
    }

    override fun onDialogResult(result: Boolean) {
        if (result){
            val intent:Intent = Intent(activity, ActivityRestaurantOwnerPage::class.java)
            startActivity(intent)
            activity!!.finish()
        }
        else {
            binding.switchMode.isChecked = false
        }
    }
}