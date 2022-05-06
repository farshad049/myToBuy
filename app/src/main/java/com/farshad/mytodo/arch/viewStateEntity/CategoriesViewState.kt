package com.farshad.mytodo.arch.viewStateEntity

import com.farshad.mytodo.database.entity.CategoryEntity

data class CategoriesViewState(
    val isLoading:Boolean=false,
    val itemList:List<Item> = emptyList()
){
    data class Item(
        val categoryEntity: CategoryEntity = CategoryEntity(),
        val isSelected:Boolean=false
    )
    fun getSelectedCategoryId():String{
        return itemList.find { it.isSelected }?.categoryEntity?.id?: "None"
    }
}