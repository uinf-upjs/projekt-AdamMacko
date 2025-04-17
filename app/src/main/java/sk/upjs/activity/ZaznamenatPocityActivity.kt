package sk.upjs.activity

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import data.AppDatabase
import data.AppRepository
import viewmodel.ZaznamenatPocityViewModel
import viewmodel.AppViewModelFactory

class ZaznamenatPocityActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var calendarView: CalendarView
    private lateinit var editText: EditText
    private lateinit var buttonSave: Button
    private var selectedDate: String = ""

    private val viewModel: ZaznamenatPocityViewModel by viewModels {
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
        setContentView(R.layout.activity_zaznamenat_pocity)

        initViews()
        setupSpinner()
        setupCalendar()
        setupSaveButton()
    }

    private fun initViews() {
        calendarView = findViewById(R.id.calendarView)
        spinner = findViewById(R.id.spinner)
        editText = findViewById(R.id.editTextText)
        buttonSave = findViewById(R.id.button)
    }

    private fun setupSpinner() {
        val activities = listOf(" ", "1", "2", "3", "4", "5")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }
    }

    private fun setupSaveButton() {
        buttonSave.setOnClickListener {
            val descrText = editText.text.toString().trim()
            val markedFeelings = spinner.selectedItem.toString().toInt()

            if (selectedDate.isEmpty()) {
                showToast("Vyberte dátum!")
                return@setOnClickListener
            }
            if (descrText.isEmpty()) {
                showToast("Zapíšte si svoje pocity")
                return@setOnClickListener
            }

            viewModel.savePocity(selectedDate, descrText, markedFeelings)
            showToast("Pocity uložené!")
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
