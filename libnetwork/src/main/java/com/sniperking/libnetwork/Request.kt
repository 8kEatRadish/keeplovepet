package com.sniperking.libnetwork

import android.util.Log
import androidx.annotation.IntDef
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.lang.StringBuilder
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.ParameterizedType

abstract class Request<T, R : Request<T,R>>(protected val mUrl: String) {
    private val headers: HashMap<String, String> = HashMap()
    protected val params: HashMap<String, Object> = HashMap()

    private lateinit var cacheKey: String


    companion object {
        //仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
        const val CACHE_ONLY = 1
        //先访问缓存，同时发起网络的请求，成功后缓存到本地
        const val CACHE_FIRST = 2
        //仅仅只访问服务器，不存任何存储
        const val NET_ONLY = 3
        //先访问网络，成功后缓存到本地
        const val NET_CACHE = 4
    }

    @IntDef(
        CACHE_ONLY,
        CACHE_FIRST,
        NET_CACHE,
        NET_ONLY
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class CacheStrategy

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Object): R {
        //int byte char short long double float boolean 和他们的包装类型，但是除了 String.class 所以要额外判断
        try {
            if (value.javaClass == String::class.java) {
                params[key] = value
            } else {
                val field = value.javaClass.getField("TYPE")
                val claz = field[null] as Class<*>
                if (claz.isPrimitive) {
                    params[key] = value
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return this as R
    }

    fun cacheKey(key: String): R {
        cacheKey = key
        return this as R
    }

    fun execute(jsonCallback: JsonCallback<T>?) {
        if (jsonCallback == null) {

        } else {
            getCall().enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    val response = ApiResponse<T>(false,null,e.message,null)
                    jsonCallback.onError(response)
                }

                override fun onResponse(call: Call, response: Response) {
                    val response : ApiResponse<T> = parseResponse(response, jsonCallback)
                    if (response.success){
                        jsonCallback.onSuccess(response)
                    }else{
                        jsonCallback.onError(response)
                    }
                }
            })
        }
    }

    private fun parseResponse(
        response: Response,
        callback: JsonCallback<T>
    ): ApiResponse<T> {
        var message : String? = null
        var status = response.code
        var isSuccess = response.isSuccessful
        if (isSuccess){
            val body = response.body.toString()
        }else{

        }
    }

    private fun getCall(): Call {
        val builder: okhttp3.Request.Builder = okhttp3.Request.Builder()
        addHeaders(builder)
        val request: okhttp3.Request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request


    private fun addHeaders(builder: okhttp3.Request.Builder) {
        for (entry in headers.entries) {
            builder.addHeader(entry.key,entry.value)
        }
    }
}