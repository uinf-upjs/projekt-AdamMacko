package sk.upjs.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import data.AppDatabase
import data.AppRepository
import data.entities.UzivatelEntity
import viewmodel.AppViewModelFactory
import viewmodel.UserConfigViewModel

class UserConfigActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var editTextGoalActivity: EditText
    private lateinit var editTextGoalNutrition: EditText
    private lateinit var editTextGoalMood: EditText
    private lateinit var editTextGoalWater: EditText
    private lateinit var buttonSave: Button

    private val viewModel: UserConfigViewModel by viewModels {
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
        setContentView(R.layout.activity_user_config)

        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextWeight = findViewById(R.id.editTextWeight)
        editTextHeight = findViewById(R.id.editTextHeight)
        editTextGoalActivity = findViewById(R.id.editTextGoalActivity)
        editTextGoalNutrition = findViewById(R.id.editTextGoalNutrition)
        editTextGoalMood = findViewById(R.id.editTextGoalMood)
        editTextGoalWater = findViewById(R.id.editTextWater)
        buttonSave = findViewById(R.id.buttonSave)


        viewModel.loadUserData { user ->
            user?.let {
                editTextName.setText(it.meno)
                editTextAge.setText(it.vek.toString())
                editTextWeight.setText(it.vaha.toString())
                editTextHeight.setText(it.vyska.toString())
                editTextGoalActivity.setText(it.aktivitaCiel.toString())
                editTextGoalNutrition.setText(it.kalorieCiel.toString())
                editTextGoalMood.setText(it.pocityCiel.toString())
                editTextGoalWater.setText(it.vodaCiel.toString())
            }
        }

        buttonSave.setOnClickListener {
            val user = UzivatelEntity(
                id = 1,
                meno = editTextName.text.toString(),
                vek = editTextAge.text.toString().toIntOrNull() ?: 0,
                vaha = editTextWeight.text.toString().toFloatOrNull() ?: 0f,
                vyska = editTextHeight.text.toString().toFloatOrNull() ?: 0f,
                aktivitaCiel = editTextGoalActivity.text.toString().toIntOrNull() ?: 0,
                kalorieCiel = editTextGoalNutrition.text.toString().toIntOrNull() ?: 0,
                pocityCiel = editTextGoalMood.text.toString().toIntOrNull() ?: 0,
                vodaCiel = editTextGoalWater.text.toString().toIntOrNull() ?: 0

            )

            viewModel.saveUserData(user)
        }
    }
}
