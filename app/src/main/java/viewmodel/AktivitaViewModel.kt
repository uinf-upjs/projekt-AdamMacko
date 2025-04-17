package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.dao.AktivitaDao
import data.entities.AktivitaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityViewModel(private val activityDao: AktivitaDao) : ViewModel() {

    fun saveActivity(date: String, duration: Int, activityName: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userId = 1
                val aktivita = AktivitaEntity(
                    userId = userId,
                    datum = date,
                    trvanie = duration,
                    nazovAktivity = activityName
                )

                activityDao.insertAktivita(aktivita)
            }
            onComplete()
        }
    }
}
