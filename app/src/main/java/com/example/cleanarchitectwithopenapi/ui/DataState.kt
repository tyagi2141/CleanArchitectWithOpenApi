package com.example.cleanarchitectwithopenapi.ui

/**
 * Created by Rahul on 09/08/20.
 */
class DataState<T>(
    var error: Event<StateError>?=null,
    var loading:Loading= Loading(false),
    val data: Data<T>?=null
){
    companion object{

        fun <T> error(response: Response):DataState<T>{

            return DataState(
                error = Event(
                    StateError(response)
                )
            )
        }
        fun <T> isLoading(
            isLoading:Boolean,
            cacheData: T? =null
        ):DataState<T>{

            return DataState(
                loading = Loading(isLoading),
                data = Data(
                    Event.dataEvent(cacheData),null
                )
            )
        }
        fun <T> data(
            data:T?=null,
            response :Response? = null
        ):DataState<T>{
            return DataState(data = Data(
                Event.dataEvent(data),
                Event.responseEvent(response)
            ))
        }
    }
}