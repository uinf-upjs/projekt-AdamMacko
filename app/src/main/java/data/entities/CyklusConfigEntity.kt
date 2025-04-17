package data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cyklus_config")
data class CyklusConfigEntity(
    @PrimaryKey val userId: Int,
    val priemernaDlzka: Int,
    val dlzkaMenstruacie: Int,
    val pmsDlzka: Int,
    val typCyklu: String
)
