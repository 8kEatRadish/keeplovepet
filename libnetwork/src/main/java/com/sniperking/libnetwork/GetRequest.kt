package com.sniperking.libnetwork

class GetRequest<T>(mUrl: String) : Request<T, GetRequest<T>>(mUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(UrlCreator.createUrlFormParams(mUrl, params)).build()
    }
}