package com.oqq.pocketrecipe.view.fragment.main

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.model.client.UserData
import com.oqq.pocketrecipe.databinding.FragmentRegisterPageBinding
import com.oqq.pocketrecipe.view.activity.ActivityInputPage
import com.oqq.pocketrecipe.view.viewmodel.RegisterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegisterPage: Fragment() {

    private lateinit var binding: FragmentRegisterPageBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    private var intialThread: Job? = null

    private lateinit var layoutLoading: FrameLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPageBinding.inflate(inflater,container,false)
        setIntialData()
        setStatus()
        setBehavior()
        return binding.root
    }

    private fun setIntialData() {
        layoutLoading = activity!!.findViewById(R.id.loading_layout)

    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.split("@").last().contains(".")
    }

    private fun setStatus() {
        intialThread =lifecycleScope.launchWhenStarted {
            this.launch {
                registerViewModel.success.collect{
                    result ->
                    if (result is UserData){
                        Toast.makeText(activity,"Đăng ký thành công", Toast.LENGTH_LONG).show()
                        (activity as? ActivityInputPage)?.setThemeLogin()
                        findNavController().navigate(R.id.action_fragment_login_dest_to_fragment_register_dest,null)

                    }
                    else {
                        Toast.makeText(activity,"Đăng ký thất bại : $result", Toast.LENGTH_LONG).show()
                    }

                }
            }

            this.launch {
                registerViewModel.loading.collect{
                    if (it is Boolean && it){
                       layoutLoading.visibility = View.VISIBLE
                    }
                    else {
                        layoutLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setBehavior() {
        binding.checkBoxPolicy.setOnCheckedChangeListener { _, b ->
            binding.btnRegister.isEnabled = b
        }

        binding.btnRegister.setOnClickListener {
            if (binding.etCrLastName.text.toString().isEmpty()){
                binding.etCrLastName.error = "Họ không được để trống"
            }
            else if (binding.etCrFirstName.text.toString().isEmpty()){
                binding.etCrFirstName.error = "Tên không được để trống"
            }
            else if (binding.etCrUsername.text.toString().isEmpty()){
                binding.etCrUsername.error = "Username không được để trống"
            }
            else if (binding.etCrEmail.text.toString().isEmpty()){
                binding.etCrEmail.error = "Email không được để trống"
            }
            else if (!isValidEmail(binding.etCrEmail.text.toString())){
                binding.etCrEmail.error = "Vui lòng nhập đúng định dạng email"
            }
            else if (binding.etCrPassword.text.toString().isEmpty()){
                binding.etCrPassword.error = "Mật khẩu không được để trống"
            }
            else if (binding.etConfPassword.text.toString().isEmpty()){
                binding.etConfPassword.error = "Mật khẩu xác nhận không được để trống"
            }
            else if (binding.etConfPassword.text.toString() != binding.etCrPassword.text.toString()){
                binding.etConfPassword.error = "Mật khẩu xác nhận không khớp"
            }
            else {
                val userData = UserData(
                    binding.etCrUsername.text.toString(),
                    binding.etCrEmail.text.toString(),
                binding.etCrPassword.text.toString(),
                binding.etCrFirstName.text.toString(),
                binding.etCrLastName.text.toString(),null)
                registerViewModel.register(userData)
            }
        }
    }
}