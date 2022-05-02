package com.farshad.mytodo.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel:ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntityLiveData=MutableLiveData<List<ItemEntity>>()
    val categoryEntityLiveData=MutableLiveData<List<CategoryEntity>>()

    val transactionCompleteLiveData=MutableLiveData<Event<Boolean>>()

    //because our ToBuyRepository needs "appDatabase:AppDatabase" we should use this fun for now
    //init our flow connectivity to Db for itemEntities and categoryEntities
    fun init(appDatabase: AppDatabase){
        repository= ToBuyRepository(appDatabase)
        viewModelScope.launch {
            repository.getAllItems().collect {item ->
                itemEntityLiveData.postValue(item)
            }
        }
        //every flow must be run on a different coroutine
        viewModelScope.launch {
            repository.getAllCategories().collect { category->
                categoryEntityLiveData.postValue(category)
            }
        }

    }

    //region ItemEntity
    fun insertItem(itemEntity: ItemEntity){
        viewModelScope.launch {
            repository.insertItem(itemEntity)
            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteItem(itemEntity: ItemEntity){
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity){
        viewModelScope.launch {
            repository.updateItem(itemEntity)
            //in order to be able observe happens and  "if (isInEditMode)" be runs
            transactionCompleteLiveData.postValue(Event(true))
        }
    }
    //endregion ItemEntity

    //region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity){
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)
            transactionCompleteLiveData.postValue(Event(true))
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity){
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity){
        viewModelScope.launch {
            repository.updateCategory(categoryEntity)
            //in order to be able observe happens and  "if (isInEditMode)" be runs
            //transactionCompleteLiveData.postValue(Event(true))
        }
    }
    //endregion CategoryEntity

}