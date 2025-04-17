package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uzivatelInfo")
data class UzivatelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meno : String,
    val vek : Int,
    val vaha : Float,
    val vyska : Float,
    val aktivitaCiel : Int,
    val kalorieCiel : Int,
    val pocityCiel : Int,
    val vodaCiel : Int
)
