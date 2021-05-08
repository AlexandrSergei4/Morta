package com.alki.morta.ui.applist

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.domain.MortaApp

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MortaApp>?) {
    val adapter = recyclerView.adapter as ApplistAdapter
    adapter.submitList(data)
}


@BindingAdapter("mortaApp")
fun bindRecyclerView(imageVeiw: ImageView, mortaApp: MortaApp?) {
    val context = imageVeiw.context!!
    val pm = context.packageManager!!
    if (mortaApp != null)
    {
        val activityName = mortaApp.activityName
        val drawable = pm.getApplicationInfo(activityName,0).loadIcon(pm)
        imageVeiw.setImageDrawable(drawable)
    }
}

@BindingAdapter("mortaApp")
fun bindRecyclerView(textView: TextView, mortaApp: MortaApp?) {
    val context = textView.context!!
    val pm = context.packageManager!!
    if (mortaApp != null)
    {
        val severityLevel = mortaApp.threatTypes.sumBy { it.severityLevel }
        textView.text = severityLevel.toString()
        when (severityLevel){
            3 -> textView.setTextColor(Color.parseColor("#FFA812"))
            4 -> textView.setTextColor(Color.parseColor("#FF7518"))
            5 -> textView.setTextColor(Color.parseColor("#FF2B2B"))
        }
    }
}