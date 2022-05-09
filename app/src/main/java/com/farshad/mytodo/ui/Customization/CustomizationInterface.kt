package com.farshad.mytodo.ui.Customization


import com.farshad.mytodo.database.entity.CategoryEntity

interface CustomizationInterface {

    fun onCategoryEmptyStateClicked()
    fun onDeleteCategory(categoryEntity: CategoryEntity)
    fun onCategorySelected(categoryEntity: CategoryEntity)
    fun onPrioritySelected(priorityName: String)
}