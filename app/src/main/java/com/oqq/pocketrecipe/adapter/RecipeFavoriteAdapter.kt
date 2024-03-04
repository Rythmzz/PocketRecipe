package com.oqq.pocketrecipe.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.model.recipe.AttributesRecipe
import com.oqq.pocketrecipe.databinding.ItemFavoriteRecipeBinding
import com.oqq.pocketrecipe.view.activity.ActivityRecipeDetailPage
import com.oqq.pocketrecipe.view.viewmodel.LoginViewModel

class RecipeFavoriteAdapter(private var listRecipe:MutableList<AttributesRecipe>, private val fragment:Fragment, private val loginViewModel: LoginViewModel): RecyclerView.Adapter<RecipeFavoriteAdapter.SearchViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newRecipes:MutableList<AttributesRecipe>){
        listRecipe = newRecipes
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding:ItemFavoriteRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item:AttributesRecipe){
            Glide.with(itemView.context).load("${fragment.getString(R.string.url_connect)}${item.imgUrl}").into(binding.imageRecipe)
            binding.nameRecipe.text = item.name
            binding.countRecipe.text = item.count.toString()
            binding.timeRecipe.text = "${item.duration} Phút"
            binding.kcalRecipe.text = "${item.kcal} Kcal"

            binding.favoriteRecipe.setOnClickListener {
                val infoUser = loginViewModel.detailUser!!
                if (infoUser.recipes.contains(item)){
                    infoUser.recipes.remove(item)
                    Toast.makeText(itemView.context,"Đã loại bỏ công thức khỏi danh sách yêu thích của bạn!",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(itemView.context,"Đã thêm công thức vào danh sách yêu thích của bạn!",Toast.LENGTH_SHORT).show()
                }
                loginViewModel.updateUser(infoUser.id,infoUser)
            }
            binding.layoutRecipe.setOnClickListener {
                val intent = Intent(itemView.context,ActivityRecipeDetailPage::class.java)
                intent.putExtra("DETAIL_RECIPE", item)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemFavoriteRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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