package com.sniperking.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

class JsonConvert<T> : Convert<T> {
    override fun convert(response: String?, type: Type?): T? {
        var jsonObject = JSON.parseObject(response)
        var data = jsonObject.getJSONObject("data")
        if (data != null){
            var data1 : Object = data["data"] as Object
            return JSON.parseObject(data1.toString(),type)
        }
        return null
    }
}