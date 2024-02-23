package com.oqq.pocketrecipe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var bottomNavigationView: BottomNavigationView

    private val loginViewModel:LoginViewModel by viewModel()

    private lateinit var adapter:RecipeFavoriteAdapter

    private var listRecipeFavorite:List<AttributesRecipe> = arrayListOf()

    private var firstThread:Job? = null

    private val mSecurePreferences: AppPreferences by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyFavoriteRecipePageBinding.inflate(inflater,container,false)
        setStatus()
        setIntialData()
        setBahavior()
        return binding.root
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                loginViewModel.success.collect{
                    result -> if (result is Boolean && result){
                        println("HELLO GUY: ${loginViewModel.detailUser.toString()}")
                    listRecipeFavorite = loginViewModel.detailUser!!.recipes
                    adapter.refreshData(listRecipeFavorite.toMutableList())
                    binding.countRecipe.setText("${listRecipeFavorite.size} công thức khác nhau được lưu trữ")
                    if (listRecipeFavorite.isEmpty()){
                        binding.nothing.visibility = View.VISIBLE
                    }
                    else binding.nothing.visibility = View.GONE
                }
                    else {
                        println("LOI ROI: ${result.toString()}")
                }
                }
            }
        }
    }

    private fun setIntialData() {
//        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
//        bottomNavigationView.visibility = View.GONE
        adapter = RecipeFavoriteAdapter(listRecipeFavorite.toMutableList(),this,loginViewModel)

        binding.listFavoriteRecipe.adapter = adapter

        val layoutManager:LinearLayoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.listFavoriteRecipe.layoutManager = layoutManager

        if (mSecurePreferences.getToken() != null){
            loginViewModel.getUser(mSecurePreferences.getUserInfo().user.id)
        }
    }

    private fun setBahavior() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_back_home,null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        bottomNavigationView.visibility = View.VISIBLE
    }
}