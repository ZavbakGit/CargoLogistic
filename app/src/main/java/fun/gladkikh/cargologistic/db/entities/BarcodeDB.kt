package `fun`.gladkikh.cargologistic.db.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "barcode",
    foreignKeys = [ForeignKey(
    entity = ProductDB::class,
    parentColumns = arrayOf("guid"),
    childColumns = arrayOf("guidProduct"),
    onDelete = ForeignKey.CASCADE
)])

data class BarcodeDB(
    @PrimaryKey @ColumnInfo(name = "guid") val guid:String,
    @ColumnInfo(index = true) var guidProduct:String,
    //@ColumnInfo(name = "guidGoods")
   // val guidGoods:String,
    val barcode:String


)