package `fun`.gladkikh.cargologistic.db.dao

import `fun`.gladkikh.cargologistic.db.entities.PrintDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PrintDao{

    @Query("SELECT * FROM print WHERE guid = :guid")
    fun getPrintByGuidFlowable(guid: String): Flowable<PrintDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrintCompletable(printDB: PrintDB):Completable
}