package com.oqq.pocketrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.data.model.other.FeatureSlide
import com.oqq.pocketrecipe.databinding.ImageFeatureItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderFeatureAdapter(private val navOptions: NavOptions, private val fragment:Fragment, private var listFeature:MutableList<FeatureSlide>): SliderViewAdapter<SliderFeatureAdapter.SliderAdapterAddImage>() {
    inner class SliderAdapterAddImage(private val binding: ImageFeatureItemBinding):ViewHolder(binding.root){
        fun bind(item: FeatureSlide, position:Int){
            binding.title.text = item.title
            binding.thumnailLayout.setBackgroundResource(item.image)
            binding.btnInteract.setOnClickListener {
               if (position == 0){
                   fragment.findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_add_recipe_detail_dest,null,navOptions)
               }
                else if (position == 1){
                    fragment.findNavController().navigate(R.id.action_fragment_home_dest_to_fragment_category_recipe_dest,null,navOptions)
               }
            }
        }
    }

    override fun getCount(): Int {
        return listFeature.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterAddImage {
        val binding = ImageFeatureItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        return SliderAdapterAddImage(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterAddImage?, position: Int) {
        val item = listFeature[position]
        viewHolder?.bind(item,position)
    }
}