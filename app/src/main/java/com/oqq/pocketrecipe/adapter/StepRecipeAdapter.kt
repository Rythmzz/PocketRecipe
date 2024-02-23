package com.oqq.pocketrecipe.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oqq.pocketrecipe.data.model.recipe.Process
import com.oqq.pocketrecipe.databinding.ItemStepBinding

class StepRecipeAdapter(var listProcess:MutableList<Process>) : RecyclerView.Adapter<StepRecipeAdapter.StepViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newListProcess:MutableList<Process>){
        listProcess = newListProcess
        notifyDataSetChanged()
    }

    inner class StepViewHolder(private val binding:ItemStepBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:Process, position: Int){
            binding.step.setText("Bước ${position+1}")
            binding.stepName.setText(item.title)
            binding.stepDescription.setText(item.description)
            if (position == listProcess.size-1){
                binding.delete.visibility = View.VISIBLE
            }

            binding.delete.setOnClickListener {
                listProcess.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemStepBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StepViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProcess.size
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val item = listProcess[position]
        holder.bind(item,position)
    }

}