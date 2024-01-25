package com.piekarskipiotr.remindmehere.presentation.add_reminder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piekarskipiotr.remindmehere.data.entities.Reminder
import com.piekarskipiotr.remindmehere.data.repositories.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _insertionSuccess = MutableLiveData<Boolean>()
    val insertionSuccess: LiveData<Boolean> = _insertionSuccess
    private var longitude by mutableDoubleStateOf(0.0)
    private var latitude by mutableDoubleStateOf(0.0)
    var description by mutableStateOf("")
    var descriptionError by mutableStateOf<String?>(null)

    fun updateLocation(newLongitude: Double, newLatitude: Double) {
        longitude = newLongitude
        latitude = newLatitude
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        descriptionError = if (description.isEmpty()) "Pole nie może być puste" else null
    }

    fun onSave() {
        descriptionError = if (description.isEmpty()) "Pole nie może być puste" else null
        if (descriptionError == null) {
            insert(latitude, longitude, description)
            _insertionSuccess.postValue(true)
        }
    }



    private fun insert(
        latitude: Double,
        longitude: Double,
        description: String,
    ) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        val reminder = Reminder(Date().time, latitude, longitude, description)
        reminderRepository.insert(reminder)
    }
}