package data

import data.dao.AktivitaDao
import data.dao.CyklusConfigDao
import data.dao.CyklusDao
import data.dao.PitnyRezimDao
import data.dao.PocityDao
import data.dao.StravaDao
import data.dao.UzivatelDao
import data.entities.AktivitaEntity
import data.entities.CyklusEntity
import data.entities.PitnyRezimEntity
import data.entities.PocityEntity
import data.entities.StravaEntity
import data.entities.UzivatelEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class AppRepository (
    val uzivatelDao: UzivatelDao,
    val aktivitaDao: AktivitaDao,
    val cyklusDao: CyklusDao,
    val pocityDao: PocityDao,
    val stravaDao: StravaDao,
    val pitnyRezimDao: PitnyRezimDao,
    val cyklusConfigDao: CyklusConfigDao
){
    //PITNY REZIM
    fun getDailyIntake(userId: Int, datum: String): Flow<Int?> = pitnyRezimDao.getDailyIntake(userId, datum)
    suspend fun insertPitnyRezim(pitnyRezim: PitnyRezimEntity) = pitnyRezimDao.insertPitnyRezim(pitnyRezim)

    //UŽÍVATEĽ
    suspend fun getUzivatelById(id: Int): UzivatelEntity? = uzivatelDao.getUzivatelById(id)
    suspend fun insertUzivatel(uzivatel: UzivatelEntity) = uzivatelDao.insertUzivatel(uzivatel)
    suspend fun updateUzivatel(uzivatel: UzivatelEntity) = uzivatelDao.updateUzivatel(uzivatel)
    suspend fun deleteUzivatel(uzivatel: UzivatelEntity) = uzivatelDao.deleteUzivatel(uzivatel)

    //AKTIVITY
    fun getAllAktivity(): Flow<List<AktivitaEntity>> = aktivitaDao.getAllAktivita()
    suspend fun insertAktivita(aktivita: AktivitaEntity) = aktivitaDao.insertAktivita(aktivita)
    suspend fun getTotalActivityForToday(today: String): Int {
        return aktivitaDao.getTotalActivityForToday(today) ?: 0
    }
    //CYKLUS
    fun getAllCykly(): Flow<List<CyklusEntity>> = cyklusDao.getAllCyklus()
    suspend fun insertCyklus(cyklus: CyklusEntity) = cyklusDao.insertCyklus(cyklus)

    //POCITY
    fun getAllPocity(): Flow<List<PocityEntity>> = pocityDao.getAllPocity()
    suspend fun insertPocity(pocity: PocityEntity) = pocityDao.insertPocity(pocity)
    fun getWeeklyMoodAverage(startDate: String, endDate: String): Int? {
        return runBlocking { pocityDao.getWeeklyMoodAverage(startDate, endDate) }
    }


    //STRAVA
    fun getAllStrava(): Flow<List<StravaEntity>> = stravaDao.getAllStrava()
    suspend fun insertStrava(strava: StravaEntity) = stravaDao.insertStrava(strava)

    suspend fun getTotalCaloriesForToday(date: String): Int {
        return stravaDao.getTotalCaloriesForToday(date) ?: 0
    }
}