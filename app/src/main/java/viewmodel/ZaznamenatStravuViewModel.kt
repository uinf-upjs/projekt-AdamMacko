package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.dao.StravaDao
import data.entities.StravaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ZaznamenatStravuViewModel(private val repository: AppRepository) : ViewModel() {

    fun saveActivity(date: String, description: String, foodType: String, kcal: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userId = 1
                val strava = StravaEntity(
                    userId = userId,
                    datum = date,
                    typJedla = foodType,
                    popisJedla = description,
                    kcal = kcal
                )
                repository.stravaDao.insertStrava(strava)
            }
            onSuccess()
        }
    }
}
