package com.farshad.mytodo.arch

import android.content.ClipData
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(private val appDatabase:AppDatabase) {

    suspend fun insertItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().delete(itemEntity)
    }

     fun getAllItems():Flow<List<ItemEntity>>{
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}