package com.josef.currency.core.utils

import android.icu.text.SimpleDateFormat
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.math.absoluteValue

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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}




