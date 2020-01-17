package com.sniperking.keeplovepet.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sniperking.keeplovepet.model.BottomBar
import com.sniperking.keeplovepet.model.Destination
import org.json.JSONObject

fun dp2px(size : Int) : Int{
    val fl = AppGlobals.getApplication().resources.displayMetrics.density * size + 0.5f
    return fl.toInt()
}

fun parseFile(fileName : String):String{
    var builder = StringBuilder()
    val assetManager = AppGlobals.getApplication().resources.assets
    val stream = assetManager.open(fileName)
    stream.buffered().reader().use {
        builder.append(it.readText())
    }
    return builder.toString()
}

class AppConfig {
    companion object{
        val destConfig : HashMap<String, Destination> by lazy {
            var content = parseFile("destination.json")
            val type = object : TypeToken<HashMap<String,Destination>>(){}.type
            Gson().fromJson<HashMap<String,Destination>>(content,type)
        }

        val bottomBar by lazy {
            var content = parseFile("main_tabs_config.json")
            val type = object : TypeToken<BottomBar>(){}.type
            Gson().fromJson<BottomBar>(content,type)
        }
    }
}
