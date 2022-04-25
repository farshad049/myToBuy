package com.farshad.mytodo.ui.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.farshad.mytodo.R
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.databinding.ItemEntityBinding
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
            //TODO
        }
        if (itemEntity.isEmpty()){
            //TODO
        }
        
        itemEntity.forEachIndexed { index, itemEntity ->
            itemEntityEpoxyModel(itemEntity,itemEntityInterface)
                .id(index)
                .addTo(this)
        }
        
        
    }

    inner class itemEntityEpoxyModel(val itemEntity: ItemEntity,val itemEntityInterface:ItemEntityInterface)
        :ViewBindingKotlinModel<ItemEntityBinding>(R.layout.item_entity){
        override fun ItemEntityBinding.bind() {
            tvTitle.text=itemEntity.title
            if (itemEntity.description==null){
                tvDescription.isGone
            }else{
                tvDescription.isVisible
                tvDescription.text=itemEntity.description
            }
            ivDelete.setOnClickListener {
                itemEntityInterface.onDeleteItemEntity(itemEntity)
            }
            tvPriority.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
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

            tvPriority.setBackgroundColor(ContextCompat.getColor(root.context, colorRes))




        }

    }
    





}