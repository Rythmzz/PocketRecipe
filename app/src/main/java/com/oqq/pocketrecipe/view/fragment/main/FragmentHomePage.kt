package com.oqq.pocketrecipe.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.makeramen.roundedimageview.RoundedImageView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.RecipeRecommendAdapter
import com.oqq.pocketrecipe.adapter.RecipeRecommendHealthyAdapter
import com.oqq.pocketrecipe.adapter.SliderFeatureAdapter
import com.oqq.pocketrecipe.adapter.SliderRecipeAdapter
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.other.FeatureSlide
import com.oqq.pocketrecipe.data.model.client.UserInfo
import com.oqq.pocketrecipe.data.model.recipe.Meal
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.databinding.FragmentHomePageBinding
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHomePage: Fragment() {
    private lateinit var binding:FragmentHomePageBinding
    private lateinit var listFeature:MutableList<FeatureSlide>

    private val mSecurePreferences: AppPreferences by inject()

    private var firstThread: Job? = null

    private val recipeViewModel:RecipeViewModel by viewModel()

    private val navOptions: NavOptions by inject()

    private var recipes:List<Recipe> = arrayListOf()

    private val loginViewModel:LoginViewModel by viewModel()

    private lateinit var userInfo:UserInfo

    private lateinit var sliderRecipeAdapter:SliderRecipeAdapter

    private lateinit var recommendRecipeAdapter:RecipeRecommendAdapter

    private var currentListRecommendRecipe:MutableList<Recipe> = mutableListOf()

    private lateinit var recommendHealthyRecipeAdapter:RecipeRecommendHealthyAdapter

    private var currentListHealthyRecipe:MutableList<Recipe> = mutableListOf()

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var badge:BadgeDrawable

    private lateinit var bottomAppBar: BottomAppBar

    private lateinit var floatingActionButton: FloatingActionButton


    private var meals:MutableList<Meal> = mutableListOf(
        Meal(R.drawable.img_meal_kv,"Khai vị",null),
            Meal(R.drawable.img_meal_c,"Chính",null),
                Meal(R.drawable.img_meal_kem,"Ăn kèm",null),
                    Meal(R.drawable.img_meal_tm,"Tráng miệng",null),
                        Meal(R.drawable.img_meal_phu,"Phụ",null),
                                Meal(R.drawable.img_meal_dd,"Dinh dưỡng",null),
                                            Meal(R.drawable.img_meal_drink,"Đồ uống",null)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateHeaderInfo(){
        val currentNavigationView = activity!!.findViewById<NavigationView>(R.id.nav_view)
        val headerView = currentNavigationView.getHeaderView(0)
        val imageAvatar = headerView.findViewById<RoundedImageView>(R.id.img_avatar)
        val fullName = headerView.findViewById<TextView>(R.id.name)
        val email = headerView.findViewById<TextView>(R.id.email)

        Glide.with(this).load("${getString(R.string.url_connect)}${mSecurePreferences.getUserInfo().user.imgAvatar}").into(imageAvatar)
        fullName.text = mSecurePreferences.getUserInfo().user.lastName + " " + mSecurePreferences.getUserInfo().user.firstName
        email.text = mSecurePreferences.getUserInfo().user.email
    }



    override fun onStop() {
        super.onStop()

        floatingActionButton.visibility = View.GONE
        bottomAppBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

        floatingActionButton.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE

        recipeViewModel.getListRecipe(0,-1)

        if (mSecurePreferences.getToken() != null){
            loginViewModel.getUser(mSecurePreferences.getUserInfo().user.id)

            updateHeaderInfo()
        }

    }



    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success.collect{
                    result -> if (result is Boolean && result){
                        userInfo = loginViewModel.detailUser!!
                        badge.isVisible = true
                        badge.number = loginViewModel.detailUser!!.recipes.size


                    this.launch {
                        recipeViewModel.success.collect{result ->
                            if (result is Boolean && result){
                                recipes = recipeViewModel.currentListRecipe!!.data

                                currentListRecommendRecipe = recipes.shuffled().take(8).toMutableList()

                                recommendRecipeAdapter.refreshData(currentListRecommendRecipe)

                                currentListHealthyRecipe = recipes.filter { it.attributes.meal!!.contains("Dinh Dưỡng", ignoreCase = true)}.toMutableList()

                                recommendHealthyRecipeAdapter.refreshData(currentListHealthyRecipe)


                                for (i in meals){
                                    i.recipe = recipes.filter { it.attributes.meal.equals(i.name)}
                                }

                                recipes[0].attributes.id = recipes[0].id
                                recipes[1].attributes.id = recipes[1].id
                                recipes[2].attributes.id = recipes[2].id
                                recipes[3].attributes.id = recipes[3].id


                                sliderRecipeAdapter.refreshData(meals)



                            }
                            else {
                                println("Bi loi $result")
                            }
                        }
                    }

                }
                    else {
                    println("Bi loi $result")
                }
                }

            }

        }
    }

    private fun setSlideImage() {


    }

    private fun setIntialData() {

        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)

        floatingActionButton = activity!!.findViewById(R.id.add_btn)
        bottomAppBar = activity!!.findViewById(R.id.bottom_appbar)

        badge = bottomNavigationView.getOrCreateBadge(R.id.fragment_my_favorite_recipe_dest)


        listFeature = mutableListOf(
            FeatureSlide("Thêm và sáng tạo công thức của chính bạn",R.drawable.img_thumb_01),
            FeatureSlide("Khám phá nhiều loại công thức khác nhau",R.drawable.img_thumb_02)
        )

        binding.sliderFeature.setSliderAdapter(SliderFeatureAdapter(navOptions,this,listFeature))
        binding.sliderFeature.startAutoCycle()

        sliderRecipeAdapter = SliderRecipeAdapter(meals)

        binding.sliderRecipeRandom.setSliderAdapter(sliderRecipeAdapter)

        binding.sliderRecipeRandom.startAutoCycle()

        setAdapter()





    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setIntialData()
        setSlideImage()
        setStatus()
        setBehavior()
        setFragment()
    }
    private fun setAdapter() {
        recommendRecipeAdapter = RecipeRecommendAdapter(currentListRecommendRecipe,this)

        recommendHealthyRecipeAdapter = RecipeRecommendHealthyAdapter(currentListHealthyRecipe,this,loginViewModel)

        binding.layoutListRecommendRecipe.adapter = recommendRecipeAdapter

        binding.layoutListHealthyRecipe.adapter = recommendHealthyRecipeAdapter



        val layoutManagerRecommendRecipe = LinearLayoutManager(activity)
        layoutManagerRecommendRecipe.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManagerRecommendHealthyRecipe = LinearLayoutManager(activity)
        layoutManagerRecommendHealthyRecipe.orientation = LinearLayoutManager.HORIZONTAL

        binding.layoutListRecommendRecipe.layoutManager = layoutManagerRecommendRecipe

        binding.layoutListHealthyRecipe.layoutManager = layoutManagerRecommendHealthyRecipe
    }

    private fun setFragment() {

    }

    private fun setBehavior() {
        binding.editSearch.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_recipe_search_dest,null,navOptions)
        }

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_add_recipe_detail_dest,null,navOptions)
        }

        binding.textSeeAll01.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_recipe_all_dest,null,navOptions)
        }

        binding.textSeeAll02.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_recipe_healthy_page,null,navOptions)
            // món có lợi cho sức khỏe
        }


    }
}