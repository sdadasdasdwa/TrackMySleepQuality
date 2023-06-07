package com.example.trackmysleepquality.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight (
//    @PrimaryKey(autoGenerate=true)
//    var nightId : Long = 0L,
//
//    @ColumnInfo(name = "start_time_milli")
//    val startTimeMilli: Long = System.currentTimeMillis(),
//
//    @ColumnInfo(name = "end_time_milli")
//    var endTimeMilli : Long = startTimeMilli,
//
//    @ColumnInfo(name = "quality_rating")
//    var sleepQuality : Int = -1
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "quality_rating")
    var sleepQuality: Int = -1

) {
    // 添加一个无参数的构造函数，并使用 @Ignore 注解标记
    @Ignore
    constructor() : this(0L, System.currentTimeMillis(), System.currentTimeMillis(), -1)
}