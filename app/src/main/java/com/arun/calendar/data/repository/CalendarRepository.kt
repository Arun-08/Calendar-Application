package com.arun.calendar.data.repository

import android.content.Context
import com.arun.calendar.data.database.AppDatabase
import com.arun.calendar.data.database.DataRepository
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.data.model.CalendarItemModel
import com.arun.calendar.data.model.CalendarModel
import com.arun.calendar.utils.CommonUtils

class CalendarRepository(private val dataSource: DataRepository) {

    companion object{
        var memoRepoInstance : CalendarRepository? = null
        fun getInstance(dataSource: DataRepository) : CalendarRepository = memoRepoInstance ?: synchronized(this){
            memoRepoInstance?: CalendarRepository(dataSource)
        }
    }

    fun getAllRecords(context: Context) : ArrayList<CalendarModel>{
        val eventsList = dataSource.getAllEventsRecord(context)
        val eventsIdList = LongArray(eventsList.size)
        val hashMap = HashMap<Long,ArrayList<CalendarItemModel>>()
        for (j in eventsList.indices){
            if(eventsIdList.contains(eventsList[j].dateTimeStamp)){
//                eventsIdList[j] = eventsList[j].dateTimeStamp
                val calendarItemList = hashMap[eventsList[j].dateTimeStamp]
                val calendarItem = CalendarItemModel(eventsList[j].eventName,eventsList[j].eStartTime,eventsList[j].eEndTime)
                calendarItemList?.add(calendarItem)
                hashMap[eventsList[j].dateTimeStamp] = calendarItemList!!
            }else{
                eventsIdList[j] = eventsList[j].dateTimeStamp
                val calendarItem = CalendarItemModel(eventsList[j].eventName,eventsList[j].eStartTime,eventsList[j].eEndTime)
                hashMap[eventsList[j].dateTimeStamp] = arrayListOf(calendarItem)
            }
        }
        val calendarList : ArrayList<CalendarModel> = arrayListOf()
        for (i in eventsIdList.indices){
            if(eventsIdList[i]>0){
                val calendarItemList = hashMap[eventsIdList[i]]
                calendarList.add(CalendarModel(eventsIdList[i],calendarItemList!!))
            }
        }
        return calendarList
    }

    fun getRecordFromDate(context: Context, timeMillis : Long): List<EventsModel> {
        val noRecord : Boolean = true
        val eventsList = dataSource.getRecordFromDate(context,timeMillis)

        return dataSource.getRecordFromDate(context,timeMillis)
    }

    fun canInsertRecord(context: Context, eventDate : Long,sTime : Long, eTime : Long) : Boolean{
        var isTimingValid : Boolean = true
        val eventsList = dataSource.getRecordFromDate(context,eventDate)
        for (i in eventsList.indices){
            val eventModel = eventsList[i]
            if(!CommonUtils.isEventTimeValid(eventModel.eStartTime,eventModel.eEndTime,sTime,eTime)){
                isTimingValid = false
                break
            }
        }
        return isTimingValid
    }

    fun getRecordFromEvent(context: Context, eventsModel: EventsModel): EventsModel {
        return dataSource.getRecordFromEvents(context,eventsModel)
    }

    fun insertEvents(context: Context, eventsModel: EventsModel){
        dataSource.insertRecords(context,eventsModel)
    }

    fun updateEventsModel(context: Context,eventsModel: EventsModel){
        dataSource.updateRecords(context,eventsModel)
    }

}