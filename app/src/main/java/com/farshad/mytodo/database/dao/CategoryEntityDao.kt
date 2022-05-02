package com.farshad.mytodo.database.dao

import androidx.room.*
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryEntityDao {

    @Query("SELECT * FROM category_entity")
    fun getAllCategoryEntities(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE  )
    suspend fun insert(categoryEntity: CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Update
    suspend fun update(categoryEntity: CategoryEntity)
}