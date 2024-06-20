package com.capstone.have.ui.menu.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.have.data.response.ExerciseRecommendationsItem
import com.capstone.have.databinding.ItemExerciseBinding

class ExerciseAdapter : ListAdapter<ExerciseRecommendationsItem, ExerciseAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExerciseRecommendationsItem) {
            // Bind data to views using ViewBinding
            Glide.with(binding.root)
                .load(item.imageUrl)
                .into(binding.imgExercise)
            binding.exerciseName.text = item.name
            binding.exerciseCalorie.text = item.calories

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExerciseRecommendationsItem>() {
            override fun areItemsTheSame(oldItem: ExerciseRecommendationsItem, newItem: ExerciseRecommendationsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ExerciseRecommendationsItem, newItem: ExerciseRecommendationsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
