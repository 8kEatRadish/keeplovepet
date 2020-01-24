package com.sniperking.libnetwork

import java.net.URLEncoder

class UrlCreator {
    companion object{
        fun createUrlFormParams(url: String, params: HashMap<String, Object>) : String {
            val builder = StringBuilder()
            builder.append(url)
            if (url.indexOf("?") > 0 || url.indexOf("&") > 0){
                builder.append("&")
            }else{
                builder.append("?")
            }

            for (entry in params.entries) {
                val value = URLEncoder.encode(entry.value.toString(),"UTF-8")
                builder.append(entry.key).append("=").append(value).append("&")
            }
            builder.deleteCharAt(builder.length - 1)
            return builder.toString()
        }
    }
}