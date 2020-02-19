package `fun`.gladkikh.cargologistic.common.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toSimpleDate():String{
    return if (this == null) {
        ""
    } else {
        val format = SimpleDateFormat("dd.MM.yyyy")
        format.format(this)
    }
}

fun Date.toSimpleDateTime():String{
    return if (this == null) {
        ""
    } else {
        val format = SimpleDateFormat("dd.MM.yyyy hh:mm:ss")
        format.format(this)
    }
}

fun Date.toFormatISO():String{
    return if (this == null) {
        ""
    } else {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
        format.format(this)
    }
}