package com.farshad.mytodo.arch

import android.content.ClipData
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.ItemEntity

class ToBuyRepository(private val appDatabase:AppDatabase) {

    fun insertItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    fun getAllItems():List<ItemEntity>{
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}