package com.example.trackmysleepquality.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackmysleepquality.R
import com.example.trackmysleepquality.databinding.FragmentSleepQualityBinding
import com.example.trackmysleepquality.databse.SleepDatabase

class SleepQualityFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentSleepQualityBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_sleep_quality,container,false
        )

        val application = requireNotNull(this.activity).application

        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        val sleepNightKey = arguments.sleepNightKey

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(sleepNightKey, dataSource)

        val sleepQualityViewModel = ViewModelProvider(this,viewModelFactory)
                                            .get(SleepQualityViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if(it == true){
                this.findNavController().navigate(
                    SleepQualityFragmentDirections
                        .actionSleepQualityFragmentToSleepTrackerFragment()
                )
                sleepQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}