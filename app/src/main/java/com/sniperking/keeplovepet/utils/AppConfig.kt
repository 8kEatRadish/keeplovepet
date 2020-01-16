package com.sniperking.keeplovepet.utils

import com.sniperking.keeplovepet.model.Destination
import java.util.concurrent.atomic.AtomicBoolean

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
    var atomicBoolean = AtomicBoolean(false)
    companion object{

    }
}