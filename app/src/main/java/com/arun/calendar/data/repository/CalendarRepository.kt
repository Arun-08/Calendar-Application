package com.arun.calendar.data.repository

import android.content.Context
import android.util.Log
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
        Log.e("CALENDAR","getAllRecords() eventsList --> ${eventsList.size}")
        val eventsIdList = LongArray(eventsList.size)
        val hashMap = HashMap<Long,ArrayList<CalendarItemModel>>()
        for (j in eventsList.indices){
            Log.e("CALENDAR","getAllRecords() eventsList.indices --> ${eventsList[j].dateTimeStamp} / ${eventsList[j].eventName}")
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
        Log.e("CALENDAR","getAllRecords() eventsIdList --> ${eventsIdList.size}")
        for (i in eventsIdList.indices){
            Log.e("CALENDAR","getAllRecords() eventsIdList[i] --> ${eventsIdList[i]}")
            if(eventsIdList[i]>0){
                val calendarItemList = hashMap[eventsIdList[i]]
                calendarList.add(CalendarModel(eventsIdList[i],calendarItemList!!))
            }
        }
        Log.e("CALENDAR","getAllRecords() calendarList --> ${calendarList.size}")
        return calendarList
    }

    fun getRecordFromDate(context: Context, timeMillis : Long): List<EventsModel> {
        val noRecord : Boolean = true
        val eventsList = dataSource.getRecordFromDate(context,timeMillis)

        return dataSource.getRecordFromDate(context,timeMillis)
    }

    fun canInsertRecord(context: Context, eventDate : Long,sTime : Long, eTime : Long) : Boolean{
        var isTimingValid = true
        val eventsList = dataSource.getRecordFromDate(context,eventDate)

//        var minStartTime : Long = -1
//        var maxEndTime : Long = -1
//        if(eventsList.isNotEmpty()){
//            for (i in eventsList.indices){
//                val eventModel = eventsList[i]
//                minStartTime = if(minStartTime >-1){
//                    Math.min(minStartTime,eventModel.eStartTime)
//                }else{
//                    eventModel.eStartTime
//                }
//                Log.e("CALENDAR--","Stime --> ${eventModel.eStartTime}")
//                Log.e("CALENDAR--","minStartTime --> $minStartTime")
//                Log.e("CALENDAR--","**************************\n")
//                maxEndTime = if(maxEndTime >-1){
//                    Math.max(maxEndTime,eventModel.eEndTime)
//                }else{
//                    eventModel.eEndTime
//                }
//                Log.e("CALENDAR--","Endtime --> ${eventModel.eEndTime}")
//                Log.e("CALENDAR--","maxEndTime --> $maxEndTime")
//                Log.e("CALENDAR--","**************************\n")
//                Log.e("CALENDAR--","/////////////////////////// \n\n")
//                Log.e("CALENDAR--","minStartTime****** --> $minStartTime")
//                Log.e("CALENDAR--","maxEndTime *******--> $maxEndTime")
//                Log.e("CALENDAR_","VALID EX STIME -->${
//                    CommonUtils.getTimeFromMillis(
//                        minStartTime,
//                        "hh:mm a"
//                    )
//                }")
//                Log.e("CALENDAR_","VALID EX STIME -->${
//                    CommonUtils.getTimeFromMillis(
//                        maxEndTime,
//                        "hh:mm a"
//                    )
//                }")
//                if(!CommonUtils.isEventTimeValid(minStartTime,maxEndTime,sTime,eTime)){
//                    isTimingValid = false
//                }
//            }
//        }
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

    fun isRecordAvailable(context: Context, eventDate : Long) : Boolean{
        val eventList = dataSource.getRecordFromDate(context,eventDate)
        return (eventList.isNotEmpty())
    }

}