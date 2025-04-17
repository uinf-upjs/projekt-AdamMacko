package sk.upjs.activity

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import viewmodel.ActivityViewModel
import viewmodel.AppViewModelFactory
import data.AppDatabase
import data.AppRepository

class ZaznamenatAktivituActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var etTime: EditText
    private lateinit var buttonSave: Button
    private lateinit var spinner: Spinner

    private var selectedDate: String = ""

    private val viewModel: ActivityViewModel by viewModels {
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
        setContentView(R.layout.activity_zaznamenat_aktivitu)

        initViews()
        setupCalendar()
        setupSpinner()
        setupSaveButton()
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendarView)
        etTime = findViewById(R.id.etTime)
        buttonSave = findViewById(R.id.button)
        spinner = findViewById(R.id.spinner)
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }
    }

    private fun setupSpinner() {
        val activities = listOf(
            ' ',
            "Chôdza", "Cyklistika", "Beh", "Plávanie", "Posilovanie",
            "Pilátes", "Jóga", "Turistika", "Box", "Iné"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val timeText = etTime.text.toString().trim()
            val activityType = spinner.selectedItem.toString()

            if (selectedDate.isEmpty()) {
                showToast("Vyberte dátum!")
                return@setOnClickListener
            }
            if (timeText.isEmpty()) {
                showToast("Zadajte čas v minútach!")
                return@setOnClickListener
            }

            val timeInMinutes = timeText.toIntOrNull()
            if (timeInMinutes == null || timeInMinutes <= 0) {
                showToast("Neplatný čas! Zadajte celé číslo v minútach.")
                return@setOnClickListener
            }

            viewModel.saveActivity(selectedDate, timeInMinutes, activityType) {
                showToast("Aktivita uložená!")
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
