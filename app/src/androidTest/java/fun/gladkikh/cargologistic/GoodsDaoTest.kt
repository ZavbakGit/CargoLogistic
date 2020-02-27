package `fun`.gladkikh.cargologistic

import `fun`.gladkikh.cargologistic.db.MainDatabase
import `fun`.gladkikh.cargologistic.db.entities.BarcodeDB

import `fun`.gladkikh.cargologistic.db.entities.ProductDB
import `fun`.gladkikh.cargologistic.db.entities.UnitMeasureDB
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.awaitAll
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class GoodsDaoTest {

    private lateinit var database: MainDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MainDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        //database = MainDatabase.getInstance(ApplicationProvider.getApplicationContext())
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getGoodsWhenNoGoodsInserted() {
        database.productDao().getProductByGuidFlowable("1")
            .test()
            .assertNoValues()
    }

    @Test fun insertAndGetGoods() {
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()

        val goodsFromDB = database.productDao().getProductByGuid("1")

        Assert.assertEquals(PRODUCT,goodsFromDB)
    }

    @Test fun insertAndGetGoodsFlowable(){
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()

        database.productDao().getProductByGuidFlowable("1")
            .test()
            .assertNoErrors()
            .awaitDone(500,TimeUnit.MILLISECONDS)
            .assertValue{
                it.guid == PRODUCT.guid && it.goodsName == PRODUCT.goodsName
            }
    }

    @Test fun insertAndGetGoodsWithBarcode(){
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()
//        database.barcodeDao().insertProduct(PRODUCT).blockingAwait()
//        val goodsFromDB = database.GoodsDao().getGoodsByGuid("1")

        database.barcodeDao().insertBarcode(BARCODE1).blockingAwait()
        database.barcodeDao().insertBarcode(BARCODE2).blockingAwait()

        database.barcodeDao().getBarcodeByProductGuidFlowableList("1")
            .test()
            .assertNoErrors()
            .awaitDone(500,TimeUnit.MILLISECONDS)
            .assertValue{it.size == 2}
//            .assertValue{it.isNotEmpty()}

    }

    @Test fun insertAndGetProductWithUnitMeasure(){
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()
        database.unitMeasureDao().insertUnitMeasure(UNITMEASURE1).blockingAwait()
        database.unitMeasureDao().insertUnitMeasure(UNITMEASURE2).blockingAwait()

        database.unitMeasureDao().getUnitMeasureByProductGuidFlowableList("1")
            .test()
            .assertNoErrors()
            .awaitDone(500,TimeUnit.MILLISECONDS)
            .assertValue{it.size == 2}

    }

    @Test fun insertAndDeleteGoodsWithBarcode(){
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()
        database.barcodeDao().insertBarcode(BARCODE1).blockingAwait()
        database.barcodeDao().insertBarcode(BARCODE2).blockingAwait()
        database.productDao().deleteAllProduct()

        database.barcodeDao().getBarcodeByProductGuidFlowableList("1")
            .test()
            .assertNoErrors()
            .awaitDone(500,TimeUnit.MILLISECONDS)
            .assertValue{it.isEmpty()}
    }

    @Test fun insertAndDeleteGoodsWithUnitMeasure(){
        database.productDao().insertProductCompletable(PRODUCT).blockingAwait()
        database.unitMeasureDao().insertUnitMeasure(UNITMEASURE1).blockingAwait()
        database.unitMeasureDao().insertUnitMeasure(UNITMEASURE2).blockingAwait()
        database.productDao().deleteProductByGuid("1")

        database.unitMeasureDao().getUnitMeasureByProductGuidFlowableList("1")
            .test()
            .assertNoErrors()
            .awaitDone(500,TimeUnit.MILLISECONDS)
            .assertValue{it.isEmpty()}
    }

    companion object{
        private val PRODUCT = ProductDB(guid = "1",goodsName = "tovar")
        private val BARCODE1 = BarcodeDB(guidProduct = "1",guid = "a1",barcode = "4601546080882")
        private val BARCODE2 = BarcodeDB(guidProduct = "1",guid = "a2",barcode = "46207784")
        private val UNITMEASURE1 = UnitMeasureDB(guidProduct = "1",guid = "b1",name = "шт",coff = 1.0f)
        private val UNITMEASURE2 = UnitMeasureDB(guidProduct = "1",guid = "b2",name = "Упак",coff = 10.0f)


    }
}