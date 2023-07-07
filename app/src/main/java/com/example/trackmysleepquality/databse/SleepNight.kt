package com.example.trackmysleepquality.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(

    @PrimaryKey(autoGenerate = true)
    var nightId: Long,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long? ,

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long? ,

    @ColumnInfo(name = "quality_rating")
    var sleepQuality: Int?
)







