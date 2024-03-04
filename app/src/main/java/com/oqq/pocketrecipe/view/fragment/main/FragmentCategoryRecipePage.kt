package com.oqq.pocketrecipe.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.CategoryAdapter
import com.oqq.pocketrecipe.data.model.recipe.Category
import com.oqq.pocketrecipe.databinding.FragmentCategoryRecipePageBinding
import org.koin.android.ext.android.inject

class FragmentCategoryRecipePage: Fragment(){
    private lateinit var binding:FragmentCategoryRecipePageBinding

    private lateinit var adapter:CategoryAdapter

    private val navOptions:NavOptions by inject()

    private val listCategory:List<Category> = arrayListOf(
        Category("Bữa Chính", R.drawable.cate_04),
        Category("Bữa Phụ", R.drawable.cate_02),
        Category("Đồ Uống", R.drawable.cate_03),
        Category("Ăn Kèm", R.drawable.cate_01),
        Category("Khai Vị", R.drawable.cate_05),
        Category("Tráng Miệng", R.drawable.cate_06),
        Category("Dinh Dưỡng", R.drawable.cate_07),
    )

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentCategoryRecipePageBinding.inflate(inflater,container,false)
        setIntialData()
        setBehavior()

        return binding.root
    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setIntialData() {
        adapter = CategoryAdapter(listCategory,this,navOptions)

        binding.listCategoryRecipe.adapter = adapter

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listCategoryRecipe.layoutManager = layoutManager

        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }

}