package com.farshad.mytodo.database.entity

data class CategoriesViewStateModel(
    val isLoading:Boolean=false,
    val itemList:List<Item> = emptyList()
){
    data class Item(
        val categoryEntity: CategoryEntity= CategoryEntity(),
        val isSelected:Boolean=false
    )
    fun getSelectedCategoryId():String{
        return itemList.find { it.isSelected }?.categoryEntity?.id?: "None"
    }
}
