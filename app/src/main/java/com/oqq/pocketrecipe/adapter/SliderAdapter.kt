package com.oqq.pocketrecipe.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.oqq.pocketrecipe.data.model.other.ArticleSlide
import com.oqq.pocketrecipe.databinding.ImageItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private var listImage:MutableList<ArticleSlide>): SliderViewAdapter<SliderAdapter.SliderAdapterAddImage>() {
    inner class SliderAdapterAddImage(private val binding: ImageItemBinding):ViewHolder(binding.root){
        fun bind(item: ArticleSlide){
            Glide.with(itemView.context).load(item.url).into(binding.imageView)
            binding.title.text = item.title
            binding.description.text = item.description

            binding.imageView.setOnClickListener {
                val webpage = Uri.parse(item.source)
                val intent = Intent(Intent.ACTION_VIEW,webpage)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getCount(): Int {
        return listImage.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterAddImage {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        return SliderAdapterAddImage(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterAddImage?, position: Int) {
        val item = listImage[position]
        viewHolder?.bind(item)
    }
}