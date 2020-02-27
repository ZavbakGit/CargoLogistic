package `fun`.gladkikh.cargologistic.db.dao

import `fun`.gladkikh.cargologistic.db.entities.UnitMeasureDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UnitMeasureDao {
    @Query("SELECT * FROM unitmeasure WHERE guid = :guid")
    fun getUnitMeasureByGuid(guid: String): Flowable<UnitMeasureDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnitMeasure(goods: UnitMeasureDB): Completable

    @Query("DELETE FROM unitmeasure")
    fun deleteAllUnitMeasures()

    @Query("SELECT * FROM unitmeasure WHERE guidProduct = :guid")
    fun getUnitMeasureByProductGuidFlowableList(guid: String): Flowable<List<UnitMeasureDB>>
}