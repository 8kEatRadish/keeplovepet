package com.sniperking.libnetwork

import okhttp3.FormBody

class PostRequest<T>(mUrl: String) : Request<T, PostRequest<T>>(mUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val bodyBuilder = FormBody.Builder()
        for (entry in params.entries) {
            bodyBuilder.add(entry.key,entry.value.toString())
        }
        return builder.url(mUrl).post(bodyBuilder.build()).build()
    }
}