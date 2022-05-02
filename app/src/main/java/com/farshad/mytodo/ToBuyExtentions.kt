package com.farshad.mytodo

import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.ui.epoxy.models.ItemHeader

fun EpoxyController.addHeaderModel(headerText:String){
    ItemHeader(headerText).id(headerText).addTo(this)

}