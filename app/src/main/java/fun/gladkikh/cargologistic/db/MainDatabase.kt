package `fun`.gladkikh.cargologistic.db

import `fun`.gladkikh.cargologistic.db.convert.DateConverter
import `fun`.gladkikh.cargologistic.db.dao.BarcodeDao
import `fun`.gladkikh.cargologistic.db.dao.PrintDao

import `fun`.gladkikh.cargologistic.db.dao.ProductDao
import `fun`.gladkikh.cargologistic.db.dao.UnitMeasureDao
import `fun`.gladkikh.cargologistic.db.entities.BarcodeDB
import `fun`.gladkikh.cargologistic.db.entities.PrintDB

import `fun`.gladkikh.cargologistic.db.entities.ProductDB
import `fun`.gladkikh.cargologistic.db.entities.UnitMeasureDB
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters

@Database(entities = [ProductDB::class,UnitMeasureDB::class,BarcodeDB::class,PrintDB::class],version = 1)
@TypeConverters(DateConverter::class)
abstract class MainDatabase:RoomDatabase(){
    abstract fun barcodeDao():BarcodeDao
    abstract fun productDao():ProductDao
    abstract fun unitMeasureDao(): UnitMeasureDao
    abstract fun printDao(): PrintDao

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