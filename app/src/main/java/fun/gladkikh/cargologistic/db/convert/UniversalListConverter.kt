package `fun`.gladkikh.cargologistic.db.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UniversalListConverter<T> {

        @TypeConverter
        fun fromListTrainings(myObjects: List<T>): String {
            val gson = Gson()
            return gson.toJson(myObjects)
        }

        @TypeConverter
        fun toListTrainings(data: String?): List<T> {
            val gson = Gson()
            if (data == null) {
                return emptyList<T>()
            }
            val listType = object : TypeToken<List<T>>() {

            }.type
            return gson.fromJson<List<T>>(data, listType)
        }

}