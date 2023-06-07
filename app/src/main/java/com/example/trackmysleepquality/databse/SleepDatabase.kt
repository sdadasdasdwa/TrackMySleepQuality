package com.example.trackmysleepquality.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [SleepNight::class], exportSchema = false)
abstract class SleepDatabase : RoomDatabase(){
    abstract val sleepDatabase : SleepDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: SleepDatabase?= null


        fun getInstance(context: Context): SleepDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                                                    SleepDatabase::class.java,
                                               "sleep_history_database" )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}