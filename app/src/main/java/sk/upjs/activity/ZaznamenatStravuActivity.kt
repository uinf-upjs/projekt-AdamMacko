package sk.upjs.activity

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import viewmodel.ZaznamenatStravuViewModel
import data.AppDatabase
import data.AppRepository
import data.entities.StravaEntity
import viewmodel.AppViewModelFactory

class ZaznamenatStravuActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var spinnerType: Spinner
    private lateinit var editTextFood: EditText
    private lateinit var buttonSave: Button
    private lateinit var editTextKcal: EditText
    private var selectedDate: String = ""

    private val viewModel: ZaznamenatStravuViewModel by viewModels {
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
        setContentView(R.layout.activity_zaznamenat_stravu)

        initViews()
        setupSpinner()
        setupCalendar()
        setupSaveButton()
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendarView)
        spinnerType = findViewById(R.id.spinnerType)
        editTextFood = findViewById(R.id.editTextText)
        buttonSave = findViewById(R.id.button)
        editTextKcal = findViewById(R.id.editTextKcal)
    }

    private fun setupSpinner() {
        val activities = listOf(" ", "Raňajky", "Desiata", "Obed", "Olovrant", "Večera")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val descrFoodText = editTextFood.text.toString().trim()
            val foodType = spinnerType.selectedItem.toString()
            val kcal = editTextKcal.text.toString().trim().toIntOrNull() ?: 0

            if (selectedDate.isEmpty()) {
                showToast("Vyberte dátum!")
                return@setOnClickListener
            }
            if (descrFoodText.isEmpty()) {
                showToast("Zapíšte si svoje jedlo")
                return@setOnClickListener
            }
            if (kcal == 0) {
                showToast("Zapíšte si kalórie")
                return@setOnClickListener
            }

            viewModel.saveActivity(selectedDate, descrFoodText, foodType, kcal) {
                showToast("Strava uložená!")
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
