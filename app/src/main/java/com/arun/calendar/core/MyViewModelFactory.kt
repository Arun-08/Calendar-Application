package com.arun.calendar.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arun.calendar.data.database.DataRepository
import com.arun.calendar.data.repository.CalendarRepository
import com.arun.calendar.ui.viewmodel.MyViewModel

class MyViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(CalendarRepository.getInstance(DataRepository)) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}