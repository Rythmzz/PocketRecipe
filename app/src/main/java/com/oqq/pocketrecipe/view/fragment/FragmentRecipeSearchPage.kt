package com.oqq.pocketrecipe.view.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.adapter.RecipeAdapter
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.databinding.FragmentRecipeSearchPageBinding
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.Normalizer


class FragmentRecipeSearchPage: Fragment() {
    private lateinit var binding:FragmentRecipeSearchPageBinding
    private lateinit var bottomNavigationView:BottomNavigationView
    private val navOptions:NavOptions by inject()

    private val recipeViewModel:RecipeViewModel by viewModel()

    private var recipes:List<Recipe> = arrayListOf()

    private var firstThread:Job? = null

    private lateinit var recipeAdapter:RecipeAdapter

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeSearchPageBinding.inflate(inflater,container,false)
        setInitialData()
        setStatus()
        setBahavior()
        return binding.root
    }

    fun removeDiacritics(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

    private fun setBahavior() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            @RequiresApi(api = Build.VERSION_CODES.N)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val filterString = s.toString()
                binding.tvSearch.setText("Search Result For Keyword \"$s\"")
                if (s.toString().isEmpty()) {
                    recipeAdapter.refreshData(recipes.toMutableList())
                } else {

                    val recipeFilteredSearch: List<Recipe> =
                        recipes.filter { data ->

                            removeDiacritics(data.attributes.name!!.toUpperCase()).contains(removeDiacritics(filterString.toUpperCase()))
                            }
                    recipeAdapter.refreshData(recipeFilteredSearch.toMutableList())
                    if (recipeFilteredSearch.isEmpty()){
                        binding.resultNotFound.visibility = View.VISIBLE
                    }
                    else {
                        binding.resultNotFound.visibility = View.GONE
                    }

                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setStatus() {
        firstThread = lifecycleScope.launchWhenStarted {
            this.launch {
                recipeViewModel.success.collect{
                    result -> if (result is Boolean && result){
                        recipes = recipeViewModel.currentListRecipe!!.data
                        recipeAdapter.refreshData(recipes.toMutableList())

                }
                    else {
                    println("Bi loi ${result.toString()}")
                }
                }
            }
            this.launch {
                recipeViewModel.loading.collect{
                    result -> if (result is Boolean && result){
                        binding.spinKit.visibility = View.VISIBLE
                    }
                    else {
                        binding.spinKit.visibility= View.GONE
                }
                }
            }
        }



    }

    private fun setInitialData() {
        binding.editSearch.requestFocus()
        binding.editSearch.showKeyboard()

        bottomNavigationView = activity!!.findViewById(R.id.bn_bottom)
        bottomNavigationView.visibility = View.GONE

        recipeViewModel.getListRecipe(0,-1)

        val layoutManager:LinearLayoutManager = LinearLayoutManager(activity)
        binding.listBenefitLayout.layoutManager = layoutManager

        recipeAdapter = RecipeAdapter(mutableListOf(),this,recipeViewModel)
        binding.listBenefitLayout.adapter = recipeAdapter



    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavigationView.visibility = View.VISIBLE
    }
}