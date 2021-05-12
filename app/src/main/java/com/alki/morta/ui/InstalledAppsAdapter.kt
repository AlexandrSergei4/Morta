package com.alki.morta.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.databinding.InstalledAppItemBinding
import com.alki.morta.domain.App
import com.alki.morta.domain.MortaApp

class InstalledAppsAdapter :
    ListAdapter<App, InstalledAppsAdapter.AppViewHolder>(ApplistDiffCallback()) {

    class AppViewHolder(private var binding: InstalledAppItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(app: App) {
            binding.app = app
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = InstalledAppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ApplistDiffCallback:DiffUtil.ItemCallback<App>(){
    override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
        return oldItem.activityName == newItem.activityName
    }

    override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
        return oldItem == newItem
    }
}