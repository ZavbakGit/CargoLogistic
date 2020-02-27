package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.db.dao.BarcodeDao

import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.db.dao.UnitMeasureDao
import `fun`.gladkikh.cargologistic.db.entities.BarcodeDB

import `fun`.gladkikh.cargologistic.db.entities.ProductDB
import `fun`.gladkikh.cargologistic.db.entities.UnitMeasureDB
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [ProductDB::class,UnitMeasureDB::class,BarcodeDB::class],version = 1)
abstract class MainDatabase:RoomDatabase(){
    abstract fun barcodeDao():BarcodeDao
    abstract fun productDao():ProductDao
    abstract fun unitMeasureDao(): UnitMeasureDao

    companion object{

        @Volatile private var INSTANCE:MainDatabase? = null

        fun getInstance(context: Context):MainDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MainDatabase::class.java, "CargoLogisticMainDB.db")
                .build()
    }

}