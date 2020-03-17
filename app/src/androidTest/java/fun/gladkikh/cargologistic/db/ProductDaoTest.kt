package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.db.entity.ProductDb
import `fun`.gladkikh.cargologistic.db.entity.UnitDb
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    lateinit var appDatabase: AppDatabase


    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun init() {
        //appDatabase = AppDatabase.getInstance(appContext)

        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }


    @Test
    fun test() {
        val listProduct = (0..10).map {
            ProductDb(
                guid = createGuid(),
                name = "Продукт $it"
            )
        }

        listProduct.forEach {
            appDatabase.productDao().insertOrUpdate(it)
        }


        if (appDatabase.productDao().getProductList() != listProduct) {
            assert(false)
        }


        val last = listProduct.last().copy(name = "update")
        appDatabase.productDao().insertOrUpdate(last)

        if (appDatabase.productDao().getProductByGuid(last.guid) != last) {
            assert(false)
        }


        appDatabase.productDao().delete(last)
        if (appDatabase.productDao().getProductByGuid(last.guid) != null) {
            assert(false)
        }

        //Еще раз добавим
        listProduct.forEach {
            appDatabase.productDao().insertOrUpdate(it)
        }
        if (appDatabase.productDao().getProductList() != listProduct) {
            assert(false)
        }


        val listUnit = listProduct.map { product ->
            product to (0..3).map {
                UnitDb(
                    guid = createGuid(),
                    name = "unit $it",
                    code = "it",
                    coeff = it.toFloat(),
                    guidProduct = product.guid,
                    current = it == 3
                )
            }
        }

        listUnit.forEach {
            it.second.forEach { unit ->
                appDatabase.productDao().insertOrUpdate(unit)
            }
        }

        val lastUnit = listUnit.last()

        if (appDatabase.productDao().getUnitListByProduct(listUnit.last().first.guid) !=
            lastUnit.second
        ) {
            assert(false)
        }

        appDatabase.productDao().delete(lastUnit.first)

        if (appDatabase.productDao().getUnitListByProduct(listUnit.last().first.guid).isNotEmpty()){
            assert(false)
        }


        assert(true)

    }

    @After
    fun closeDb() {
        appDatabase.close()
    }


}