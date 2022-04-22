package com.farshad.mytodo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farshad.mytodo.dao.ItemEntityDao
import com.farshad.mytodo.model.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun itemEntityDao():ItemEntityDao

}