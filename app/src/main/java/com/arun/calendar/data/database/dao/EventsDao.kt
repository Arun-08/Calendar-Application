package com.arun.calendar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arun.calendar.data.database.table.EventsModel

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEvents(eventsEntity: EventsModel)

    @Query("SELECT * FROM event_records")
    fun getAllRecords() : List<EventsModel>

    @Query("UPDATE event_records SET eName=:eName, eStartTime=:sTime, eEndTime=:eTime WHERE dateTimeStamp = :eventDate")
    fun updateEvents(eName : String, sTime : Long,eTime : Long,eventDate : Long)

}