package com.alki.morta.ui.mortaapps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.databinding.MortaAppItemBinding
import com.alki.morta.domain.MortaApp
import com.alki.morta.ui.appdetail.AppClickListener

class MortaAppsListAdapter(private val clickListener: AppClickListener) :
    ListAdapter<MortaApp, MortaAppsListAdapter.MortaAppViewHolder>(ApplistDiffCallback()) {

    class MortaAppViewHolder(private var binding: MortaAppItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mortaApp: MortaApp, clickListener: AppClickListener) {
            binding.mortaApp = mortaApp
            binding.itemContainer.setOnClickListener(View.OnClickListener {
                clickListener.onClick(mortaApp.packageName)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MortaAppViewHolder {
        val binding = MortaAppItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return MortaAppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MortaAppViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class ApplistDiffCallback:DiffUtil.ItemCallback<MortaApp>(){
    override fun areItemsTheSame(oldItem: MortaApp, newItem: MortaApp): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: MortaApp, newItem: MortaApp): Boolean {
        return oldItem == newItem
    }
}