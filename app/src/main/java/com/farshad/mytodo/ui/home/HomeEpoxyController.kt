package com.farshad.mytodo.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.addHeaderModel
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import com.farshad.mytodo.databinding.EpoxyModelEmptyBinding
import com.farshad.mytodo.databinding.ItemEntityBinding
import com.farshad.mytodo.ui.epoxy.LoadingEpoxyModel
import com.farshad.mytodo.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val itemEntityInterface:ItemEntityInterface
):EpoxyController(){

    var isLoading:Boolean=true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }
    
    var itemList = ArrayList<ItemWithCategoryEntity>()
        set(value) {
            field=value
            isLoading=false
            requestModelBuild()
        }
    
    override fun buildModels() {
        if (isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
        }

        if (itemList.isEmpty()){
            EmptyEpoxyModel().id("empty_state").addTo(this)
        }
        //in order not to set header for every item, but just for every priority
        var currentPriority:Int=-1
        itemList.sortedByDescending { it.itemEntity.priority }.forEach { it ->
            if (it.itemEntity.priority != currentPriority){
                currentPriority = it.itemEntity.priority
                val text=takePriorityText(currentPriority)
                addHeaderModel(text)
            }
            ItemEntityEpoxyModel(it,itemEntityInterface).id(it.itemEntity.id).addTo(this)
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






    private fun takePriorityText(priority:Int):String{
        return when(priority){
            1 -> "low"
            2 -> "medium"
            else -> "High"
        }
    }

    class EmptyEpoxyModel: ViewBindingKotlinModel<EpoxyModelEmptyBinding>(R.layout.epoxy_model_empty){
        override fun EpoxyModelEmptyBinding.bind() {
        }
    }




}