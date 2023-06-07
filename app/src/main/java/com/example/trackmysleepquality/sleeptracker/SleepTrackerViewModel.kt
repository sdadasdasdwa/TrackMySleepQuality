package com.example.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trackmysleepquality.databse.SleepDatabaseDao
import com.example.trackmysleepquality.databse.SleepNight
import kotlinx.coroutines.launch

class SleepTrackerViewModel (val database : SleepDatabaseDao, application: Application)
                                                          :  AndroidViewModel(application){
    private val _tonight = MutableLiveData<SleepNight?>()
    val tonight : LiveData<SleepNight?>
        get() = _tonight

    init {
        initializeTonight()
    }

    private fun initializeTonight(){
        viewModelScope.launch {
            _tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if(night?.endTimeMilli != night?.startTimeMilli){
            night = null
        }
        return night
    }

    fun onStartTracking(){
        viewModelScope.launch {
            val newNight = SleepNight()
            insertNight(newNight)
            _tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insertNight(newNight: SleepNight){
        database.insert(newNight)
    }

    

}



