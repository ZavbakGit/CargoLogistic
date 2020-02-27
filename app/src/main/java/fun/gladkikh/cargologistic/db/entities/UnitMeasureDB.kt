package `fun`.gladkikh.cargologistic.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "unitmeasure",foreignKeys = [ForeignKey(
    entity = ProductDB::class,
    parentColumns = arrayOf("guid"),
    childColumns = arrayOf("guidProduct"),
    onDelete = ForeignKey.CASCADE
)])
data class UnitMeasureDB(
    @PrimaryKey @ColumnInfo(name = "guid") val guid:String,
    @ColumnInfo(name = "guidProduct", index = true) val guidProduct: String,
    val name:String,
    @ColumnInfo(name = "coefficient") val coff:Float

){

}