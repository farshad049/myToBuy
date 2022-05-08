package com.farshad.mytodo.ui.home.bottomSheet

import androidx.room.PrimaryKey
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.arch.viewStateEntity.HomeViewState
import com.farshad.mytodo.databinding.ModelSortOrderBinding
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class BottomSheetEpoxyController(
    private val sortOptions:Array<HomeViewState.Sort>,
    private val selectedSort:(HomeViewState.Sort)->Unit
):EpoxyController() {



    override fun buildModels() {
        sortOptions.forEach {
            SortOrderItem(it,selectedSort)
                .id(it.displayName)
                .addTo(this)
        }
    }

    data class SortOrderItem(val sortBy:HomeViewState.Sort,val onClick:(HomeViewState.Sort)->Unit)
        :ViewBindingKotlinModel<ModelSortOrderBinding>(R.layout.model_sort_order) {
        override fun ModelSortOrderBinding.bind() {
            tvTitleSort.text=sortBy.displayName
            root.setOnClickListener {
                onClick(sortBy)
            }
        }
    }


}

