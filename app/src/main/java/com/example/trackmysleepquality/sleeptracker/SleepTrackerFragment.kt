package com.example.trackmysleepquality.sleeptracker

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
import com.example.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.example.trackmysleepquality.databse.SleepDatabase
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker,container,false)

        //获取对应用上下文的引用
        val application = requireNotNull(this.activity).application

        //获取对数据库的 DAO 的引用
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        //创建 viewModelFactory 的实例
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)

        //获取对 SleepTrackerViewModel 的引用
        val sleepTrackerViewModel = ViewModelProvider(this,viewModelFactory)
                                        .get(SleepTrackerViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.sleepTrackerViewModel = sleepTrackerViewModel

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer {
            night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
                )
                sleepTrackerViewModel.doneSleepQuality()
            }
        })

        sleepTrackerViewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_LONG
                ).show()
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        })

        return binding.root
    }
}