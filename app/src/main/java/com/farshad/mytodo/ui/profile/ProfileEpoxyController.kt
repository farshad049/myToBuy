package com.farshad.mytodo.ui.profile

import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.addHeaderModel
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.databinding.ModelCategoryBinding
import com.farshad.mytodo.databinding.ModelEmptyButtonBinding
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class ProfileEpoxyController(
    val onCategoryEmptyStateClick:() -> Unit
):EpoxyController() {

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var categories=ArrayList<CategoryEntity>()
    set(value) {
        field=value
        isLoading=false
        requestModelBuild()
    }



    override fun buildModels() {
        addHeaderModel("Categories")

        categories.forEach {
            Category(it)
                .id(it.id)
                .addTo(this)
        }

        EmptyButton("Add Category",onCategoryEmptyStateClick)
            .id("add_category")
            .addTo(this)

    }

    data class Category(val categoryEntity:CategoryEntity)
        : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category){
        override fun ModelCategoryBinding.bind() {
            tv.text=categoryEntity.name
        }
    }

    data class EmptyButton(val buttonText:String,val onClick:()->Unit)
        :ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button){
        override fun ModelEmptyButtonBinding.bind() {
            button.text=buttonText
            button.setOnClickListener { onClick.invoke() }
        }
        //let EmptyButton take entire screen
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

}