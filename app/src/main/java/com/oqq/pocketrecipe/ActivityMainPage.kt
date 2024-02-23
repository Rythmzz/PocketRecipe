package com.oqq.pocketrecipe

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.databinding.ActivityMainPageBinding
import com.oqq.pocketrecipe.listener.DialogResultListener
import com.oqq.pocketrecipe.view.activity.ActivityInputPage
import com.oqq.pocketrecipe.view.dialog.CustomDialog
import org.koin.android.ext.android.inject

class ActivityMainPage: AppCompatActivity(), DialogResultListener {
    private lateinit var binding:ActivityMainPageBinding

    private val mSecurePreferences:AppPreferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLogin()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = Color.parseColor("#000000")
//        }

        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
                ?: return

        val navController = navHost.navController

        setupNavigationMenu(navController)

        binding.bnBottom.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.logout_dest -> {
                        val dialog:CustomDialog = CustomDialog(this@ActivityMainPage,this@ActivityMainPage,
                        "Đăng xuất","Bạn có muốn đăng xuất khỏi tài khoản?")
                        dialog.show()
                    }

                }
                return true
            }


        })
    }




    private fun checkLogin() {
        val token = mSecurePreferences.getToken()
        if (token == null){
            var intent: Intent = Intent(this, ActivityInputPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }




    private fun setupNavigationMenu(navController:NavController){
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    override fun onDialogResult(result: Boolean) {
        if (result){
            mSecurePreferences.setToken(null)
            mSecurePreferences.setUserInfo(null)
            val intent: Intent = Intent(this@ActivityMainPage, ActivityInputPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}