package sk.upjs.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import data.AppDatabase
import data.AppRepository
import viewmodel.AppViewModelFactory
import viewmodel.ZaznamenatCyklusViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ZaznamenatCyklusActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var buttonSave: Button
    private lateinit var editTextPopis: EditText
    private var selectedDate: String = ""

    private val viewModel: ZaznamenatCyklusViewModel by viewModels {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zaznamenat_cyklus)

        calendarView = findViewById(R.id.calendarView)
        buttonSave = findViewById(R.id.button)
        editTextPopis = findViewById(R.id.editTextPopis)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }

        val btnOpenConfig = findViewById<Button>(R.id.buttonOpenConfig)
        btnOpenConfig.setOnClickListener {
            startActivity(Intent(this, CyklusConfigActivity::class.java))
        }


        buttonSave.setOnClickListener {
            val popis = editTextPopis.text.toString().trim()

            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Prosím vyber dátum", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (popis.isEmpty()) {
                Toast.makeText(this, "Zadaj popis cyklu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.saveCyklus(selectedDate, popis) {
                runOnUiThread {
                    Toast.makeText(this, "Cyklus uložený!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }


        val tvNextCycleInfo = findViewById<TextView>(R.id.tvNextCycleInfo)

        viewModel.getPredictedNextCycle { nextDate ->
            runOnUiThread {
                if (nextDate != null) {
                    val formatted = nextDate.format(DateTimeFormatter.ofPattern("d.M.yyyy"))
                    tvNextCycleInfo.text = "Predpokladaný začiatok cyklu: $formatted"
                } else {
                    tvNextCycleInfo.text = "Predpokladaný cyklus: nedostupné"
                }
            }
        }

    }
}
