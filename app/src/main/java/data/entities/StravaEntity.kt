package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strava")
data class StravaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val datum: String,
    val typJedla: String,
    val popisJedla: String,
    val kcal : Int

)
