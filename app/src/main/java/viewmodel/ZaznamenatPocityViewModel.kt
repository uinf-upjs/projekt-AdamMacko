package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.dao.PocityDao
import data.entities.PocityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ZaznamenatPocityViewModel(private val repository: AppRepository) : ViewModel() {

    fun savePocity(date: String, description: String, mark: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = 1
            val pocity = PocityEntity(
                userId = userId,
                datum = date,
                popis = description,
                hodnotenie = mark
            )
           repository.pocityDao.insertPocity(pocity)
        }
    }
}
