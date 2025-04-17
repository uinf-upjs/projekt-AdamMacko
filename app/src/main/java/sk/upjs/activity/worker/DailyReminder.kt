package sk.upjs.activity.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import data.AppDatabase
import data.AppRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.first
import sk.upjs.activity.R

class DailyReminder(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val context = applicationContext
        val database = AppDatabase.getDatabase(context)
        val repository = AppRepository(
            database.uzivatelDao(),
            database.aktivitaDao(),
            database.cyklusDao(),
            database.pocityDao(),
            database.stravaDao(),
            database.pitnyRezimDao(),
            database.cyklusConfigDao()
        )

        val user = repository.getUzivatelById(1) ?: return Result.success()
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("d.M.yyyy"))

        val kalorie = repository.getTotalCaloriesForToday(today)
        val aktivita = repository.getTotalActivityForToday(today)
        val voda = repository.pitnyRezimDao.getDailyIntake(user.id, today).first() ?: 0

        val chybajuce = mutableListOf<String>()
        if (kalorie < user.kalorieCiel) chybajuce.add("kalórie")
        if (aktivita < user.aktivitaCiel) chybajuce.add("aktivita")
        if (voda < user.vodaCiel) chybajuce.add("pitný režim")

        if (chybajuce.isNotEmpty()) {
            val sprava = "Ešte máš čas! Dokonči: ${chybajuce.joinToString(", ")}"
            sendNotification(context, "Denné ciele", sprava)
        }

        return Result.success()
    }

    private fun sendNotification(context: Context, title: String, message: String) {
        val channelId = "daily_goals_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Denné pripomienky",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1001, notification)
    }



}
