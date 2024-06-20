package com.capstone.have.ui.menu.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.have.R
import com.capstone.have.data.response.AllactivityItem
import com.capstone.have.databinding.ItemActivityBinding

class ActivityAdapter : ListAdapter<AllactivityItem, ActivityAdapter.MyViewHolder>(
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

        fun bind(item: AllactivityItem) {
            // Bind data to views using ViewBinding

            binding.iconActivity.setImageResource(R.drawable.ic_activity)
            binding.iconTime.setImageResource(R.drawable.ic_time)
            binding.tvActivity.text = item.name
            binding.tvActivityTime.text = item.duration
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllactivityItem>() {
            override fun areItemsTheSame(oldItem: AllactivityItem, newItem: AllactivityItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: AllactivityItem, newItem: AllactivityItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
