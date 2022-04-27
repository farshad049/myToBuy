package com.farshad.mytodo.ui.home

import com.farshad.mytodo.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onItemClicked(itemEntity: ItemEntity)
}