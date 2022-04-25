package com.farshad.mytodo.ui.home

import com.farshad.mytodo.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}