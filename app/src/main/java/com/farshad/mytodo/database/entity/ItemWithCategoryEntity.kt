package com.farshad.mytodo.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithCategoryEntity(
    @Embedded val itemEntity: ItemEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    //it is nullable because ItemEntity.categoryId is set to null by default
    val categoryEntity: CategoryEntity?
)
