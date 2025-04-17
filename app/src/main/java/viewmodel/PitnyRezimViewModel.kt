package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.entities.PitnyRezimEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PitnyRezimViewModel(private val repository: AppRepository) : ViewModel() {

    fun saveUserWater(date: String, volume: Int, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = 1
            val pitnyRezim = PitnyRezimEntity(
                userId = userId,
                datum = date,
                objem = volume
            )
            repository.pitnyRezimDao.insertPitnyRezim(pitnyRezim)
            onComplete()
        }
    }
}
