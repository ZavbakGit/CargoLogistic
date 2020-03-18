package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.Constants.DATABASE_NAME
import `fun`.gladkikh.cargologistic.db.dao.PrintLabelDao
import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.db.entity.BarcodeDb
import `fun`.gladkikh.cargologistic.db.entity.PrintLabelDb
import `fun`.gladkikh.cargologistic.db.entity.ProductDb
import `fun`.gladkikh.cargologistic.db.entity.UnitDb
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ProductDb::class, UnitDb::class, BarcodeDb::class, PrintLabelDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun printLabelDao(): PrintLabelDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    }

                )
                //.allowMainThreadQueries() // Если без потоков
                .build()
        }
    }
}