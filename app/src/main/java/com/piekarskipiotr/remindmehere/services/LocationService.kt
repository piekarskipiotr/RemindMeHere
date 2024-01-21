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
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var reminderRepository: ReminderRepository
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 10000
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
        handler.post(runnableCode)
        return START_STICKY
    }

    private fun checkNearbyReminders() {
        val currentLocation = getCurrentLocation()
        val reminders = reminderRepository.getReminders().value

        reminders?.forEach { reminder ->
            val reminderLocation = Location("").apply {
                latitude = reminder.latitude
                longitude = reminder.longitude
            }

            currentLocation?.distanceTo(reminderLocation)?.let { distance ->
                if (distance <= 300) {
                    sendNotification(reminder)
                }
            }
        }
    }

    private fun getCurrentLocation(): Location? {
        var currentLocation: Location? = null
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location -> currentLocation = location }

        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener
            )

            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: SecurityException) {
            println("Error getting current location in service: $e")
        }

        return currentLocation
    }

    private fun sendNotification(reminder: Reminder) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1

        val channel = NotificationChannel(
            "channel_id",
            "Channel Name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)


        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("Hey you have some business near")
            .setContentText("Description: ${reminder.description}")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
