package com.farshad.mytodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.farshad.mytodo.database.dao.CategoryEntityDao
import com.farshad.mytodo.database.dao.ItemEntityDao
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity

@Database(entities = [ItemEntity::class,CategoryEntity::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
   companion object{
       private var appDatabase: AppDatabase?=null
        fun getDatabase(context: Context): AppDatabase {

            //we need this val to add migration from version 1 to 2
            val MIGRATION_1_2 = object : Migration(1, 2){
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `category_entity` (`id` TEXT NOT NULL,`name` TEXT NOT NULL, PRIMARY KEY(`id`))")
                }
            }


            if (appDatabase != null){
                return appDatabase!!
            }
            appDatabase =Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java,"to-buy-database")
                .addMigrations(MIGRATION_1_2)
                .build()
            return appDatabase!!
        }
   }


    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun categoryEntityDao(): CategoryEntityDao
}