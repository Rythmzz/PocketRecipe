package com.oqq.pocketrecipe.view.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.recipe.AttributesRecipe
import com.oqq.pocketrecipe.data.model.recipe.RecipeRequest
import com.oqq.pocketrecipe.databinding.ActivityRecipeDetailPageBinding
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import com.transferwise.sequencelayout.SequenceStep
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.oqq.pocketrecipe.data.model.recipe.Process
import com.oqq.pocketrecipe.di.MyApplication
import kotlinx.coroutines.flow.collect

class ActivityRecipeDetailPage: AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailPageBinding

    private lateinit var recipe:AttributesRecipe

    private lateinit var listStep:List<SequenceStep>

    private val mSecurePreferences: AppPreferences by inject()

    private val loginViewModel:LoginViewModel by viewModel()

    private var firstThread:Job? = null

    private val recipeViewModel:RecipeViewModel by viewModel()

    private var countLike:Int = 0


    private var ingredient:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntialData()
        setStatus()
        setBehavior()
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success.collect{
                    result -> if (result is Boolean && result){
                    if (loginViewModel.detailUser!!.recipes.contains(recipe)){
                        binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_01)
                    }
                    else binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_02)
                    
                    if (loginViewModel.detailUser!!.likes.contains(recipe)){
                        binding.likeRecipe.setImageResource(R.drawable.ic_like_01)
                    }
                    else binding.likeRecipe.setImageResource(R.drawable.ic_like_02)
                }
                    else {
                        println(result.toString())
                }
                }


            }

            this.launch {
                recipeViewModel.success2.collect{
                        result -> if (result is Boolean && result){
                    countLike = recipeViewModel.currentLike
                    binding.countLike.setText("${countLike}")
                }
                else {
                    println(result.toString())

                }
                }
            }
        }
    }

    private fun setBehavior() {
        binding.back.setOnClickListener {
            finish()
        }

        binding.favoriteRecipe.setOnClickListener {
            if (loginViewModel.detailUser!!.recipes.contains(recipe)){
                binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_01)
                loginViewModel.detailUser!!.recipes.remove(recipe)
            }
            else {
                binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_02)
                loginViewModel.detailUser!!.recipes.add(recipe)
            }

            loginViewModel.updateUser(mSecurePreferences.getUserInfo().user.id,loginViewModel.detailUser!!)
        }
        
        binding.likeRecipe.setOnClickListener {
            if (loginViewModel.detailUser!!.likes.contains(recipe)){
                binding.favoriteRecipe.setImageResource(R.drawable.ic_like_01)
                loginViewModel.detailUser!!.likes.remove(recipe)
                binding.countLike.setText("${countLike-1}")
            }
            else {
                binding.favoriteRecipe.setImageResource(R.drawable.ic_like_02)
                loginViewModel.detailUser!!.likes.add(recipe)
                binding.countLike.setText("${countLike+1}")
            }

            loginViewModel.updateUser(mSecurePreferences.getUserInfo().user.id,loginViewModel.detailUser!!)
        }





        Glide.with(this).load("${MyApplication.url_local}${recipe.imgUrl}").into(binding.imgDetailFood)

        binding.textNameDetailFood.setText("${recipe.name}")
        binding.textMealDetailFood.setText("${recipe.meal}")
        binding.textBriefDetailFood.setText(recipe.brief)
        binding.textDescriptionDetailFood.setText(recipe.description)
        binding.textDurationDetailFood.setText("\uD83D\uDD52 ${recipe.duration} Phút")
        binding.textKcalDetailFood.setText("${recipe.kcal} Kcal")
        binding.textCountRecipeDetailFood.setText("\uD83D\uDCDD ${recipe.ingredients!!.size + 1} ")
        binding.textCountFamilyDetailFood.setText("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\u200D\uD83D\uDC66 ${recipe.count}")


        for (i:String in recipe.ingredients!!){
            ingredient += "⭐ $i\n"
            binding.textIngredientDetailFood.setText(ingredient)
        }









    }

    private fun setIntialData() {

    val intent = intent
    recipe = intent.getSerializableExtra("DETAIL_RECIPE") as AttributesRecipe


        val recipeCurrent = recipe
        recipeCurrent.view = recipeCurrent.view!! + 1
        val recipeRequest = RecipeRequest(recipeCurrent)
        recipeViewModel.updateRecipe(recipe.id!!,recipeRequest)

        recipeViewModel.getSpecificRecipe(recipe.id!!)
        loginViewModel.getUser(mSecurePreferences.getUserInfo().user.id)

        var processCount:Int = 1
        for (i in recipe.process!!){
            createStep(applicationContext,i,processCount)
            processCount++

        }




    }

    private fun createStep(context:Context, process:Process, position:Int){
        val sequenceStep = SequenceStep(context)
        sequenceStep.id = View.generateViewId() // Generate a unique ID for the step
        sequenceStep.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        sequenceStep.setAnchor("Bước ${position}")
        sequenceStep.setAnchorTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat)
        sequenceStep.setTitleTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Title)
        sequenceStep.setSubtitle(process.description)
        sequenceStep.setTitle(process.title)


        binding.layoutListStep.addView(sequenceStep)
    }


}