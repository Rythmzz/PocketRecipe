package com.oqq.pocketrecipe.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.client.UserInfo
import com.oqq.pocketrecipe.data.model.client.UserResponse
import com.oqq.pocketrecipe.databinding.FragmentProfileDetailPageBinding
import com.oqq.pocketrecipe.di.MyApplication
import com.oqq.pocketrecipe.listener.DialogResultListener
import com.oqq.pocketrecipe.view.dialog.CustomDialog
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream

class FragmentProfileDetailPage: Fragment(), DialogResultListener {
    private lateinit var binding:FragmentProfileDetailPageBinding



    private val mSecurePreferences: AppPreferences by inject()

    private lateinit var currentUser:UserInfo

    private var isModeEdit:Boolean = false

    private val loginViewModel:LoginViewModel by viewModel()

    private var currentImageAvatar:MultipartBody.Part? = null

    private var firstThread:Job? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDetailPageBinding.inflate(inflater,container,false)
        setIntialData()
        setStatus()
        setBehavior()
        return binding.root
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success2.collect{
                    result -> if (result is Boolean && result){
                        updateUser(currentUser)
                }
                }
            }

            this.launch {
                loginViewModel.success.collect{
                    result -> if (result is Boolean && result){
                        Toast.makeText(activity,"Sửa thông tin thành công",Toast.LENGTH_SHORT).show()

                    binding.updateUser.setText("Chỉnh sửa")

                    isModeEdit = false
                    infoEnable(isModeEdit)
                }
                }
            }

        }
    }

    fun infoEnable(enable:Boolean){
        binding.editLastName.isEnabled = enable
        binding.editFirstName.isEnabled = enable
        binding.rbFemale.isEnabled = enable
        binding.rbMale.isEnabled = enable
        binding.editAddress.isEnabled = enable

        if (enable) binding.editAvatar.visibility = View.VISIBLE
        else binding.editAvatar.visibility = View.GONE


        if (binding.editEmail.text.isEmpty()){
            binding.editEmail.isEnabled = enable
        }
        else {
            binding.editEmail.isEnabled = false
        }


        if (binding.editPhone.text.isEmpty()){
            binding.editPhone.isEnabled = enable
        }
        else{
            binding.editPhone.isEnabled = false
        }

    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_dest_to_fragment_profile_detail_dest,null)
        }

        binding.editAvatar.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                MyApplication.PICK_IMAGE
            )
        }

        binding.updateUser.setOnClickListener {
            if (!isModeEdit){
                binding.updateUser.setText("Hoàn thành")



                isModeEdit = true
                infoEnable(isModeEdit)
            }
            else {
                if (binding.editLastName.text.isEmpty()){
                    binding.editLastName.error = "Không được để trống Họ(đệm)"
                }
                else if (binding.editFirstName.text.isEmpty()){
                    binding.editFirstName.error = "Không được để trống tên"
                }
                else {
                    val dialog = CustomDialog(activity!!,this,"Thay đổi thông tin cá nhân", "Bạn có chắc chắn thay đổi thông tin không?")
                    dialog.show()
                }


            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MyApplication.PICK_IMAGE && resultCode == Activity.RESULT_OK){
            val imageUri = data!!.data
            binding.imageAvatar.setImageURI(imageUri)
            val inputStream: InputStream = activity!!.contentResolver.openInputStream(imageUri!!)!!
            val imageData = inputStream.readBytes()
            val requestFile = RequestBody.create(
                MediaType.parse(activity!!.contentResolver.getType(imageUri)),
                imageData
            )
            val image = MultipartBody.Part.createFormData("files", "image.jpg", requestFile)
            currentImageAvatar = image

        }
    }

    private fun setIntialData() {

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

    fun updateUser(userInfo: UserInfo){
        userInfo.firstName = binding.editFirstName.text.toString()
        userInfo.lastName = binding.editLastName.text.toString()

        userInfo.phone = binding.editPhone.text.toString()
        userInfo.address = binding.editAddress.text.toString()

        if (currentImageAvatar != null){
            userInfo.imgAvatar = loginViewModel.currentUrlImageAvatar
        }

        if (binding.rbMale.isChecked == true) userInfo.gender = 0
        else userInfo.gender = 1

        mSecurePreferences.setUserInfo(UserResponse(mSecurePreferences.getUserInfo().jwt,userInfo))

        loginViewModel.updateUser(userInfo.id,userInfo)

    }

    override fun onDialogResult(result: Boolean) {
        if (result){

            if (currentImageAvatar != null){
                loginViewModel.uploadImageAvatar(currentImageAvatar!!)
            }
            else {
                updateUser(currentUser)
            }

        }
    }
}