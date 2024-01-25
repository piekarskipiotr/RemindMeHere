package com.piekarskipiotr.remindmehere.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.piekarskipiotr.remindmehere.data.entities.Reminder
import com.piekarskipiotr.remindmehere.data.repositories.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val _currentLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val currentLocation: StateFlow<LatLng> = _currentLocation

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return
            _currentLocation.value = LatLng(location.latitude, location.longitude)
        }
    }

    /*fun getReminders(

    ) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        reminderRepository.getRemindersLiveData()
    }*/

    fun getReminders(): LiveData<List<Reminder>> {
        return reminderRepository.getRemindersLiveData()
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.delete(reminder)
        }
    }

    init {
        startLocationUpdates()
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

    override fun onCleared() {
        super.onCleared()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}