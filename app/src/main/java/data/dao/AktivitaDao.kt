package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.entities.AktivitaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AktivitaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAktivita(aktivita: AktivitaEntity)

    @Query("SELECT * FROM aktivity WHERE id = :aktivitaId")
    suspend fun getAktivitaById(aktivitaId: Int): AktivitaEntity?


    @Query("SELECT * FROM aktivity")
    fun getAllAktivita(): Flow<List<AktivitaEntity>>


    @Update
    suspend fun updateAktivita(aktivita: AktivitaEntity)

    @Delete
    suspend fun deleteAktivita(aktivita: AktivitaEntity)

    @Query("SELECT SUM(trvanie) FROM aktivity WHERE datum = :today")
    suspend fun getTotalActivityForToday(today: String): Int?

}