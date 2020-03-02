package `fun`.gladkikh.cargologistic

import `fun`.gladkikh.cargologistic.db.MainDatabase
import `fun`.gladkikh.cargologistic.db.entities.PrintDB
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class PrintDaoTest {

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
        database.printDao().getPrintByGuidFlowable("1")
            .test()
            .assertNoValues()
    }

    @Test fun insertPrint(){
        database.printDao().insertPrintCompletable(PRINT).blockingAwait()

        database.printDao().getPrintByGuidFlowable("dasdasd")
            .test()
            .awaitDone(400,TimeUnit.MILLISECONDS)
            .assertValue{it == PRINT}
    }

    companion object{
        private val PRINT = PrintDB(guid = "dasdasd", guidProduct = "1",countLabel = 5,dateCreate = Date(1583134342000L - (5..10).random() * 86400000L ),datePrint = Date(1583134188000L - (1..4).random() * 86400000L))
    }
}

