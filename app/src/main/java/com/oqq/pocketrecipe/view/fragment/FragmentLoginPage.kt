package com.oqq.pocketrecipe.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.oqq.pocketrecipe.ActivityMainPage
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.databinding.FragmentLoginPageBinding
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLoginPage: Fragment() {

    private lateinit var binding:FragmentLoginPageBinding

    private val mSecurePreferences:AppPreferences by inject()

    private val loginViewModel:LoginViewModel by viewModel()
    private var firstThread:Job? = null


    private lateinit var layoutLoading: FrameLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStatus()
        setIntitalData()
        setBehavior()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginPageBinding.inflate(inflater,container,false)


        return binding.root
    }

    private fun setIntitalData() {
        layoutLoading = activity!!.findViewById(R.id.loading_layout)
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success.collect{
                    result ->
                    if (result is Boolean && result){
                           loginViewModel.user?.let {
                               println("INFO: ${loginViewModel.user.toString()}")
                               mSecurePreferences.setToken(it.jwt)
                               mSecurePreferences.setUserInfo(it)
                           }

                    val intent: Intent = Intent(activity,ActivityMainPage::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    activity!!.finish()
                    }
                    else {
                        Toast.makeText(activity,"Đăng nhập thất bại ${result.toString()}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            this.launch {
                loginViewModel.loading.collect{
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
        binding.btnLogin.setOnClickListener {
            if (binding.etUser.text.toString().isEmpty()){
                binding.etUser.error = "Không để trống tên đăng nhập"
            }
            else if (binding.etPassword.text.toString().isEmpty()){
                binding.etUser.error = "Không để trống mật khẩu"
            }
            else {

                loginViewModel.login(binding.etUser.text.toString(),binding.etPassword.text.toString())
            }
        }
    }
}