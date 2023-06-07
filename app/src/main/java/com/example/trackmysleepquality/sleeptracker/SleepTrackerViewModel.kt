package com.example.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.trackmysleepquality.databse.SleepDatabaseDao

class SleepTranckerViewModel (val database : SleepDatabaseDao, application: Application)
                                                          :  AndroidViewModel(application){

    }



