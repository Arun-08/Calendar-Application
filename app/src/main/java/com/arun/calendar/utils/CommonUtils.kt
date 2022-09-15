package com.arun.calendar.utils

import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object{

        fun getDateFromMillis(milliSeconds : Long) : String{
            val formatter = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }

        fun getTimeFromMillis(milliSeconds: Long, formatStr : String) : String{
            val date = Date(milliSeconds)
            val formatter = SimpleDateFormat(formatStr,Locale.ENGLISH)
            return formatter.format(date)
        }

        fun isEventTimeValid(existingStartTime : Long,existingEndTime: Long, eventStartTime : Long, eventEndTime: Long) : Boolean{
            var eventStartTimeValid = false
            var eventEndTimeValid = false

            val existStartCalendar = Calendar.getInstance().apply {
                time = Date(existingStartTime)
                add(Calendar.DATE,1)
            }
            val existEndCalendar = Calendar.getInstance().apply {
                time = Date(existingEndTime)
                add(Calendar.DATE,1)
            }
            val eventStartCalendar = Calendar.getInstance().apply {
                time = Date(eventStartTime)
                add(Calendar.DATE,1)
            }
            val eventEndCalendar = Calendar.getInstance().apply {
                time = Date(eventEndTime)
                add(Calendar.DATE,1)
            }

            val eventStartDate = eventStartCalendar.time
            if(eventStartDate.before(existStartCalendar.time) || eventStartDate.after(existEndCalendar.time)){
                eventStartTimeValid = true
            }
            val eventEndDate = eventEndCalendar.time
            if(eventEndDate.before(existStartCalendar.time)){
                eventEndTimeValid = true
            }
            return (eventStartTimeValid && eventEndTimeValid)
        }

    }

}