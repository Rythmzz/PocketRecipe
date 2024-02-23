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
import com.oqq.pocketrecipe.databinding.ItemRecommendRecipeBinding
import com.oqq.pocketrecipe.view.activity.ActivityRecipeDetailPage

class RecipeRecommendAdapter(private var listRecipe:MutableList<Recipe>, private val fragment:Fragment): RecyclerView.Adapter<RecipeRecommendAdapter.RecommendViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newRecipes:MutableList<Recipe>){
        listRecipe = newRecipes
        notifyDataSetChanged()
    }

    inner class RecommendViewHolder(private val binding:ItemRecommendRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Recipe){
            val detailItem = item.attributes
            detailItem.id = item.id
            Glide.with(itemView.context).load("${fragment.getString(R.string.url_connect)}${detailItem.imgUrl}").into(binding.imgRecipe)
            binding.nameRecipe.setText(detailItem.name)
            binding.mealRecipe.setText("Món: ${detailItem.meal}")
            binding.viewRecipe.setText(detailItem.view.toString())

            binding.layoutRecipe.setOnClickListener {
                val intent:Intent = Intent(itemView.context,ActivityRecipeDetailPage::class.java)
                intent.putExtra("DETAIL_RECIPE",detailItem)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding = ItemRecommendRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecommendViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (listRecipe.size < 8) return listRecipe.size
        else return 8
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val item = listRecipe[position]
        holder.bind(item)
    }
}