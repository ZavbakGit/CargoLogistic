package `fun`.gladkikh.cargologistic.db.dao

import `fun`.gladkikh.cargologistic.db.entity.PrintLabelDb
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PrintLabelDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnoreAction(entity: PrintLabelDb): Long

    @Transaction
    fun insertOrUpdate(entity: PrintLabelDb){
        if (insertIgnoreAction(entity) == -1L) {
            update(entity)
        }
    }

    @Update
    fun update(entity: PrintLabelDb)

    @Query("SELECT * FROM print_label")
    fun getPrintLabelList():LiveData<List<PrintLabelDb>>

    @Query("DELETE FROM print_label")
    fun deleteAllPrintLabel()
}

