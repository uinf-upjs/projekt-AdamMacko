package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.entities.PocityEntity
import data.entities.UzivatelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PocityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPocity(pocity: PocityEntity)

    @Query("SELECT * FROM pocity WHERE id = :pocityId")
    suspend fun getPocityById(pocityId: Int): PocityEntity?

    @Query("SELECT * FROM pocity")
    fun getAllPocity(): Flow<List<PocityEntity>>


    @Update
    suspend fun updatePocity(pocity: PocityEntity)

    @Delete
    suspend fun deletePocity(pocity: PocityEntity)

    @Query("SELECT AVG(hodnotenie) FROM pocity WHERE datum BETWEEN :startDate AND :endDate")
    suspend fun getWeeklyMoodAverage(startDate: String, endDate: String): Int?


}