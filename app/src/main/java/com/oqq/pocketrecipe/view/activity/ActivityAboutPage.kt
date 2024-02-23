package com.oqq.pocketrecipe.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oqq.pocketrecipe.ActivityMainPage
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.databinding.ActivityAboutPageBinding
import com.oqq.pocketrecipe.listener.DialogResultListener
import com.oqq.pocketrecipe.view.dialog.CustomDialog
import com.oqq.pocketrecipe.view.fragment.FragmentAbout01
import com.oqq.pocketrecipe.view.fragment.FragmentAbout02
import com.oqq.pocketrecipe.view.fragment.FragmentAbout03
import org.koin.android.ext.android.inject

class ActivityAboutPage: AppCompatActivity(), DialogResultListener {
    private lateinit var binding:ActivityAboutPageBinding
    private lateinit var fragments:ArrayList<Fragment>
    private val mSecurePreferences:AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        skipTheme()
        setTitleUI()
        setFragment()
        setAdapter()
        setBehavior()

    }

    private fun skipTheme() {
        val check:Boolean = mSecurePreferences.getFirstTheme()
        if (!check){
            nextHomePage()
        }
        }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0 ){
            super.onBackPressed()
        }
        else {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1,true)
        }
    }

    private fun nextHomePage(){
        mSecurePreferences.setFirstTheme(false)
        val intent: Intent = Intent(this,ActivityMainPage::class.java)
        startActivity(intent)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        finish()
    }

    private fun setBehavior() {
        binding.next.setOnClickListener {
            if (binding.viewPager.currentItem == fragments.size -1){
                nextHomePage()
            }
            else{
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1,true)
            }
        }

        binding.skip.setOnClickListener {
            val dialog = CustomDialog(this,this,"Bỏ Qua Phần Giới Thiệu","Bạn có chắc muốn bỏ qua phần giới thiệu?")
            dialog.show()
        }
    }

    private fun setAdapter() {
        val adapterViewPage = object: FragmentStateAdapter(this){
            override fun getItemCount(): Int {
               return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
               return fragments[position]
            }

        }
        binding.viewPager.adapter = adapterViewPage
        binding.dosIndicator.setViewPager2(binding.viewPager)
    }

    private fun setFragment() {
        fragments = arrayListOf(
            FragmentAbout01(),FragmentAbout02(), FragmentAbout03()
        )
    }

    private fun setTitleUI() {
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color_01)
    }

    override fun onDialogResult(result: Boolean) {
        if (result){
            nextHomePage()
        }
        else {
        }
    }
}