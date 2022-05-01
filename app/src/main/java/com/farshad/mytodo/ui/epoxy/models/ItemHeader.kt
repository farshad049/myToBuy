package com.farshad.mytodo.ui.epoxy.models

import com.farshad.mytodo.R
import com.farshad.mytodo.databinding.ModelHeaderItemBinding
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

data class ItemHeader(val headerText:String)
    : ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item){
    override fun ModelHeaderItemBinding.bind() {
        tvHeader.text=headerText
    }

}