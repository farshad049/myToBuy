package com.farshad.mytodo.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.addHeaderModel
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.EpoxyModelEmptyBinding
import com.farshad.mytodo.databinding.EpoxyModelLoadingBinding
import com.farshad.mytodo.databinding.ItemEntityBinding
import com.farshad.mytodo.databinding.ModelHeaderItemBinding
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
    
    var itemEntity = ArrayList<ItemEntity>()
        set(value) {
            field=value
            isLoading=false
            requestModelBuild()
        }
    
    override fun buildModels() {
        if (isLoading){
            LoadingEpoxyModel().id("loading_state").addTo(this)
        }

        if (itemEntity.isEmpty()){
            EmptyEpoxyModel().id("empty_state").addTo(this)
        }
        //in order not to set header for every item, but just for every priority
        var currentPriority:Int=-1
        itemEntity.sortedByDescending { it.priority }.forEach { itemEntity ->
            if (itemEntity.priority != currentPriority){
                currentPriority = itemEntity.priority
                val text=takePriorityText(currentPriority)
                addHeaderModel(text)



            }
            ItemEntityEpoxyModel(itemEntity,itemEntityInterface).id(itemEntity.id).addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(val itemEntity: ItemEntity,val itemEntityInterface:ItemEntityInterface)
        :ViewBindingKotlinModel<ItemEntityBinding>(R.layout.item_entity){
        override fun ItemEntityBinding.bind() {
            tvTitle.text=itemEntity.title
            if (itemEntity.description==null){
                tvDescription.isGone
            }else{
                tvDescription.isVisible
                tvDescription.text=itemEntity.description
            }
            tvPriority.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }
            val colorRes = when (itemEntity.priority) {
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
                itemEntityInterface.onItemClicked(itemEntity)
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

    class LoadingEpoxyModel: ViewBindingKotlinModel<EpoxyModelLoadingBinding>(R.layout.epoxy_model_loading) {
        override fun EpoxyModelLoadingBinding.bind(){
            //nothing to do
        }
    }


}