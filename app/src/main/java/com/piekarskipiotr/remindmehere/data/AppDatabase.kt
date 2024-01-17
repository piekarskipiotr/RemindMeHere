package com.piekarskipiotr.remindmehere.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.piekarskipiotr.remindmehere.data.dao.ReminderDao
import com.piekarskipiotr.remindmehere.data.entities.Reminder

@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private const val DATABASE_NAME: String = "remind_me_here_database"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            CoroutineScope(Dispatchers.Default).launch {
//                                instance!!.shoppingListDao().insert(ShoppingList(1, "Example list", 1, 2, Date(), false))
//                                instance!!.groceryDao().insert(Grocery(0, "Banana", 7, false, 1))
//                                instance!!.groceryDao().insert(Grocery(0, "Blueberry", 47, true, 1))
//                            }
                        }
                    })
                    .build()
            return instance!!
        }
    }
}