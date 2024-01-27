package com.piekarskipiotr.remindmehere.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.piekarskipiotr.remindmehere.R
import com.piekarskipiotr.remindmehere.data.entities.Reminder
import com.piekarskipiotr.remindmehere.data.repositories.ReminderRepository
import javax.inject.Inject
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var reminderRepository: ReminderRepository
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 10000
    private val channelID: String = "REMIND_ME_HERE_CHANNEL_ID"
    private val channelName: String = "REMIND_ME_HERE_CHANNEL"
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val runnableCode = object : Runnable {
        override fun run() {
            checkNearbyReminders()
            handler.postDelayed(this, checkInterval)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnableCode)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        handler.post(runnableCode)
        return START_STICKY
    }

    private fun checkNearbyReminders() {
        val reminders = reminderRepository.getReminders()
        getCurrentLocation { currentLocation ->
            println("REMINDMEHEREDEBUGER: current location $currentLocation")
            currentLocation.let {
                currentLocation?.let { location ->
                    reminders.forEach { reminder ->
                        val reminderLocation = Location("").apply {
                            latitude = reminder.latitude
                            longitude = reminder.longitude
                        }

                        val distance = location.distanceTo(reminderLocation)
                        println("REMINDMEHEREDEBUGER: reminder ($reminderLocation) distance $distance")
                        if (distance <= 300) {
                            sendNotification(reminder)
                            deleteReminder(reminder)
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentLocation(callback: (Location?) -> Unit) {
        try {
            val currentLocationRequest = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            fusedLocationProviderClient.getCurrentLocation(currentLocationRequest, null)
                .addOnSuccessListener { location ->
                    callback(location)
                }
                .addOnFailureListener { e ->
                    println("Failed to get current location: $e")
                    callback(null)
                }
        } catch (e: SecurityException) {
            println("Error getting current location in service: $e")
            callback(null)
        }
    }


    private fun sendNotification(reminder: Reminder) {
        println("REMINDMEHEREDEBUGER: should send notification to user")
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1

        val channel = NotificationChannel(
            channelID, channelName, NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)


        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Hey, you seem to have some business nearby.")
            .setContentText("Description: ${reminder.description}")
            .setSmallIcon(R.drawable.ic_notification).build()

        notificationManager.notify(notificationId, notification)
    }

    private fun deleteReminder(reminder: Reminder) {
        CoroutineScope(Dispatchers.IO).launch {
            reminderRepository.delete(reminder)
        }
    }
}
