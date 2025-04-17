package sk.upjs.activity

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import data.AppDatabase
import data.AppRepository
import data.entities.CyklusConfigEntity
import viewmodel.AppViewModelFactory
import viewmodel.CyklusConfigViewModel

class CyklusConfigActivity : AppCompatActivity() {

    private lateinit var etCycleLength: EditText
    private lateinit var etPeriodLength: EditText
    private lateinit var etPmsLength: EditText
    private lateinit var etTyp: EditText
    private lateinit var btnSave: Button

    private val viewModel: CyklusConfigViewModel by viewModels {
        val db = AppDatabase.getDatabase(this)
        AppViewModelFactory(
            AppRepository(
                db.uzivatelDao(),
                db.aktivitaDao(),
                db.cyklusDao(),
                db.pocityDao(),
                db.stravaDao(),
                db.pitnyRezimDao(),
                db.cyklusConfigDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cyklus_config)

        etCycleLength = findViewById(R.id.etCycleLength)
        etPeriodLength = findViewById(R.id.etPeriodLength)
        etPmsLength = findViewById(R.id.etPmsLength)
        etTyp = findViewById(R.id.etTyp)
        btnSave = findViewById(R.id.btnSave)

        val userId = 1

        viewModel.loadConfig(userId) { config ->
            runOnUiThread {
                config?.let {
                    etCycleLength.setText(it.priemernaDlzka.toString())
                    etPeriodLength.setText(it.dlzkaMenstruacie.toString())
                    etPmsLength.setText(it.pmsDlzka.toString())
                    etTyp.setText(it.typCyklu)
                }
            }
        }

        btnSave.setOnClickListener {
            val config = CyklusConfigEntity(
                userId = userId,
                priemernaDlzka = etCycleLength.text.toString().toIntOrNull() ?: 28,
                dlzkaMenstruacie = etPeriodLength.text.toString().toIntOrNull() ?: 5,
                pmsDlzka = etPmsLength.text.toString().toIntOrNull() ?: 3,
                typCyklu = etTyp.text.toString()
            )

            viewModel.saveConfig(config) {
                runOnUiThread {
                    Toast.makeText(this, "Konfigurácia uložená", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
