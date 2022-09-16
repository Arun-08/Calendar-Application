package com.arun.calendar.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object{

        fun getDateFromMillis(milliSeconds : Long) : String{
            val formatter = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            calendar.time = Date(milliSeconds)
            return formatter.format(calendar.time)
        }

        fun getTimeFromMillis(milliSeconds: Long, formatStr : String) : String{
            val date = Date(milliSeconds)
            val formatter = SimpleDateFormat(formatStr,Locale.ENGLISH)
            return formatter.format(date)
        }

        fun getParticularDuration(eventStartTime : Long, eventEndTime: Long) :String{
            val millis = eventEndTime-eventStartTime
            val hours = (millis / (1000 * 60 * 60)).toInt()
            val mins = (millis / (1000 * 60) % 60).toInt()
            return "$hours:$mins"
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

        fun formatString(hourOfDay: Int, minute: Int) : String{
            return when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }
        }

    }

}