package com.farshad.mytodo.arch

import android.content.ClipData
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(private val appDatabase:AppDatabase) {

    //region itemEntity
    suspend fun insertItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun updateItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().update(itemEntity)
    }

     fun getAllItemWithCategoryEntity():Flow<List<ItemWithCategoryEntity>>{
        return appDatabase.itemEntityDao().getAllItemWithCategoryEntity()
    }

    fun getAllItems():Flow<List<ItemEntity>>{
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
    //endregion itemEntity

    //region categoryEntity
    suspend fun insertCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().insert(categoryEntity)
    }

    suspend fun deleteCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().delete(categoryEntity)
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity){
        appDatabase.categoryEntityDao().update(categoryEntity)
    }

    fun getAllCategories():Flow<List<CategoryEntity>>{
        return appDatabase.categoryEntityDao().getAllCategoryEntities()
    }
    //endregion categoryEntity
}