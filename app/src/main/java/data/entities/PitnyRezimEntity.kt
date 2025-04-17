package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pitny_rezim")
data class PitnyRezimEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val datum: String,
    val objem: Int
)