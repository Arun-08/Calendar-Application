package com.arun.calendar.ui.view.adapters

import android.databinding.DataBindingUtil
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arun.calendar.data.database.table.EventsModel
import com.arun.calendar.databinding.CalendarAdapterLayoutBinding
import com.arun.calendar.utils.CommonUtils

class CalendarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class CalendarViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val itemBinding : CalendarAdapterLayoutBinding = DataBindingUtil.bind(view)!!

        fun setDetails(eventsModel: EventsModel){
            itemBinding.tvDate.text = CommonUtils.getDateFromMillis(eventsModel.dateTimeStamp)
        }

    }
}