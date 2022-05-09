package com.farshad.mytodo.ui.home.bottomSheet

import android.graphics.Typeface
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.room.PrimaryKey
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.arch.viewStateEntity.HomeViewState
import com.farshad.mytodo.databinding.ModelSortOrderBinding
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class BottomSheetEpoxyController(
    private val selectedSort: HomeViewState.Sort,
    private val sortOptions:Array<HomeViewState.Sort>,
    private val selectedCallBack:(HomeViewState.Sort)->Unit
):EpoxyController() {



    override fun buildModels() {
        sortOptions.forEach {
            val isSelected= it.name == selectedSort.name
            SortOrderItem(isSelected,it,selectedCallBack)
                .id(it.displayName)
                .addTo(this)
        }
    }









    //taking care of which item has been clicked is not handling here, its handling on ToBuyViewModel.currentSort
    data class SortOrderItem(
        val isSelected :Boolean,
        val sortBy:HomeViewState.Sort,
        val onClick:(HomeViewState.Sort)-> Unit
    )
        :ViewBindingKotlinModel<ModelSortOrderBinding>(R.layout.model_sort_order) {
        override fun ModelSortOrderBinding.bind() {
            tvTitle.text=sortBy.displayName
            root.setOnClickListener {
                onClick(sortBy)
            }
            // Styling for selected option
            tvTitle.typeface = if (isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            view.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }


}

