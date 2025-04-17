package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.entities.StravaEntity
import data.entities.UzivatelEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Dao
interface StravaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStrava(strava: StravaEntity)

    @Query("SELECT * FROM strava WHERE id = :stravaId")
    suspend fun getStravaById(stravaId: Int): StravaEntity?

    @Query("SELECT * FROM strava")
    fun getAllStrava(): Flow<List<StravaEntity>>

    @Update
    suspend fun updateStrava(strava: StravaEntity)

    @Delete
    suspend fun deleteStrava(strava: StravaEntity)

    @Query("SELECT SUM(kcal) FROM strava WHERE datum LIKE :today || '%'")
    suspend fun getTotalCaloriesForToday(today: String): Int?


}