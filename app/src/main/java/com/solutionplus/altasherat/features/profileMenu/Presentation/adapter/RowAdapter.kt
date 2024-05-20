package com.solutionplus.altasherat.features.menu.Presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.databinding.ItemMenuBinding

class RowAdapter(
    private val items: List<RowItem>,
    private val navController: NavController
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
            navController.navigate(item.destinationFragmentId)
        }
    }

    override fun getItemCount() = items.size
}

data class RowItem(val icon: Int, val text: String, val destinationFragmentId: Int)