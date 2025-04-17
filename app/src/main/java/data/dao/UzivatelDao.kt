package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.entities.UzivatelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UzivatelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUzivatel(uzivatel: UzivatelEntity)

    @Query("SELECT * FROM uzivatelInfo WHERE id = :uzivatelId")
    suspend fun getUzivatelById(uzivatelId: Int): UzivatelEntity?


    @Query("SELECT * FROM uzivatelInfo")
    fun getAllUzivatel(): Flow<List<UzivatelEntity>>

    @Update
    suspend fun updateUzivatel(uzivatel: UzivatelEntity)

    @Delete
    suspend fun deleteUzivatel(uzivatel: UzivatelEntity)
}