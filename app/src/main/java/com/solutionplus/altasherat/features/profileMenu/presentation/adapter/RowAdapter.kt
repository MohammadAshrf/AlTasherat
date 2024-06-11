package com.solutionplus.altasherat.features.menu.Presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.databinding.ItemMenuBinding
import com.solutionplus.altasherat.features.profileMenu.presentation.adapter.OnRowItemClickListener
import com.solutionplus.altasherat.features.profileMenu.presentation.adapter.RowItem

class RowAdapter(
    private val items: List<RowItem>,
    private val listener: OnRowItemClickListener
) : RecyclerView.Adapter<RowAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.iconImage.setImageResource(item.icon)
        holder.binding.textView.text = item.text
        holder.itemView.setOnClickListener {
            listener.onRowItemClick(item.destinationFragmentId, item.destinationActivity)
        }
    }

    override fun getItemCount() = items.size
}

