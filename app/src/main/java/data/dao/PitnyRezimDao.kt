package data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.entities.PitnyRezimEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PitnyRezimDao {
    @Insert
    suspend fun insertPitnyRezim(pitnyRezim: PitnyRezimEntity)

    @Query("SELECT SUM(objem) FROM pitny_rezim WHERE userId = :userId AND datum = :datum")
    fun getDailyIntake(userId: Int, datum: String): Flow<Int?>
}
