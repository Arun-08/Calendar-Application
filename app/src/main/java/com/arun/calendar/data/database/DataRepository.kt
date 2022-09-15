package com.arun.calendar.data.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.arun.calendar.data.database.table.EventsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataRepository {

    fun getAllEventsRecord(context: Context): List<EventsModel> {
        val appDatabase = AppDatabase.getInstance(context)
        return appDatabase.eventsDao().getAllRecords()
    }
    fun updateRecords(context: Context,eventsModel: EventsModel){
        val appDatabase = AppDatabase.getInstance(context)
        appDatabase.eventsDao().updateEvents(eventsModel.eventName,eventsModel.eStartTime,eventsModel.eEndTime,eventsModel.dateTimeStamp)
    }

    fun insertRecords(context: Context,eventsModel: EventsModel){
        val appDatabase = AppDatabase.getInstance(context)
        appDatabase.eventsDao().addEvents(eventsModel)
    }

    fun getRecordFromDate(context: Context, timeMillis : Long) : List<EventsModel>{
        val appDatabase = AppDatabase.getInstance(context)
        return appDatabase.eventsDao().getRecordFromDate(timeMillis)
    }

    fun getRecordFromEvents(context: Context, eventsModel: EventsModel) : EventsModel{
        val appDatabase = AppDatabase.getInstance(context)
        return appDatabase.eventsDao().getRecordFromEvents(eventsModel.eStartTime,eventsModel.eEndTime,eventsModel.dateTimeStamp)
    }
}