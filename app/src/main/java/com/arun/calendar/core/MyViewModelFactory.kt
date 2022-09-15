package com.arun.calendar.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arun.calendar.ui.viewmodel.MyViewModel

class MyViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel() as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}