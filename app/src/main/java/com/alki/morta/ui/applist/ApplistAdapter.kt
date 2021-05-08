package com.alki.morta.ui.applist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.databinding.ApplistItemBinding
import com.alki.morta.domain.MortaAppSensitive

class ApplistAdapter :
    ListAdapter<MortaAppSensitive, ApplistAdapter.MortaAppViewHolder>(ApplistDiffCallback()) {

    class MortaAppViewHolder(private var binding: ApplistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mortaApp: MortaAppSensitive) {
            binding.mortaApp = mortaApp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MortaAppViewHolder {
        val binding = ApplistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MortaAppViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MortaAppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ApplistDiffCallback:DiffUtil.ItemCallback<MortaAppSensitive>(){
    override fun areItemsTheSame(oldItem: MortaAppSensitive, newItem: MortaAppSensitive): Boolean {
        return oldItem.activityName == newItem.activityName
    }

    override fun areContentsTheSame(oldItem: MortaAppSensitive, newItem: MortaAppSensitive): Boolean {
        return oldItem == newItem
    }
}