package com.alki.morta.ui.installedapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.databinding.InstalledAppItemBinding
import com.alki.morta.domain.App
import com.alki.morta.ui.appdetail.AppClickListener

class InstalledAppsAdapter(private val clickListener: AppClickListener) :
    ListAdapter<App, InstalledAppsAdapter.AppViewHolder>(ApplistDiffCallback()) {

    class AppViewHolder(private var binding: InstalledAppItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(app: App, clickListener: AppClickListener) {
            binding.app = app
            binding.itemContainer.setOnClickListener(View.OnClickListener {
                clickListener.onClick(app.packageName)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = InstalledAppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class ApplistDiffCallback:DiffUtil.ItemCallback<App>(){
    override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: App, newItem: App): Boolean {
        return oldItem == newItem
    }
}