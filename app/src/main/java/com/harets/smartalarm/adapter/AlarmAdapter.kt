package com.harets.smartalarm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harets.smartalarm.data.Alarm
import com.harets.smartalarm.databinding.ItemRowReminderAlarmBinding

class AlarmAdapter() : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

     val listAlarm: ArrayList<Alarm> = arrayListOf()

    class MyViewHolder(val binding: ItemRowReminderAlarmBinding) : RecyclerView.ViewHolder(binding.root) //untuk inisalisasi layout yg mau dipakai

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemRowReminderAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alarm = listAlarm[position]
        holder.binding.apply{
            itemDateAlarm.text = alarm.date
            itemTimeAlarm.text = alarm.date
            itemNoteAlarm.text = alarm.date
        }
    }

    override fun getItemCount() = listAlarm.size

    fun setData(list : List<Alarm>){
        listAlarm.clear()
        listAlarm.addAll(list)
        notifyDataSetChanged() 
    }
}