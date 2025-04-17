package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cyklus")
data class CyklusEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val datum: String,
    val popis: String
)
