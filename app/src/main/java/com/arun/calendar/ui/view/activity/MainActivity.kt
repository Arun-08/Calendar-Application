package com.arun.calendar.ui.view.activity

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.arun.calendar.core.MyApp
import com.arun.calendar.core.MyViewModelFactory
import com.arun.calendar.databinding.ActivityMainBinding
import com.arun.calendar.ui.view.adapters.CalendarAdapter
import com.arun.calendar.ui.viewmodel.MyViewModel

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MyViewModel
    private lateinit var calendarAdapter : CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider((application as MyApp).viewModelStore,MyViewModelFactory()).get(MyViewModel::class.java)
        fetchCalendarEvents()
    }

    private fun fetchCalendarEvents(){
        val calendarList = viewModel.getAllEventsRecord(this)
        calendarAdapter = CalendarAdapter(this,calendarList)
        binding.rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rvList.adapter = calendarAdapter
    }

    override fun onClick(view: View) {
        when(view){
            binding.tvAddEvents-> {

            }
        }
    }
    

}