package com.oqq.pocketrecipe.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.model.recipe.Recipe
import com.oqq.pocketrecipe.databinding.SearchRecipeItemBinding
import com.oqq.pocketrecipe.view.activity.ActivityRecipeDetailPage
import com.oqq.pocketrecipe.view.viewmodel.RecipeViewModel

class RecipeAdapter(private var listRecipe:MutableList<Recipe>, private val fragment:Fragment, private val recipeViewModel: RecipeViewModel): RecyclerView.Adapter<RecipeAdapter.SearchViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newRecipes:MutableList<Recipe>){
        listRecipe = newRecipes
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding:SearchRecipeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Recipe){
            val detailItem = item.attributes
            detailItem.id = item.id
            Glide.with(itemView.context).load("${fragment.getString(R.string.url_connect)}${detailItem.imgUrl}").into(binding.imageRecipe)
            binding.nameRecipe.setText(detailItem.name)
            binding.countRecipe.setText("${detailItem.count.toString()}")
            binding.timeRecipe.setText("${detailItem.duration} Phút")
            binding.kcalRecipe.setText("${detailItem.kcal} Kcal")

            binding.layoutRecipe.setOnClickListener {

                val intent:Intent = Intent(itemView.context,ActivityRecipeDetailPage::class.java)
                intent.putExtra("DETAIL_RECIPE",detailItem)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchRecipeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = listRecipe[position]
        holder.bind(item)
    }
}