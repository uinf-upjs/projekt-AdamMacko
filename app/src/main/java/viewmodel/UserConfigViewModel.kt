package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.dao.UzivatelDao
import data.entities.UzivatelEntity
import kotlinx.coroutines.launch

class UserConfigViewModel(private val repository: AppRepository) : ViewModel() {

    fun loadUserData(onResult: (UzivatelEntity?) -> Unit) {
        viewModelScope.launch {
            val user = repository.uzivatelDao.getUzivatelById(1)
            onResult(user)
        }
    }

    fun saveUserData(user: UzivatelEntity) {
        viewModelScope.launch {
            repository.uzivatelDao.insertUzivatel(user)
        }
    }
}
