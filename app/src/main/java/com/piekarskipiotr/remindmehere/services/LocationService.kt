package com.piekarskipiotr.remindmehere.services

import android.annotation.SuppressLint
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
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationService : Service() {
    @Inject
    lateinit var reminderRepository: ReminderRepository
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return
            checkNearbyReminders(LatLng(location.latitude, location.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(5000)
            .build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()
        return START_STICKY
    }

    private fun checkNearbyReminders(currentLatLng: LatLng) {
        val reminders = reminderRepository.getReminders()
        val currentLocation = Location("").apply {
            latitude = currentLatLng.latitude
            longitude = currentLatLng.longitude
        }

        reminders.forEach { reminder ->

            val reminderLocation = Location("").apply {
                latitude = reminder.latitude
                longitude = reminder.longitude
            }
            currentLocation.distanceTo(reminderLocation).let { distance ->
                if (distance <= 300) {
                    sendNotification(reminder)
                    deleteReminder(reminder)
                }
            }
        }
    }

    private fun sendNotification(reminder: Reminder) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1

        val channel = NotificationChannel(
            "channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)


        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle("Hey you have some business near")
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
