package com.arun.calendar.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.data.model.CalendarModel
import com.arun.calendar.data.repository.CalendarRepository

class MyViewModel(private val calendarRepo : CalendarRepository) : ViewModel() {

    var data : MutableLiveData<List<CalendarModel>> = MutableLiveData()

    fun getAllEventsRecord(context: Context) : List<CalendarModel> {
//        val dataLiveData : MutableLiveData<List<CalendarModel>> = MutableLiveData()
//        data.postValue(calendarRepo.getAllRecords(context))
        return calendarRepo.getAllRecords(context)
//        return dataLiveData
    }

    fun getRecordFromDate(context: Context,timeMillis : Long) : List<EventsModel>{
        return calendarRepo.getRecordFromDate(context,timeMillis)
    }

    fun isGivenEventTimeValid(context: Context, eventDate : Long,sTime : Long, eTime : Long) : Boolean{
        return calendarRepo.canInsertRecord(context,eventDate,sTime,eTime)
    }

    fun insertEvents(context: Context, eventsModel: EventsModel){
        calendarRepo.insertEvents(context,eventsModel)
    }

    fun updateEventsModel(context: Context,eventsModel: EventsModel){
        calendarRepo.updateEventsModel(context,eventsModel)
    }

    fun getRecordFromEvent(context: Context,eventsModel: EventsModel) : EventsModel{
        return calendarRepo.getRecordFromEvent(context,eventsModel)
    }

}