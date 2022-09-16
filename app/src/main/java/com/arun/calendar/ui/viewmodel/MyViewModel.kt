package com.arun.calendar.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.data.model.CalendarModel
import com.arun.calendar.data.repository.CalendarRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyViewModel(private val calendarRepo : CalendarRepository) : ViewModel(), CoroutineScope {

    fun getAllEventsRecord(context: Context) : LiveData<List<CalendarModel>> {
        val dataLiveData : MutableLiveData<List<CalendarModel>> = MutableLiveData()
        dataLiveData.postValue(calendarRepo.getAllRecords(context))
        return dataLiveData
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

    fun isRecordAvailable(context: Context, eventDate : Long) : Boolean{
        return calendarRepo.isRecordAvailable(context,eventDate)
    }

    fun getRecordFromEvent(context: Context,eventsModel: EventsModel) : EventsModel{
        return calendarRepo.getRecordFromEvent(context,eventsModel)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

}