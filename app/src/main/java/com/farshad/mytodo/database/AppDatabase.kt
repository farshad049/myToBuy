package com.farshad.mytodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farshad.mytodo.database.dao.ItemEntityDao
import com.farshad.mytodo.database.entity.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
   companion object{
       private var appDatabase: AppDatabase?=null
        fun getDatabase(context: Context): AppDatabase {
            if (AppDatabase != null){
                return appDatabase!!
            }
            appDatabase =Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"to-buy-database").build()
            return appDatabase!!
        }
   }
    abstract fun itemEntityDao(): ItemEntityDao
}