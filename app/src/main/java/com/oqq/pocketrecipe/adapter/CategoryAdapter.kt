package com.oqq.pocketrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.oqq.pocketrecipe.data.model.recipe.Category
import com.oqq.pocketrecipe.databinding.ItemCategoryRecipeBinding
import com.oqq.pocketrecipe.view.fragment.FragmentCategoryRecipePageDirections

class CategoryAdapter(val listCategory:List<Category>, val fragment:Fragment, val navOptions: NavOptions) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding:ItemCategoryRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Category,position:Int){
            binding.title.setText(item.title)
            binding.image.setImageResource(item.image)

            binding.layoutCategory.setOnClickListener {
                val action = FragmentCategoryRecipePageDirections.actionFragmentCategoryRecipeDestToFragmentRecipeAllDetailDest(position)
                fragment.findNavController().navigate(action,navOptions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = listCategory[position]
        holder.bind(item,position)
    }

}