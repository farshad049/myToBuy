package com.farshad.mytodo.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.addHeaderModel
import com.farshad.mytodo.arch.viewStateEntity.HomeViewState
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import com.farshad.mytodo.databinding.EpoxyModelEmptyBinding
import com.farshad.mytodo.databinding.ItemEntityBinding
import com.farshad.mytodo.ui.epoxy.LoadingEpoxyModel
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val itemEntityInterface:ItemEntityInterface
):EpoxyController(){

    var viewState:HomeViewState= HomeViewState(isLoading = true)
    set(value) {
        field=value
        requestModelBuild()
    }
    
    override fun buildModels() {
        if (viewState.isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
        }

        if (viewState.dataList.isEmpty()){
            EmptyEpoxyModel().id("empty_state").addTo(this)
        }


        viewState.dataList.forEach {
            if (it.isHeader){
                addHeaderModel(it.data.toString())
                //just return the forEach not entire function
                return@forEach
            }
            val itemWithCategoryEntity=it.data as ItemWithCategoryEntity
            ItemEntityEpoxyModel(itemWithCategoryEntity,itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
        }








    }

    data class ItemEntityEpoxyModel(val item: ItemWithCategoryEntity,val itemEntityInterface:ItemEntityInterface)
        :ViewBindingKotlinModel<ItemEntityBinding>(R.layout.item_entity){
        override fun ItemEntityBinding.bind() {
            tvTitle.text=item.itemEntity.title
            if (item.itemEntity.description==null){
                tvDescription.isGone
            }else{
                tvDescription.isVisible
                tvDescription.text=item.itemEntity.description
            }
            tvCategory.text=item.categoryEntity?.name
            tvPriority.setOnClickListener {
                itemEntityInterface.onBumpPriority(item.itemEntity)
            }
            val colorRes = when (item.itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }
            val color=ContextCompat.getColor(root.context, colorRes)
            tvPriority.setBackgroundColor(color)
            //set stroke color for items
            root.setStrokeColor(ColorStateList.valueOf(color))

            root.setOnClickListener {
                itemEntityInterface.onItemClicked(item.itemEntity)
            }
        }
    }




    class EmptyEpoxyModel: ViewBindingKotlinModel<EpoxyModelEmptyBinding>(R.layout.epoxy_model_empty){
        override fun EpoxyModelEmptyBinding.bind() {
        }
    }




}