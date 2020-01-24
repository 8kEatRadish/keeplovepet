package com.sniperking.libnetwork

abstract class JsonCallback<T> {
    fun onSuccess(apiResponse: ApiResponse<T>){}
    fun onError(apiResponse: ApiResponse<T>){}
    fun onCacheSuccess(apiResponse: ApiResponse<T>){}
}