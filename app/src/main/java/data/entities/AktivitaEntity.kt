package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aktivity")
data class AktivitaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val datum: String,
    val trvanie: Int,
    val nazovAktivity: String
)
