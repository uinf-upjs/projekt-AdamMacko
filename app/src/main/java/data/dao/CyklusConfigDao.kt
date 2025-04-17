package data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.entities.CyklusConfigEntity

@Dao
interface CyklusConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(config: CyklusConfigEntity)

    @Query("SELECT * FROM cyklus_config WHERE userId = :userId")
    suspend fun getConfig(userId: Int): CyklusConfigEntity?
}
