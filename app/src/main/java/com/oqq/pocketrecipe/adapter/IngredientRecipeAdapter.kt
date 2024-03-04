package com.oqq.pocketrecipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oqq.pocketrecipe.databinding.ItemIngredientChipBinding

class IngredientRecipeAdapter(var listIngredient:MutableList<String>) : RecyclerView.Adapter<IngredientRecipeAdapter.IngredientViewHolder>() {

    

    inner class IngredientViewHolder(private val binding:ItemIngredientChipBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item:String, position: Int){
            binding.ingredient.text = item

            binding.delete.setOnClickListener {
                listIngredient.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientChipBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listIngredient.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = listIngredient[position]
        holder.bind(item,position)
    }

}