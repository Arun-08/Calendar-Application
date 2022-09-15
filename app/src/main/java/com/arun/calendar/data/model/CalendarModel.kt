package com.arun.calendar.data.model

import androidx.room.ColumnInfo

data class CalendarModel(val eventDate : Long,val calendarItemList : ArrayList<CalendarItemModel>)

data class CalendarItemModel(val eventName : String, val eStartTime : Long, val eEndTime : Long)