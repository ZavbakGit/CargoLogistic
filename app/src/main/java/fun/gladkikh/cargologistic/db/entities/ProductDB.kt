package `fun`.gladkikh.cargologistic.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDB(
            @PrimaryKey @ColumnInfo(name = "guid",index = true)
            val guid: String,
            val goodsName:String
)