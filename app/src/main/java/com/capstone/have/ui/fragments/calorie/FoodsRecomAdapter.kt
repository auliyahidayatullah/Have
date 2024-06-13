package com.capstone.have.ui.fragments.calorie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.have.data.response.FoodRecommendationsItem
import com.capstone.have.databinding.ItemCalorieBinding

class FoodRecomAdapter : ListAdapter<FoodRecommendationsItem, FoodRecomAdapter.FoodRecomViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecomViewHolder {
        val binding = ItemCalorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodRecomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodRecomViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class FoodRecomViewHolder(private val binding: ItemCalorieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodRecommendationsItem) {
            Glide.with(binding.root)
                .load(item.imageUrl)
                .into(binding.imgRecomFood)
            binding.textFoodName.text = item.name
            binding.calorieCount.text = item.calories
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodRecommendationsItem>() {
            override fun areItemsTheSame(oldItem: FoodRecommendationsItem, newItem: FoodRecommendationsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodRecommendationsItem, newItem: FoodRecommendationsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
