package `fun`.gladkikh.cargologistic.db.convert

import androidx.room.TypeConverter
import java.util.*


class DateConverter {

//    @TypeConverter
//    fun toDate(dateLong: Long?): Date? {
//        return if (dateLong == null) null else Date(dateLong)
//    }
//
//    @TypeConverter
//    fun fromDate(date: Date?): Long? {
//        return (if (date == null) null else date.time)?.toLong()
//    }
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
    return if (value == null) null else Date(value)
}

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}