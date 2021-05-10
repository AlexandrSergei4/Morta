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

@BindingAdapter("mortaAppSeverityLevel")
fun bindRecyclerView(textView: TextView, mortaApp: MortaApp?) {
    val context = textView.context!!
    if (mortaApp != null)
    {
        val severityLevel = mortaApp.severityLevel
        textView.text = severityLevel.toString()
            if (severityLevel > 3) textView.setTextColor(Color.parseColor("#FFA812"))
            if (severityLevel > 4) textView.setTextColor(Color.parseColor("#FF7518"))
            if (severityLevel > 5) textView.setTextColor(Color.parseColor("#FF2B2B"))
    }
}

@BindingAdapter("mortaAppThreatTypes")
fun bindThreatTypeRecyclerView(textView: TextView, mortaApp: MortaApp?) {
    textView.text = mortaApp?.threatTypes
}