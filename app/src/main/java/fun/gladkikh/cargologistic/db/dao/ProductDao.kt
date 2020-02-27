package `fun`.gladkikh.cargologistic.db.dao

import `fun`.gladkikh.cargologistic.db.entities.ProductDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProductDao {
    @Query("SELECT * FROM product WHERE guid = :guid")
    fun getProductByGuidFlowable(guid: String): Flowable<ProductDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductCompletable(ProductDB: ProductDB): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(ProductDB: ProductDB)

    @Query("DELETE FROM product")
    fun deleteAllProduct()

    @Query("DELETE FROM product WHERE guid = :guid")
    fun deleteProductByGuid(guid:String)


    @Query("SELECT * FROM product")
    fun getAllProduct(): Array<ProductDB>

    @Query("SELECT * FROM product WHERE guid = :guid")
    fun getProductByGuid(guid: String): ProductDB

    //@Query("SELECT * FROM ProductDB WHERE ")
}