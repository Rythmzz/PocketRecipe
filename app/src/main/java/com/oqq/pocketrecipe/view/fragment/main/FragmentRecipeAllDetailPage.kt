package com.oqq.pocketrecipe.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.oqq.pocketrecipe.adapter.RecipeAdapter
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.databinding.FragmentRecipeAllDetailPageBinding
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRecipeAllDetailPage: Fragment() {
    private lateinit var binding:FragmentRecipeAllDetailPageBinding

    private val recipeViewModel:RecipeViewModel by viewModel()

    private var firstThread: Job? = null

    private val safeArgs: FragmentRecipeAllDetailPageArgs by navArgs()

    private lateinit var recipeAdapter:RecipeAdapter

    private var recipes:List<Recipe> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeAllDetailPageBinding.inflate(inflater,container,false)
        setIntialData()
        setStatus()
        setBehavior()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                recipeViewModel.success.collect{
                    result -> if (result is Boolean && result){
                    recipes = recipeViewModel.currentListRecipe!!.data
                    recipeAdapter.refreshData(recipes.toMutableList())
                    if (recipeViewModel.countMainRecipe != 0){
                        binding.countRecipe.text = "${recipeViewModel.countMainRecipe} ý tưởng công thức"
                    }
                    else if (recipeViewModel.countSubRecipe != 0){
                        binding.countRecipe.text = "${recipeViewModel.countSubRecipe} ý tưởng công thức"

                    }
                    else if (recipeViewModel.countOther != 0){
                        binding.countRecipe.text = "${recipeViewModel.countOther} ý tưởng công thức"

                    }
                    else {
                        binding.countRecipe.text = "${recipes.size} ý tưởng công thức"

                    }

                }

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setIntialData() {
        recipeAdapter = RecipeAdapter(mutableListOf(),this)
        binding.listRecipeLayout.adapter = recipeAdapter

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listRecipeLayout.layoutManager = layoutManager

        when(safeArgs.idMeal){
            0 -> {
                binding.title.text = "Công Thức Bữa Chính"
                recipeViewModel.getListRecipeMeal("Chính")
            }
            1 -> {
                binding.title.text = "Công Thức Bữa Phụ"
                recipeViewModel.getListRecipeMeal("Phụ")
            }
            2 -> {
                binding.title.text = "Công Thức Đồ Uống"
                recipeViewModel.getListRecipeMeal("Đồ uống")
            }
            3 -> {
                binding.title.text = "Công Thức Ăn Kèm"
                recipeViewModel.getListRecipeMeal("Ăn kèm")
            }
            4 -> {
                binding.title.text = "Công Thức Khai Vị"
                recipeViewModel.getListRecipeMeal("Khai vị")
            }
            5 -> {
                binding.title.text = "Công Thức Tráng Miệng"
                recipeViewModel.getListRecipeMeal("Tráng miệng")
            }
            6 -> {
                binding.title.text = "Công Thức Dinh Dưỡng"
                recipeViewModel.getListRecipeMeal("Dinh dưỡng")
            }
            else -> {
                binding.title.text = "Công Thức Món Khác"
                recipeViewModel.getListNotContainRecipeMeal("Chính")
            }
        }




    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}