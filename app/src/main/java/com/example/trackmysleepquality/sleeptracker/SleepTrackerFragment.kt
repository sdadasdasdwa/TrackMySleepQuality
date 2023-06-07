package com.example.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trackmysleepquality.R
import com.example.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.example.trackmysleepquality.databse.SleepDatabase

class SleepTrackerFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker,container,false)

        val application = requireNotNull(this.activity).application

        //create an instance of the ViewModel Factory
        val dataSource = SleepDatabase.getInstance(application).sleepDatabase

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)

        //Get a reference to the ViewModel associated with the fragment
        val sleepTrackerViewModel = ViewModelProvider(this,viewModelFactory)
                                        .get(SleepTrackerViewModel::class.java)



        return binding.root
    }
}