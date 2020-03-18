package `fun`.gladkikh.cargologistic.db.entity

import `fun`.gladkikh.cargologistic.db.Converters
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "print_label")
data class PrintLabelDb(
    @PrimaryKey val guid: String,
    val productObj: String,
    val barcodeRead: String,
    val printerObj: String,
    val countLabel: Int,
    @TypeConverters(Converters::class) val dateCreate: Date,
    @TypeConverters(Converters::class) val datePrint: Date?
)