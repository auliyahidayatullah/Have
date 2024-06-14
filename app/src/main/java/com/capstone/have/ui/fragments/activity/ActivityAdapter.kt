package com.capstone.have.ui.fragments.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.have.R
import com.capstone.have.data.response.ActivityData
import com.capstone.have.databinding.ItemActivityBinding

class ActivityAdapter : ListAdapter<ActivityData, ActivityAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ActivityData) {
            // Bind data to views using ViewBinding
            binding.iconActivity.setImageResource(R.drawable.ic_activity)
            binding.iconActivity.setImageResource(R.drawable.ic_time)
            binding.tvActivity.text = item.name
            binding.tvActivityTime.text = item.duration

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ActivityData>() {
            override fun areItemsTheSame(oldItem: ActivityData, newItem: ActivityData): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ActivityData, newItem: ActivityData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
