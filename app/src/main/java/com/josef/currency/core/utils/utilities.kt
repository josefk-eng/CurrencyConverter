package com.josef.currency.core.utils

import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.toMap(): Map<String, Any?> = keys().asSequence().associateWith {
    when (val value = this[it])
    {
        is JSONArray ->
        {
            (0 until value.length()).map { value[it] }
//            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
//            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else            -> value
    }
}