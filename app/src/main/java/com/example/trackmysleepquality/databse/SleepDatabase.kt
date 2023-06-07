package com.example.trackmysleepquality.databse

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [SleepNight::class], exportSchema = false)
abstract class SleepDatabase : RoomDatabase(){
    abstract val sleepDatabase : SleepDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: SleepDatabase?= null

    }
}