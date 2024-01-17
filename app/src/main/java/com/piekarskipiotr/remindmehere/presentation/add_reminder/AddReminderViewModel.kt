package com.piekarskipiotr.remindmehere.presentation.add_reminder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piekarskipiotr.remindmehere.data.entities.Reminder
import com.piekarskipiotr.remindmehere.data.repositories.ReminderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class AddReminderViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    fun insert(
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