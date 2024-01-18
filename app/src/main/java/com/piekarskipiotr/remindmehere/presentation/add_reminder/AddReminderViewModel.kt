package com.piekarskipiotr.remindmehere.presentation.add_reminder

import androidx.compose.runtime.*
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
    var place by mutableStateOf("")
    var description by mutableStateOf("")
    var sliderValue by mutableFloatStateOf(0f)
    var placeError by mutableStateOf<String?>(null)
    var descriptionError by mutableStateOf<String?>(null)

    // Methods to update the state
    fun onPlaceChange(newPlace: String) {
        place = newPlace
        placeError = if (place.isEmpty()) "Pole nie może być puste" else null
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        descriptionError = if (description.isEmpty()) "Pole nie może być puste" else null
    }

    fun onSliderValueChange(newValue: Float) {
        sliderValue = newValue
    }

    fun onSave() {
        if (placeError == null && descriptionError == null) {
            // TODO: Implement the data insertion logic
        }
    }

    private fun insert(
        place: String,
        latitude: Double,
        longitude: Double,
        description: String,
        range: Double
    ) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        val reminder = Reminder(Date().time, place, latitude, longitude, description, range)
        reminderRepository.insert(reminder)
    }
}