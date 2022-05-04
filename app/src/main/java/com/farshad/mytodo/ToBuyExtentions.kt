package com.farshad.mytodo

import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.ui.epoxy.models.ItemHeader
import com.google.android.material.color.MaterialColors

fun EpoxyController.addHeaderModel(headerText:String){
    ItemHeader(headerText).id(headerText).addTo(this)
}


//this function will help us to take attr colors
@ColorInt
fun View.getAttrColor(attrResId: Int): Int {
    return MaterialColors.getColor(this, attrResId)
}