package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.entities.CyklusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CyklusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCyklus(cyklus: CyklusEntity)

    @Query("SELECT * FROM cyklus WHERE id = :cyklusId")
    suspend fun getCyklusById(cyklusId: Int): CyklusEntity?

    @Query("SELECT * FROM cyklus")
    fun getAllCyklus(): Flow<List<CyklusEntity>>

    @Update
    suspend fun updateCyklus(cyklus: CyklusEntity)

    @Delete
    suspend fun deleteCyklus(cyklus: CyklusEntity)

    @Query("SELECT * FROM cyklus ORDER BY datum DESC LIMIT 1")
    suspend fun getLastCycle(): CyklusEntity?

}