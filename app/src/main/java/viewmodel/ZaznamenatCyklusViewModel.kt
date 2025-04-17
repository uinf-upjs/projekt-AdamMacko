package viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.entities.CyklusEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ZaznamenatCyklusViewModel(private val repository: AppRepository) : ViewModel() {
    fun saveCyklus(date: String, popis: String, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = 1
            val cyklus = CyklusEntity(
                userId = userId,
                datum = date,
                popis = popis
            )
            repository.cyklusDao.insertCyklus(cyklus)
            onComplete()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPredictedNextCycle(onResult: (LocalDate?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = 1
            val config = repository.cyklusConfigDao.getConfig(userId)
            val lastCycle = repository.cyklusDao.getLastCycle()

            if (config != null && lastCycle != null) {
                val formatter = DateTimeFormatter.ofPattern("d.M.yyyy")
                val lastDate = LocalDate.parse(lastCycle.datum, formatter)
                val nextDate = lastDate.plusDays(config.priemernaDlzka.toLong())
                onResult(nextDate)
            } else {
                onResult(null)
            }
        }
    }


}
