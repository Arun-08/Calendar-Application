package com.arun.calendar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arun.calendar.data.database.dao.EventsDao
import com.arun.calendar.data.database.table.EventsModel


@Database(entities = [EventsModel::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventsDao() : EventsDao

    companion object{

        private var INSTANCE : AppDatabase? = null

        fun getInstance(mContext: Context) : AppDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(mContext,AppDatabase::class.java,DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }


    }


}