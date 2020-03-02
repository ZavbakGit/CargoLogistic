package `fun`.gladkikh.cargologistic.db.entities

import `fun`.gladkikh.cargologistic.db.convert.DateConverter
import androidx.room.*
import java.util.*

@Entity(tableName = "print")
data class PrintDB (
    @PrimaryKey
    val guid:String = UUID.randomUUID().toString(),
    val guidProduct:String,
    @ColumnInfo(name = "count") val countLabel:Int,
    @TypeConverters(DateConverter::class) val dateCreate:Date,
    @TypeConverters(DateConverter::class) val datePrint:Date

)