package sk.upjs.activity

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import data.AppDatabase
import data.AppRepository
import viewmodel.AppViewModelFactory
import viewmodel.PitnyRezimViewModel

class PitnyRezimActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var etVolume: EditText
    private lateinit var buttonSave: Button
    private var selectedDate: String = ""

    private val viewModel: PitnyRezimViewModel by viewModels {
        val database = AppDatabase.getDatabase(this)
        val repository = AppRepository(
            database.uzivatelDao(),
            database.aktivitaDao(),
            database.cyklusDao(),
            database.pocityDao(),
            database.stravaDao(),
            database.pitnyRezimDao(),
            database.cyklusConfigDao()
        )
        AppViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pitny_rezim)
        initViews()
        setupCalendar()
        setupSaveButton()
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendarView)
        etVolume = findViewById(R.id.etVolume)
        buttonSave = findViewById(R.id.button)
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val volumeText = etVolume.text.toString().trim()

            if (selectedDate.isEmpty()) {
                showToast("Vyberte dátum!")
                return@setOnClickListener
            }
            if (volumeText.isEmpty()) {
                showToast("Zadajte objem v ml")
                return@setOnClickListener
            }

            val volume = volumeText.toIntOrNull()
            if (volume == null || volume <= 0) {
                showToast("Neplatný objem")
                return@setOnClickListener
            }

            viewModel.saveUserWater(selectedDate, volume) {
                runOnUiThread {
                    showToast("Uložené!")
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
