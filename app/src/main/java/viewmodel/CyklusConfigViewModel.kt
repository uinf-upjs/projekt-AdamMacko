package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import data.entities.CyklusConfigEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CyklusConfigViewModel(private val repository: AppRepository) : ViewModel() {

    fun loadConfig(userId: Int, onResult: (CyklusConfigEntity?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val config = repository.cyklusConfigDao.getConfig(userId)
            onResult(config)
        }
    }

    fun saveConfig(config: CyklusConfigEntity, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.cyklusConfigDao.insertOrUpdate(config)
            onComplete()
        }
    }
}
