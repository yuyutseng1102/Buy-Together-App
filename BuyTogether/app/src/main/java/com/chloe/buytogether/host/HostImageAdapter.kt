package com.chloe.buytogether.host

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chloe.buytogether.databinding.ItemHostImageBinding
import com.chloe.buytogether.databinding.ItemHostOptionBinding
import com.chloe.buytogether.host.item.GatherOptionAdapter

class HostImageAdapter(private val viewModel: HostViewModel) : ListAdapter<String, HostImageAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: ItemHostImageBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String,viewModel: HostViewModel) {
            binding.item = item
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHostImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,viewModel)
    }

}