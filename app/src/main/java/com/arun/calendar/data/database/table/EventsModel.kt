package com.arun.calendar.data.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_records")
data class EventsModel(
    @PrimaryKey @ColumnInfo(name = "id") var id : Long?,
    @ColumnInfo(name = "dateTimeStamp") val dateTimeStamp : Long,
    @ColumnInfo(name = "eName") val eventName : String,
    @ColumnInfo(name = "eStartTime") val eStartTime : Long,
    @ColumnInfo(name = "eEndTime") val eEndTime : Long
)
