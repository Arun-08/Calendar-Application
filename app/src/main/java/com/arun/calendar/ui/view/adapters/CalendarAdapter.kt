package com.arun.calendar.ui.view.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arun.calendar.R
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.data.model.CalendarItemModel
import com.arun.calendar.data.model.CalendarModel
import com.arun.calendar.databinding.CalendarAdapterLayoutBinding
import com.arun.calendar.utils.CommonUtils

class CalendarAdapter(private val mContext : Context,private val calendarList : List<CalendarModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.calendar_adapter_layout,parent,false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CalendarViewHolder){
            holder.setDetails(calendarList[position])
        }
    }

    override fun getItemCount(): Int = calendarList.size

    inner class CalendarViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val itemBinding : CalendarAdapterLayoutBinding = DataBindingUtil.bind(view)!!

        fun setDetails(calendarModel: CalendarModel){
            itemBinding.tvDate.text = CommonUtils.getDateFromMillis(calendarModel.eventDate)
            val arrayAdapter = ArrayAdapter<String>(mContext, R.layout.textview_layout,getFormattedStringArray(calendarModel.calendarItemList))
            itemBinding.listView.adapter = arrayAdapter
        }
        private fun getFormattedStringArray(itemList : List<CalendarItemModel>) : ArrayList<String>{
            val eventList = ArrayList<String>()
            for (i in itemList.indices){
                val sTimeText = CommonUtils.getTimeFromMillis(itemList[i].eStartTime,"h:mm a")
                val eTimeText = CommonUtils.getTimeFromMillis(itemList[i].eEndTime,"h:mm a")
                val intervalTime = CommonUtils.getParticularDuration(itemList[i].eStartTime,itemList[i].eEndTime)
                val string = "${itemList[i].eventName}: $sTimeText-$eTimeText ($intervalTime)"
                eventList.add(string)
            }
            return eventList
        }
    }
}