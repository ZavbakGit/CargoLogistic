package `fun`.gladkikh.cargologistic.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDb(
    @PrimaryKey val guid: String,
    val name: String
)

@Entity(
    tableName = "unit",
    foreignKeys = [androidx.room.ForeignKey(
        entity = ProductDb::class,
        parentColumns = kotlin.arrayOf("guid"),
        childColumns = kotlin.arrayOf("guidProduct"),
        onDelete = androidx.room.ForeignKey.CASCADE
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
    foreignKeys = [androidx.room.ForeignKey(
        entity = ProductDb::class,
        parentColumns = kotlin.arrayOf("guid"),
        childColumns = kotlin.arrayOf("guidProduct"),
        onDelete = androidx.room.ForeignKey.CASCADE
    )]
)
data class BarcodeDb(
    @PrimaryKey val barcode: String,
    @ColumnInfo(index = true) var guidProduct: String
)


