package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.AppRepository

class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(UserConfigViewModel::class.java) -> {
                UserConfigViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ActivityViewModel::class.java) -> {
                ActivityViewModel(repository.aktivitaDao) as T
            }

            modelClass.isAssignableFrom(ZaznamenatCyklusViewModel::class.java) -> {
                ZaznamenatCyklusViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ZaznamenatPocityViewModel::class.java) -> {
                ZaznamenatPocityViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ZaznamenatStravuViewModel::class.java) -> {
                ZaznamenatStravuViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PitnyRezimViewModel::class.java) -> {
                PitnyRezimViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CyklusConfigViewModel::class.java) -> {
                CyklusConfigViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
