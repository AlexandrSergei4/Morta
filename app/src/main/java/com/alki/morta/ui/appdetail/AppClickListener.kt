package com.alki.morta.ui.appdetail

class AppClickListener(val clickListener : (packageName:String) -> Unit ){
    fun onClick(packageName:String){
        clickListener(packageName)
    }
}