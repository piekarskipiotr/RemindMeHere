package com.piekarskipiotr.remindmehere.di

import android.content.Context
import com.piekarskipiotr.remindmehere.data.AppDatabase
import com.piekarskipiotr.remindmehere.data.repositories.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideRemindersRepository(
        database: AppDatabase,
    ) = ReminderRepository(database)
}