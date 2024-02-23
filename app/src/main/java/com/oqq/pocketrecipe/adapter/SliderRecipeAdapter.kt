package com.oqq.pocketrecipe.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.oqq.pocketrecipe.data.model.recipe.Meal
import com.oqq.pocketrecipe.databinding.ImageRecipeItemBinding
import com.oqq.pocketrecipe.view.activity.ActivityRecipeDetailPage
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlin.random.Random

class SliderRecipeAdapter(private var listMeal:MutableList<Meal>, private val fragment:Fragment, private val navOptions: NavOptions): SliderViewAdapter<SliderRecipeAdapter.SliderAdapterAddImage>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newMeals:MutableList<Meal>){
        listMeal = newMeals
        notifyDataSetChanged()
    }

    inner class SliderAdapterAddImage(private val binding: ImageRecipeItemBinding):SliderViewAdapter.ViewHolder(binding.root){
        fun bind(item:Meal){
            binding.imageRecipe.setImageResource(item.image)
            binding.title.setText("Công thức món ${item.name} ngẫu nhiên")
            binding.btnStartCook.setOnClickListener {
                val random:Int = Random.nextInt(0,item.recipe!!.size)
                item.recipe!![random].attributes.id = item.recipe!![random].id
                val intent: Intent = Intent(itemView.context, ActivityRecipeDetailPage::class.java)
                intent.putExtra("DETAIL_RECIPE",item.recipe!![random].attributes)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getCount(): Int {
        return listMeal.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterAddImage {
        val binding = ImageRecipeItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        return SliderAdapterAddImage(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterAddImage?, position: Int) {
        val item = listMeal[position]
        viewHolder?.bind(item)
    }
}