package com.farshad.mytodo.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.ItemEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel:ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntityLiveData=MutableLiveData<List<ItemEntity>>()

    val transactionCompleteLiveData=MutableLiveData<Boolean>()

    //because our ToBuyRepository needs "appDatabase:AppDatabase" we should use this fun for now
    fun init(appDatabase: AppDatabase){
        repository= ToBuyRepository(appDatabase)
        viewModelScope.launch {
            repository.getAllItems().collect {item ->
                itemEntityLiveData.postValue(item)
            }
        }
    }

    fun insertItem(itemEntity: ItemEntity){
        viewModelScope.launch {
            repository.insertItem(itemEntity)
            transactionCompleteLiveData.postValue(true)
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
            transactionCompleteLiveData.postValue(true)
        }
    }

}