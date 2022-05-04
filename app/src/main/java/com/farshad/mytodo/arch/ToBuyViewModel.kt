package com.farshad.mytodo.arch

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.mytodo.database.AppDatabase
import com.farshad.mytodo.database.entity.CategoriesViewStateModel
import com.farshad.mytodo.database.entity.CategoryEntity
import com.farshad.mytodo.database.entity.ItemEntity
import com.farshad.mytodo.database.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel:ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntityLiveData=MutableLiveData<List<ItemEntity>>()
    val categoryEntityLiveData=MutableLiveData<List<CategoryEntity>>()
    val itemEntityWithCategoryLiveData=MutableLiveData<List<ItemWithCategoryEntity>>()
    //MutableLiveData could be modified in view Layer and because we don't want this, we made an instance by LiveData which is unMutable and can't be modified
    private val _categoriesViewStateLiveData=MutableLiveData<CategoriesViewStateModel>()
    val categoriesViewStateLiveData:LiveData<CategoriesViewStateModel>
    get() = _categoriesViewStateLiveData

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
        viewModelScope.launch {
            repository.getAllItemWithCategoryEntity().collect {itemWithCategory ->
                itemEntityWithCategoryLiveData.postValue(itemWithCategory)
            }
        }

    }

    //it works like when we init our usual live data but here we are initializing CategoriesViewStateModel by this function
    fun onCategorySelected(categoryId:String,isLoading:Boolean=false){
        //we handle this in AddItemEntityFragment,and set it true only when we are in update mode,not in insert mode
        if (isLoading){
            val isLoadingViewState=CategoriesViewStateModel(isLoading = true)
            //we set .value= isLoadingViewState instead of postValue(isLoadingViewState) because we want it to be rum immediately at function runs
            _categoriesViewStateLiveData.value= isLoadingViewState
        }
        val categories = categoryEntityLiveData.value ?:return
        val viewStateItemList=ArrayList<CategoriesViewStateModel.Item>()
        //add none category in order to default category
        viewStateItemList.add(
            CategoriesViewStateModel.Item(
                categoryEntity =CategoryEntity("None","None"),
                isSelected = categoryId=="None"
        ))
        //init the viewStateItemList arrayList
        categories.forEach {
            viewStateItemList.add(CategoriesViewStateModel.Item(
                categoryEntity = it,
                isSelected = it.id==categoryId
            ))
        }
        //here isLoading is setting to false as CategoriesViewStateModel defaults
        val itemListViewState= CategoriesViewStateModel(itemList = viewStateItemList)
        _categoriesViewStateLiveData.postValue(itemListViewState)
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