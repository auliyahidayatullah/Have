package com.capstone.have.ui.menu.calorie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.have.data.response.DataItem
import com.capstone.have.databinding.ItemCalorieBinding

class BigCalorieAdapter : ListAdapter<DataItem, BigCalorieAdapter.BigCalorieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigCalorieViewHolder {
        val binding = ItemCalorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BigCalorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BigCalorieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class BigCalorieViewHolder(private val binding: ItemCalorieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem) {
            // Bind data to views using ViewBinding
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.imgRecomFood)
            binding.textFoodName.text = item.foodname
            binding.calorieCount.text = item.calories
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}