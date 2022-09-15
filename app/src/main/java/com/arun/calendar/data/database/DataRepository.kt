package com.arun.calendar.data.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.arun.calendar.data.database.table.EventsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataRepository {

    fun getAllEventsRecord(context: Context): MutableLiveData<List<EventsModel>> {
        val liveData : MutableLiveData<List<EventsModel>> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val appDatabase = AppDatabase.getInstance(context)
            liveData.postValue(appDatabase.eventsDao().getAllRecords())
        }
        return liveData
    }
    fun updateRecords(context: Context,eventsModel: EventsModel){
        val appDatabase = AppDatabase.getInstance(context)
        appDatabase.eventsDao().updateEvents(eventsModel.eventName,eventsModel.eStartTime,eventsModel.eEndTime,eventsModel.dateTimeStamp)
    }

    fun insertRecords(context: Context,eventsModel: EventsModel){
        val appDatabase = AppDatabase.getInstance(context)
        appDatabase.eventsDao().addEvents(eventsModel)
    }


}