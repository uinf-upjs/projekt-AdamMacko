package sk.upjs.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import data.AppDatabase
import data.AppRepository
import sk.upjs.activity.worker.DailyReminder
import viewmodel.AppViewModelFactory
import viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var btnZaznamenatAktivitu: Button
    private lateinit var btnPersonConfig: ImageButton
    private lateinit var progressBarAktivita: ProgressBar
    private lateinit var progressBarKalorie: ProgressBar
    private lateinit var progressBarPocity: ProgressBar
    private lateinit var progressBarPitie: ProgressBar

    private val viewModel: MainViewModel by viewModels {
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
        setContentView(R.layout.activity_main)

        initViews()
        setupObservers()

        viewModel.loadUserData(1)

        val testRequest = OneTimeWorkRequestBuilder<DailyReminder>().build()
        WorkManager.getInstance(this).enqueue(testRequest)

        btnZaznamenatAktivitu.setOnClickListener {
            startActivity(Intent(this, MenuZaznamenavaniaActivity::class.java))
        }

        btnPersonConfig.setOnClickListener {
            startActivity(Intent(this, UserConfigActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserData(1)
    }

    private fun initViews() {
        textViewName = findViewById(R.id.hiName)
        btnZaznamenatAktivitu = findViewById(R.id.button1)
        btnPersonConfig = findViewById(R.id.buttonPerson)
        progressBarAktivita = findViewById(R.id.progressBar)
        progressBarKalorie = findViewById(R.id.progressBar2)
        progressBarPocity = findViewById(R.id.progressBar4)
        progressBarPitie = findViewById(R.id.progressBar3)
    }

    private fun setupObservers() {
        viewModel.user.observe(this, Observer { user ->
            textViewName.text = "  Ahoj, \n${user?.meno}"
        })

        viewModel.caloriesConsumed.observe(this, Observer { value ->
            updateProgressBar(progressBarKalorie, value, viewModel.user.value?.kalorieCiel ?: 100)
        })

        viewModel.activityCompleted.observe(this, Observer { value ->
            updateProgressBar(progressBarAktivita, value, viewModel.user.value?.aktivitaCiel ?: 100)
        })

        viewModel.waterConsumed.observe(this, Observer { value ->
            Log.d("MainActivity", "Water consumed observer: $value")
            updateProgressBar(progressBarPitie, value ?: 0, viewModel.user.value?.vodaCiel ?: 100)
        })

        viewModel.moodScore.observe(this, Observer { value ->
            updateProgressBar(progressBarPocity, value, viewModel.user.value?.pocityCiel ?: 100)
        })


    }

    private fun updateProgressBar(progressBar: ProgressBar, current: Int, goal: Int) {
        progressBar.max = goal
        progressBar.progress = current
    }





}
