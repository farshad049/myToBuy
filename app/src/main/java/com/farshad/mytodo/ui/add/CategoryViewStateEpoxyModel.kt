package com.farshad.mytodo.ui.add

import android.content.res.ColorStateList
import android.graphics.Typeface
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.arch.viewStateEntity.CategoriesViewState
import com.farshad.mytodo.databinding.ModelCategoryItemSelectionBinding
import com.farshad.mytodo.getAttrColor
import com.farshad.mytodo.ui.epoxy.LoadingEpoxyModel
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class CategoryViewStateEpoxyModel(
    private val onCategorySelectedClick: (String) -> Unit
):EpoxyController() {

    var viewState= CategoriesViewState()
    set(value) {
        field=value
        requestModelBuild()
    }

    override fun buildModels() {
        //we handle isLoading mode by this mode which is different with old fashion mode because we just want to handle loading on this specific field on page,not on entire page
        if (viewState.isLoading){
            LoadingEpoxyModel()
                .id("loading_state")
                .addTo(this)
            return
        }
        viewState.itemList.forEach {
            CategoryViewStateItem(it,onCategorySelectedClick)
                .id(it.categoryEntity.id)
                .addTo(this)
        }
    }

    data class CategoryViewStateItem(
        val item:CategoriesViewState.Item,
        private val onClick: (String) -> Unit
        )
        :ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection){
        override fun ModelCategoryItemSelectionBinding.bind() {
            tv.text=item.categoryEntity.name
            root.setOnClickListener { onClick(item.categoryEntity.id) }

            val colorRes=if (item.isSelected) androidx.navigation.ui.R.attr.colorSecondary else androidx.appcompat.R.attr.colorPrimary
            tv.setTextColor(root.getAttrColor(colorRes))
            root.setStrokeColor(ColorStateList.valueOf(colorRes))
            //set selected item font to be bold
            tv.typeface = if (item.isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }

    }















}