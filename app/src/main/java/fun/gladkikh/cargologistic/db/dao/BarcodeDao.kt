package `fun`.gladkikh.cargologistic.db.dao


import `fun`.gladkikh.cargologistic.db.entities.BarcodeDB
import `fun`.gladkikh.cargologistic.db.entities.ProductDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BarcodeDao {
    @Query("SELECT * FROM barcode WHERE guid = :guidBarcode")
    fun getBarcodeByGuid(guidBarcode: String): Flowable<BarcodeDB>

    @Query("SELECT * FROM barcode WHERE barcode = :barcode")
    fun getBarcodeByBarcode(barcode: String): Flowable<BarcodeDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBarcode(barcode:BarcodeDB): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product:ProductDB): Completable

    @Query("DELETE FROM barcode")
    fun deleteAllBarcods()

    @Query("SELECT * FROM barcode WHERE guid = :guidBarcode")
    fun getBarcodeByGuidFlowableList(guidBarcode: String): Flowable<List<BarcodeDB>>

    @Query("SELECT * FROM barcode WHERE guidProduct = :guidBarcode")
    fun getBarcodeByProductGuidFlowableList(guidBarcode: String): Flowable<List<BarcodeDB>>
}