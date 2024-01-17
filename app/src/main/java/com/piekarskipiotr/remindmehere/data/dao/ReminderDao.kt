package com.piekarskipiotr.remindmehere.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.piekarskipiotr.remindmehere.data.entities.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getReminders(): LiveData<List<Reminder>>

    @Insert
    suspend fun insert(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)
}