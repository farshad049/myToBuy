package com.farshad.mytodo.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.ItemEntity
import kotlinx.coroutines.launch

class ToBuyViewModel:ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntityLiveData=MutableLiveData<List<ItemEntity>>()

    //because our ToBuyRepository needs "appDatabase:AppDatabase" we should use this fun for now
    fun init(appDatabase: AppDatabase){
        viewModelScope.launch {
            repository= ToBuyRepository(appDatabase)
            itemEntityLiveData.postValue(repository.getAllItems())
        }

    }

    fun insertItem(itemEntity: ItemEntity){
        repository.insertItem(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity){
        repository.deleteItem(itemEntity)
    }

}