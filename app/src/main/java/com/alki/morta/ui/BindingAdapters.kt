package com.alki.morta.ui.applist

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alki.morta.domain.App
import com.alki.morta.domain.MortaApp
import com.alki.morta.ui.installedapps.InstalledAppsAdapter
import com.alki.morta.ui.mortaapps.MortaAppsListAdapter

@BindingAdapter("mortaAppsRecycleList")
fun bindMortaAppsRecycleList(recyclerView: RecyclerView, data: List<MortaApp>?) {
    val adapter = recyclerView.adapter as MortaAppsListAdapter
    adapter.submitList(data)
}

@BindingAdapter("installedAppRecycleList")
fun bindInstalledAppRecycleList(recyclerView: RecyclerView, data: List<App>?) {
    val adapter = recyclerView.adapter as InstalledAppsAdapter
    adapter.submitList(data)
}

@BindingAdapter("mortaAppIconImage")
fun bindMortaAppIconImage(imageVeiw: ImageView, mortaApp: MortaApp?) {
    val context = imageVeiw.context!!
    val pm = context.packageManager!!
    if (mortaApp != null)
    {
        val activityName = mortaApp.packageName
        val drawable = pm.getApplicationInfo(activityName,0).loadIcon(pm)
        imageVeiw.setImageDrawable(drawable)
    }
}

@BindingAdapter("mortaAppSeverityLevelText")
fun bindMortaAppSeverityLevelText(textView: TextView, mortaApp: MortaApp?) {
    if (mortaApp != null)
    {
        val severityLevel = mortaApp.severityLevel
        textView.text = severityLevel.toString()
            if (severityLevel > 3) textView.setTextColor(Color.parseColor("#FFA812"))
            if (severityLevel > 4) textView.setTextColor(Color.parseColor("#FF7518"))
            if (severityLevel > 5) textView.setTextColor(Color.parseColor("#FF2B2B"))
    }
}

@BindingAdapter("mortaAppThreatTypesText")
fun bindMortaAppThreatTypesText(textView: TextView, mortaApp: MortaApp?) {
    textView.text = mortaApp?.threatTypes
}

@BindingAdapter("packageNameImage")
fun bindPackageNameImage(imageVeiw: ImageView, packageName: String?) {
    val context = imageVeiw.context!!
    val pm = context.packageManager!!

    if (packageName != null)
        imageVeiw.setImageDrawable(pm.getApplicationIcon(packageName))
}