package `fun`.gladkikh.cargologistic.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDb(
    @PrimaryKey val guid: String,
    val name: String
)

@Entity(
    tableName = "unit",
    foreignKeys = [ForeignKey(
        entity = ProductDb::class,
        parentColumns = arrayOf("guid"),
        childColumns = arrayOf("guidProduct"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class UnitDb(
    @PrimaryKey val code: String,
    @ColumnInfo(index = true) var guidProduct: String,
    val name: String,
    val coeff: Float,
    val current: Boolean
)

@Entity(
    tableName = "barcode",
    foreignKeys = [ForeignKey(
        entity = ProductDb::class,
        parentColumns = arrayOf("guid"),
        childColumns = arrayOf("guidProduct"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class BarcodeDb(
    @PrimaryKey val barcode: String,
    @ColumnInfo(index = true) var guidProduct: String
)


