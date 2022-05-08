package com.farshad.mytodo.arch.viewStateEntity

data class HomeViewState(
    // * means any
    val dataList: List<DataItem<*>> = emptyList(),
    val isLoading:Boolean=false,
    val sort: Sort =Sort.NONE
){
    //T means you are creating a temporary data type which can hold any type of data
    data class DataItem<T>(
        val data:T,
        val isHeader:Boolean=false
    )
    enum class Sort(val displayName:String){
        NONE("None"),
        CATEGORY("Category"),
        OLDEST("Oldest"),
        NEWEST("Newest")
    }
}