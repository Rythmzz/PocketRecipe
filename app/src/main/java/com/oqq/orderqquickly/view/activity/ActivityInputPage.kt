package com.oqq.orderqquickly.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.oqq.orderqquickly.R
import com.oqq.orderqquickly.databinding.ActivityInputPageBinding
import com.oqq.orderqquickly.listener.RegisterListener
import com.oqq.orderqquickly.view.viewmodel.RegisterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityInputPage: AppCompatActivity() {

    private lateinit var binding:ActivityInputPageBinding
    private var isLogin:Boolean = true


    private val navOptions:NavOptions by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
                ?: return
        val navControll = navHost.navController
        setStatus()
        setAction(navControll)
    }

    private fun setStatus() {

    }

    fun setThemeLogin(){
        binding.textHeader.text = "Đăng Nhập"
        binding.textLbLogin.setTextColor(Color.parseColor("#FFA500"))
        binding.textLbRegister.setTextColor(Color.parseColor("#A6A6A6"))
        binding.dividerOfLogin.visibility = View.VISIBLE
        binding.dividerOfRegister.visibility = View.GONE

        isLogin = true
    }

    private fun setThemeRegister(){
        binding.textHeader.text = "Tạo Một Tài Khoản"
        binding.textLbRegister.setTextColor(Color.parseColor("#FFA500"))
        binding.textLbLogin.setTextColor(Color.parseColor("#A6A6A6"))
        binding.dividerOfLogin.visibility = View.GONE
        binding.dividerOfRegister.visibility = View.VISIBLE

        isLogin = false
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (!isLogin){
            setThemeLogin()
            isLogin = true
        }

    }

    private fun setAction(navController: NavController) {
        binding.labelLogin.setOnClickListener {
            if (!isLogin){
                setThemeLogin()


                navController.navigate(R.id.action_fragment_login_dest_to_fragment_register_dest,null)

            }

        }

        binding.labelRegister.setOnClickListener {
            if (isLogin){
                setThemeRegister()


                navController.navigate(R.id.action_fragment_login_dest_to_fragment_register_dest,null,navOptions)
            }
        }
    }



}