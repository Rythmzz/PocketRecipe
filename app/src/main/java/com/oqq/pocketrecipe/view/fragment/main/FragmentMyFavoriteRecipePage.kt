package com.oqq.pocketrecipe.view.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.RecipeFavoriteAdapter
import com.oqq.pocketrecipe.data.local.AppPreferences
import com.oqq.pocketrecipe.data.model.recipe.AttributesRecipe
import com.oqq.pocketrecipe.databinding.FragmentMyFavoriteRecipePageBinding
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentMyFavoriteRecipePage: Fragment() {
    private lateinit var binding:FragmentMyFavoriteRecipePageBinding

    private val loginViewModel:LoginViewModel by viewModel()

    private lateinit var adapter:RecipeFavoriteAdapter

    private var listRecipeFavorite:List<AttributesRecipe> = arrayListOf()

    private var firstThread:Job? = null

    private val mSecurePreferences: AppPreferences by inject()

    private val navOptions :NavOptions by inject()

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var badge: BadgeDrawable

    private lateinit var bottomAppBar: BottomAppBar

    private lateinit var floatingActionButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFavoriteRecipePageBinding.inflate(inflater,container,false)
        setStatus()
        setIntialData()
        setBahavior()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success.collect{
                    result -> if (result is Boolean && result){
                    badge.isVisible = true
                    badge.number = loginViewModel.detailUser!!.recipes.size
                    listRecipeFavorite = loginViewModel.detailUser!!.recipes
                    adapter.refreshData(listRecipeFavorite.toMutableList())
                    binding.countRecipe.text = "${listRecipeFavorite.size} công thức khác nhau được lưu trữ"
                    if (listRecipeFavorite.isEmpty()){
                        binding.nothing.visibility = View.VISIBLE
                    }
                    else binding.nothing.visibility = View.GONE
                }
                    else {
                        println("LOI ROI: $result")
                }
                }
            }
        }
    }

    private fun setIntialData() {
        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)

        floatingActionButton = activity!!.findViewById(R.id.add_btn)
        bottomAppBar = activity!!.findViewById(R.id.bottom_appbar)

        badge =  bottomNavigationView.getOrCreateBadge(R.id.fragment_my_favorite_recipe_dest)
        adapter = RecipeFavoriteAdapter(listRecipeFavorite.toMutableList(),this,loginViewModel)

        binding.listFavoriteRecipe.adapter = adapter

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listFavoriteRecipe.layoutManager = layoutManager

        if (mSecurePreferences.getToken() != null){
            loginViewModel.getUser(mSecurePreferences.getUserInfo().user.id)
        }
    }

    private fun setBahavior() {
        binding.seeNow.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_my_favorite_recipe_dest_to_fragment_recipe_all_dest,null,navOptions)
        }

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

    }
}