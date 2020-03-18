package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.domain.entity.*
import `fun`.gladkikh.cargologistic.mapper.transform
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class PrintLabelDaoTest {

    lateinit var appDatabase: AppDatabase


    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun init() {
        //appDatabase = AppDatabase.getInstance(appContext)

        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }


    @Test
    fun test() {

        val listPrintLabel = (0..100).map {
            PrintLabelEntity(
                guid = createGuid() + "-$it",
                product = ProductEntity(
                    guid = createGuid() + "-$it",
                    name = "Товар $it",
                    listUnit = (0..3).map {
                        UnitEntity(
                            code = createGuid() + "-$it",
                            name = "Ед-$it",
                            current = it == 3,
                            coeff = it.toFloat()

                        )
                    },
                    listBarcode = (0..3).map {
                        BarcodeEntity(createGuid() + "-$it")
                    }
                ),
                printer = PrinterEntity(
                    name = "Принтер $it",
                    current = false,
                    guid = createGuid()
                ),
                barcodeRead = "jkghjkgjk",
                countLabel = 3,
                dateCreate = Date(),
                datePrint = Date()
            )
        }


        listPrintLabel.forEach {
            appDatabase.printLabelDao().insertOrUpdate(it.transform())
        }

        val list =  appDatabase.printLabelDao().getPrintLabelList().map {
            it.transform()
        }


        assert(list.isNotEmpty())



    }

    @After
    fun closeDb() {
        appDatabase.close()
    }


}