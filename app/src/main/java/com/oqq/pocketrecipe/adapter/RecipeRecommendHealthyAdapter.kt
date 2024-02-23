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
import com.oqq.pocketrecipe.databinding.ItemHealthyRecipeBinding
import com.oqq.pocketrecipe.view.activity.ActivityRecipeDetailPage
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel

class RecipeRecommendHealthyAdapter(private var listRecipe:MutableList<Recipe>, private val fragment:Fragment, private val loginViewModel: LoginViewModel): RecyclerView.Adapter<RecipeRecommendHealthyAdapter.RecommendViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newRecipes:MutableList<Recipe>){
        listRecipe = newRecipes
        notifyDataSetChanged()
    }

    inner class RecommendViewHolder(private val binding:ItemHealthyRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Recipe){
            val detailItem = item.attributes
            detailItem.id = item.id
            Glide.with(itemView.context).load("${fragment.getString(R.string.url_connect)}${detailItem.imgUrl}").into(binding.imgRecipe)
            binding.nameRecipe.setText(detailItem.name)
            val infoUser = loginViewModel.detailUser!!
            if (infoUser.recipes.contains(detailItem)){
                binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_01)
            }
            else binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_02)

            binding.layoutRecipe.setOnClickListener {
                val intent:Intent = Intent(itemView.context,ActivityRecipeDetailPage::class.java)
                intent.putExtra("DETAIL_RECIPE",detailItem)
                itemView.context.startActivity(intent)
            }

            binding.favoriteRecipe.setOnClickListener {

                if (infoUser.recipes.contains(detailItem)){
                    infoUser.recipes.remove(detailItem)
                    binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_02)
                }
                else {
                    infoUser.recipes.add(detailItem)
                    binding.favoriteRecipe.setImageResource(R.drawable.ic_heart_01)

                }
                loginViewModel.updateUser(infoUser.id,infoUser)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding = ItemHealthyRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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