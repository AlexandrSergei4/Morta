package com.alki.morta.ui.custom

import android.content.Context
import android.text.util.Linkify
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.alki.morta.R

class PropertyNameValueTextView(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    private val valueTextView = TextView(context)
    private val nameTextView = TextView(context)
    private var autolink: String? = null;
    var name: String? = null
    var value: String? = null
        set(value) {
            if (value == null || value.isEmpty()) {
                this.visibility = GONE
            } else {
                this.visibility = VISIBLE
                field = value
                this.valueTextView.text = value
            }
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PropertyNameValueTextView, 0, 0
        ).apply {
            try {
                this@PropertyNameValueTextView.name =
                    getString(R.styleable.PropertyNameValueTextView_label_text)
                this@PropertyNameValueTextView.autolink =
                    getString(R.styleable.PropertyNameValueTextView_autoLink)
            } finally {
                recycle()
            }
        }
        this.orientation = VERTICAL
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(0, 8, 0, 0)
        valueTextView.layoutParams = layoutParams
        nameTextView.layoutParams = layoutParams
        this.addView(nameTextView);
        this.addView(valueTextView);
        nameTextView.text = name
        val autolinkMask = autolink.let {
            when (it) {
                "phone" -> Linkify.PHONE_NUMBERS
                "email" -> Linkify.EMAIL_ADDRESSES
                "web" -> Linkify.WEB_URLS
                else -> 0
            }
        }
        valueTextView.autoLinkMask =autolinkMask
        this.visibility = GONE;
    }

}