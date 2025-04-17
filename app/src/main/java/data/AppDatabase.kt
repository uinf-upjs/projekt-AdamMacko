package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.dao.AktivitaDao
import data.dao.CyklusConfigDao
import data.dao.CyklusDao
import data.dao.PitnyRezimDao
import data.dao.PocityDao
import data.dao.StravaDao
import data.dao.UzivatelDao
import data.entities.StravaEntity
import data.entities.UzivatelEntity
import data.entities.PocityEntity
import data.entities.CyklusEntity
import data.entities.AktivitaEntity
import data.entities.CyklusConfigEntity
import data.entities.PitnyRezimEntity

@Database(
    entities = [
        UzivatelEntity::class,
        AktivitaEntity::class,
        CyklusEntity::class,
        StravaEntity::class,
        PocityEntity::class,
        PitnyRezimEntity::class,
        CyklusConfigEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun uzivatelDao(): UzivatelDao
    abstract fun aktivitaDao(): AktivitaDao
    abstract fun cyklusDao(): CyklusDao
    abstract fun stravaDao(): StravaDao
    abstract fun pocityDao(): PocityDao
    abstract fun pitnyRezimDao(): PitnyRezimDao
    abstract fun cyklusConfigDao(): CyklusConfigDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
