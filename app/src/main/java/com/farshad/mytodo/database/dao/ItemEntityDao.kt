package com.farshad.mytodo.database.dao

import androidx.room.*
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
     fun getAllItemEntities():Flow<List<ItemEntity>>

    @Transaction
    @Query("SELECT * FROM item_entity")
    fun getAllItemWithCategoryEntity():Flow<List<ItemWithCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE  )
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    @Update
    suspend fun update(itemEntity: ItemEntity)
}