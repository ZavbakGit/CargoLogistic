package `fun`.gladkikh.cargologistic.common.utils

import `fun`.gladkikh.cargologistic.common.type.Either
import `fun`.gladkikh.cargologistic.common.type.Failure
import java.text.SimpleDateFormat
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

fun String.getDateYMD(): Either<Failure, Date> {
    try {
        val str = this.replace(".","")

        var yaar = str.substring((0..1)).toInt()
        var month = str.substring((2..3)).toInt()
        var day = str.substring((4..5)).toInt()

        if (yaar !in 15..35) {
            return Either.Left(Failure("Не верно указан год!"))
        }
        yaar += 2000

        if (month !in (1..12)) {
            return Either.Left(Failure("Не верно указан месяц!"))
        }

        if (day !in (1..31)) {
            return Either.Left(Failure("Не верно указан день!"))
        }

        val date = GregorianCalendar()
            .apply {
                set(yaar, month-1, day)
            }.time

        return Either.Right(date)
    } catch (e: Exception) {
        return Either.Left(Failure("Неверная дата!"))
    }
}

fun String.getDateDMY(): Either<Failure, Date> {
    try {

        val str = this.replace(".","")

        var yaar = str.substring((4..5)).toInt()
        var month = str.substring((2..3)).toInt()
        var day = str.substring((0..1)).toInt()


        if (yaar !in 15..35) {
            return Either.Left(Failure("Не верно указан год!"))
        }
        yaar += 2000

        if (month !in (1..12)) {
            return Either.Left(Failure("Не верно указан месяц!"))
        }

        if (day !in (1..31)) {
            return Either.Left(Failure("Не верно указан день!"))
        }

        val date = GregorianCalendar()
            .apply {
                set(yaar, month-1, day)
            }.time

        return Either.Right(date)
    } catch (e: Exception) {
        return Either.Left(Failure("Неверная дата!"))
    }
}

fun Date.toStringDMY():String{
    return if (this == null) {
        ""
    } else {
        val format = SimpleDateFormat("dd.MM.yyyy")
        format.format(this).removeRange((6..7))
    }
}