package com.alki.morta.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.alki.morta.R

class ThreatTypesView(context: Context, attrs: AttributeSet) : HorizontalScrollView(context, attrs){
    var selectedThreatTypes:List<String> = emptyList()
    set(value) {
        field = value
        recreateElements()
    }
    var allThreatTypes:List<String> = emptyList()
        set(value) {
            field = value
            recreateElements()
        }
    private val baseLayout:LinearLayout;
    private val textViewBg = ResourcesCompat.getDrawable(resources,R.drawable.bg_threat_type, context.theme)
    private val selectedTextViewBg = ResourcesCompat.getDrawable(resources,R.drawable.bg_selected_threat_type, context.theme)


    init {
        baseLayout = LinearLayout(context);
        baseLayout.orientation = HORIZONTAL
        baseLayout.dividerPadding
        this.addView(baseLayout)
    }

    private fun recreateElements(){
        baseLayout.removeAllViews()
        addAllAsTextView()

    }

    private fun addAllAsTextView()
    {
        for (selected in selectedThreatTypes) {
            var tv = createTextView(selected);
            tv.background = selectedTextViewBg;
            baseLayout.addView(tv)
        }
        for (threatType in allThreatTypes) {
            if (!selectedThreatTypes.contains(threatType)) {
                var tv = createTextView(threatType);
                tv.background = textViewBg;
                baseLayout.addView(tv)

            }
        }
    }

    private fun createTextView(text:String):TextView
    {
        var textView = TextView(context)
        var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(4,4,4,4)
        textView.setPadding(4,4,4,4)
        textView.layoutParams = layoutParams
        textView.text = text
        return textView
    }


}