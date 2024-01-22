package com.piekarskipiotr.remindmehere.data.repositories


import com.piekarskipiotr.remindmehere.data.AppDatabase
import com.piekarskipiotr.remindmehere.data.entities.Reminder
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val database: AppDatabase,
) {
    fun getRemindersLiveData() = database.reminderDao().getRemindersLiveData()
    fun getReminders() = database.reminderDao().getReminders()

    suspend fun insert(reminder: Reminder) {
        database.reminderDao().insert(reminder)
    }

    suspend fun delete(reminder: Reminder) {
        database.reminderDao().delete(reminder)
    }
}