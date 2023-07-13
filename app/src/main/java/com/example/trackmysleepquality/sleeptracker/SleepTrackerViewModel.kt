package com.example.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.example.trackmysleepquality.databse.SleepDatabaseDao
import com.example.trackmysleepquality.databse.SleepNight
import com.example.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    // tonight指向数据库中id最大即时间最新的数据
    private var tonight = MutableLiveData<SleepNight?>()

    // nights用来获取数据库中全部的数据
    val nights = database.getAllNights()
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    // 封装的navigateToSleepQuality, 当点击STOP按钮后将导航到SleepToQuality页面
    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality : LiveData<SleepNight?>
        get() = _navigateToSleepQuality

    //使用三个变量来控制按钮可见性
    //当tonight.value值为空时，startButton可见
    val startButtonVisible = Transformations.map(tonight){
        it == null
    }

    //当tonight.value值不为空时，stopButton可见
    val stopButtonVisible = Transformations.map(tonight){
        it != null
    }

    //当tonight.value值不为空时，stopButton可见
    val clearButtonVisible = Transformations.map(nights){
        it?.isNotEmpty()
    }

    //使用snackBar通知用户
    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent : LiveData<Boolean>
        get() = _showSnackbarEvent



    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight(
                nightId = 0,
                startTimeMilli = System.currentTimeMillis(),
                endTimeMilli = System.currentTimeMillis(),
                sleepQuality = -1
            )
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        database.insert(night)
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            //点击STOP按钮后，应该导航到SleepQuality页面中
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night: SleepNight){
        database.update(night)
    }

    fun onClearTracking(){
        viewModelScope.launch{
            clear();
            _showSnackbarEvent.value = true
        }
    }

    private suspend fun clear(){
        database.clear()
        tonight.value = null
    }

    //重置navigateToSleepQuality变量的值
    fun doneSleepQuality(){
        _navigateToSleepQuality.value = null
    }

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }


    private val _navigateToSleepDetail = MutableLiveData<Long?>()
    val navigateToSleepDetail:LiveData<Long?>
        get() = _navigateToSleepDetail

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDetail.value = id
    }

    fun onSleepDetailNavigated() {
        _navigateToSleepDetail.value = null
    }

}



