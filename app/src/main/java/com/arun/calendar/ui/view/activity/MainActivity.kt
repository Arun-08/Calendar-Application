package com.arun.calendar.ui.view.activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arun.calendar.core.MyApp
import com.arun.calendar.core.MyViewModelFactory
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.databinding.ActivityMainBinding
import com.arun.calendar.databinding.DialogLayoutBinding
import com.arun.calendar.ui.view.adapters.CalendarAdapter
import com.arun.calendar.ui.viewmodel.MyViewModel
import com.arun.calendar.utils.CommonUtils
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MyViewModel
    private lateinit var calendarAdapter : CalendarAdapter
    private var mEventDateMillis : Long = 0
    private var mEventStartTimeMillis : Long = 0
    private var mEventEndTimeMillis : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider((application as MyApp).viewModelStore,MyViewModelFactory()).get(MyViewModel::class.java)
        binding.tvAddEvents.setOnClickListener {
            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val customDialog = CustomDialog()
            customDialog.window!!.setLayout((6 * width)/7,(4 * height)/5)
            customDialog.show()
        }
        fetchCalendarEvents()
    }

    private fun fetchCalendarEvents(){
        Log.e("CALENDAR","fetchCalendarEvents()")
        viewModel.getAllEventsRecord(this).observe(this){
            Log.e("CALENDAR","List size --> ${it.size}")
            calendarAdapter = CalendarAdapter(this,it)
            binding.rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.rvList.adapter = calendarAdapter
            calendarAdapter.notifyDataSetChanged()
        }
    }

    inner class CustomDialog : Dialog(this) {
        private lateinit var dialogBinding : DialogLayoutBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            dialogBinding.tvAddButton.setOnClickListener { addEvents() }
            dialogBinding.tvEventDate.setOnClickListener { openDatePicker() }
            dialogBinding.tvEventSTime.setOnClickListener { openTimePicker(true) }
            dialogBinding.tvEventETime.setOnClickListener { openTimePicker(false) }
        }

        private fun addEvents(){
            if(mEventDateMillis>0 && mEventStartTimeMillis>0 && mEventEndTimeMillis>0 && !TextUtils.isEmpty(dialogBinding.etEventName.text.toString())){
                val eventModel = EventsModel(null,mEventDateMillis,dialogBinding.etEventName.text.toString(),mEventStartTimeMillis,mEventEndTimeMillis)
//                if(viewModel.isRecordAvailable(this@MainActivity,mEventDateMillis)){
//
//                }else{
//                    viewModel.insertEvents(this@MainActivity,eventModel)
//                    makeToast("Event added")
//                    this.dismiss()
//                }
                val isValid = viewModel.isGivenEventTimeValid(this@MainActivity,mEventDateMillis,mEventStartTimeMillis,mEventEndTimeMillis)
                if(isValid){
                    viewModel.insertEvents(this@MainActivity,eventModel)
                    fetchCalendarEvents()
                    makeToast("Event added")
                    this.dismiss()
                }else{
                    makeToast("Event exist on this time")
                }
            }else{
                makeToast("Please check the above fields")
            }
        }

        private fun openDatePicker(){
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this@MainActivity,object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker, yr: Int, mnth: Int, date: Int) {
                    calendar.set(Calendar.YEAR,yr)
                    calendar.set(Calendar.MONTH,mnth)
                    calendar.set(Calendar.DAY_OF_MONTH,date)
                    calendar.set(Calendar.HOUR_OF_DAY,0)
                    calendar.set(Calendar.MINUTE,0)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    mEventDateMillis = calendar.timeInMillis
                    dialogBinding.tvEventDate.setText(
                        StringBuilder().append(yr).append("/")
                            .append(mnth+1).append("/").append(date)
                    )
                }
            },year,month,day)
            dialog.datePicker.minDate = calendar.timeInMillis
            dialog.show()
        }

        private fun openTimePicker(isStartTime : Boolean){
            val dialog = TimePickerDialog(this@MainActivity,object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker, p1: Int, p2: Int) {
                    Log.e("CALENDAR","isStartTime --> $isStartTime")
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY,p1)
                    calendar.set(Calendar.MINUTE,p2)
                    if(isStartTime){
                        mEventStartTimeMillis = calendar.timeInMillis
                        dialogBinding.tvEventSTime.text = CommonUtils.formatString(p1,p2)
                    }else{
                        mEventEndTimeMillis = calendar.timeInMillis
                        dialogBinding.tvEventETime.text = CommonUtils.formatString(p1,p2)
                    }
                }
            },0,0,false)
            dialog.show()
        }



    }

    private fun makeToast(msg : String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


}