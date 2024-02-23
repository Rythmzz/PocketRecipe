package com.oqq.pocketrecipe.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.SliderAdapter
import com.oqq.pocketrecipe.data.model.ArticleSlide
import com.oqq.pocketrecipe.databinding.FragmentMenuRestaurantPageBinding
import org.koin.android.ext.android.inject

class FragmentMenuRestaurantPage : Fragment() {

    private lateinit var binding:FragmentMenuRestaurantPageBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var imageList:MutableList<ArticleSlide>

    private val navOptions: NavOptions by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuRestaurantPageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()
        return binding.root
    }

    private fun changeCategory(title:TextView, divider:View, layoutCategory:LinearLayout?){
        clearColorTitle()
        clearLayoutCategory()
        clearDivider()
        title.setTextColor(Color.parseColor("#FFA500"))
        divider.visibility = View.VISIBLE
        if (layoutCategory != null) layoutCategory.visibility = View.VISIBLE
        else showLayoutCategory()

    }

    private fun clearDivider() {
        binding.dividerPopular.visibility = View.GONE
        binding.dividerSpecial.visibility = View.GONE
        binding.dividerMainFood.visibility = View.GONE
        binding.dividerSubFood.visibility = View.GONE
        binding.dividerAll.visibility = View.GONE
    }

    private fun clearLayoutCategory() {
        binding.layoutCategoryPopular.visibility = View.GONE
        binding.layoutCategorySpecial.visibility = View.GONE
        binding.layoutCategoryMainFood.visibility = View.GONE
        binding.layoutCategorySubFood.visibility = View.GONE
        
    }

    private fun showLayoutCategory() {
        binding.layoutCategoryPopular.visibility = View.VISIBLE
        binding.layoutCategorySpecial.visibility = View.VISIBLE
        binding.layoutCategoryMainFood.visibility = View.VISIBLE
        binding.layoutCategorySubFood.visibility = View.VISIBLE

    }

    private fun clearColorTitle() {
        binding.textPopular.setTextColor(Color.parseColor("#C5C5C5"))
        binding.textSpecial.setTextColor(Color.parseColor("#C5C5C5"))
        binding.textMainFood.setTextColor(Color.parseColor("#C5C5C5"))
        binding.textSubFood.setTextColor(Color.parseColor("#C5C5C5"))
        binding.textAll.setTextColor(Color.parseColor("#C5C5C5"))
    }

    private fun setBehavior() {
        binding.textPopular.setOnClickListener {
            changeCategory(binding.textPopular,binding.dividerPopular,binding.layoutCategoryPopular)
        }

        binding.textSpecial.setOnClickListener {
            changeCategory(binding.textSpecial,binding.dividerSpecial,binding.layoutCategorySpecial)
        }

        binding.textMainFood.setOnClickListener {
            changeCategory(binding.textMainFood,binding.dividerMainFood,binding.layoutCategoryMainFood)
        }

        binding.textSubFood.setOnClickListener {
            changeCategory(binding.textSubFood,binding.dividerSubFood,binding.layoutCategorySubFood)
        }

        binding.textAll.setOnClickListener {
            changeCategory(binding.textAll,binding.dividerAll,null)

        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_menu_dest_to_fragment_menu_restaurant_dest,null)
        }

        binding.food.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_menu_restaurant_dest_to_fragment_food_detail_dest,null,navOptions)
        }
    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        binding.layoutCategorySpecial.visibility = View.GONE
        binding.layoutCategoryMainFood.visibility = View.GONE
        binding.layoutCategorySubFood.visibility = View.GONE

        imageList = mutableListOf(
            ArticleSlide("https://kfcvietnam.com.vn/kfc-tabs/promotion-details/trua-nay-an-gi","https://static.kfcvietnam.com.vn/710x470%20%20BTTK)_KO%20KT%20(7%20PHAN).jpg",
            "Trưa Nay Ăn Gì?",""),
            ArticleSlide("https://kfcvietnam.com.vn/kfc-tabs/promotion-details/ga-que-kem","https://static.kfcvietnam.com.vn/gaquekem_articel.jpg",
            "GÀ QUE KEM VOL.2 – ĐÃ “HOT” NAY CÒN NÓNG HƠN!!!","Gà Que Kem Xốt Cajun Phủ Ớt Cựa Gà Xông Khói"),
            ArticleSlide("https://kfcvietnam.com.vn/kfc-tabs/promotion-details/xuan-giap-thin-tiec-linh-dinh","https://static.kfcvietnam.com.vn/710x470-01.jpg",
            "Xuân Giáp Thìn – Tiệc Linh Đình!","Cơ hội nhận ngay quà tặng năm mới cực chất cho cả năm")
        )

        binding.imageSlider.setSliderAdapter(SliderAdapter(imageList))
        binding.imageSlider.startAutoCycle()
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}