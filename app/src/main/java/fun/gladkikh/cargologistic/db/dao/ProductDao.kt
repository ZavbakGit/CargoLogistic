package `fun`.gladkikh.cargologistic.db.dao

import `fun`.gladkikh.cargologistic.db.entity.BarcodeDb
import `fun`.gladkikh.cargologistic.db.entity.ProductDb
import `fun`.gladkikh.cargologistic.db.entity.UnitDb
import androidx.room.*

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreAction(entity: ProductDb): Long

    @Transaction
    fun insertOrUpdate(entity: ProductDb){
        if (insertIgnoreAction(entity) == -1L) {
            update(entity)
        }
    }

    @Update
    fun update(entity: ProductDb)

    @Delete
    fun delete(entity: ProductDb)

    @Query("SELECT * FROM product WHERE guid = :guid")
    fun getProductByGuid(guid:String):ProductDb?

    @Query("SELECT * FROM product")
    fun getProductList():List<ProductDb>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreAction(entity: UnitDb): Long

    @Transaction
    fun insertOrUpdate(entity: UnitDb){
        if (insertIgnoreAction(entity) == -1L) {
            update(entity)
        }
    }

    @Update
    fun update(entity: UnitDb)

    @Delete
    fun delete(entity: UnitDb)

    @Query("SELECT * FROM unit")
    fun getUnitList():List<UnitDb>

    @Query("SELECT * FROM unit WHERE guidProduct = :guidProduct")
    fun getUnitListByProduct(guidProduct:String):List<UnitDb>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreAction(entity: BarcodeDb): Long

    @Transaction
    fun insertOrUpdate(entity: BarcodeDb){
        if (insertIgnoreAction(entity) == -1L) {
            update(entity)
        }
    }

    @Update
    fun update(entity: BarcodeDb)

    @Delete
    fun delete(entity: BarcodeDb)

    @Query("SELECT * FROM barcode WHERE barcode = :barcode")
    fun getBarcode(barcode:String):BarcodeDb?

    @Query("SELECT * FROM barcode")
    fun getBarcodeList():List<BarcodeDb>

    @Query("SELECT * FROM barcode WHERE guidProduct = :guidBarcode")
    fun getBarcodeListByProduct(guidBarcode:String):List<BarcodeDb>
}