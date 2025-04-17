package viewmodel

import android.util.Log
import androidx.lifecycle.*
import data.AppRepository
import data.entities.UzivatelEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _user = MutableLiveData<UzivatelEntity?>()
    val user: LiveData<UzivatelEntity?> = _user

    private val _caloriesConsumed = MutableLiveData<Int>()
    val caloriesConsumed: LiveData<Int> = _caloriesConsumed

    private val _activityCompleted = MutableLiveData<Int>()
    val activityCompleted: LiveData<Int> = _activityCompleted

    private val _moodScore = MutableLiveData<Int>()
    val moodScore: LiveData<Int> = _moodScore

    private val _waterConsumed = MutableLiveData<Int>()
    val waterConsumed: LiveData<Int> = _waterConsumed

    fun loadUserData(userId: Int) {
        viewModelScope.launch {
            _user.value = repository.getUzivatelById(userId)
            loadUserMetrics()
        }
    }

    private fun loadUserMetrics() {
        viewModelScope.launch {
            val user = _user.value
            val today = getCurrentDate() // napr. "6.4.2025"

            if (user != null) {
                _caloriesConsumed.value = repository.getTotalCaloriesForToday(today) ?: 0
                _activityCompleted.value = repository.getTotalActivityForToday(today) ?: 0

                val calendar = Calendar.getInstance()
                // Začiatok týždňa (napr. pondelok)
                calendar.firstDayOfWeek = Calendar.MONDAY
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                val startDate =
                    SimpleDateFormat("d.M.yyyy", Locale.getDefault()).format(calendar.time)

                // Koniec týždňa (napr. nedeľa)
                calendar.add(Calendar.DAY_OF_WEEK, 6)
                val endDate =
                    SimpleDateFormat("d.M.yyyy", Locale.getDefault()).format(calendar.time)
                // Log dátumov pre kontrolu
                Log.d("MainViewModel", "Weekly period: $startDate - $endDate")
                _moodScore.value = repository.getWeeklyMoodAverage(startDate, endDate) ?: 0

                val dailyIntake =
                    repository.pitnyRezimDao.getDailyIntake(user.id, today).first() ?: 0
                _waterConsumed.value = dailyIntake
            }
        }
    }


    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("d.M.yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}