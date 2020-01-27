package com.sniperking.libnetwork

abstract class JsonCallback<T> {
    fun onSuccess(apiResponse: ApiResponse<Object>){}
    fun onError(apiResponse: ApiResponse<Object>){}
    fun onCacheSuccess(apiResponse: ApiResponse<Object>){}
}