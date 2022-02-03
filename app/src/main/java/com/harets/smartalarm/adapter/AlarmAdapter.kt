package com.harets.smartalarm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.harets.smartalarm.data.Alarm
import com.harets.smartalarm.databinding.ItemRowReminderAlarmBinding

class AlarmAdapter() : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {

     var listAlarm: ArrayList<Alarm> = arrayListOf()

    class MyViewHolder(val binding: ItemRowReminderAlarmBinding) : RecyclerView.ViewHolder(binding.root) //untuk inisalisasi layout yg mau dipakai

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemRowReminderAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alarm = listAlarm[position]
        holder.binding.apply{
            itemDateAlarm.text = alarm.date
            itemTimeAlarm.text = alarm.time
            itemNoteAlarm.text = alarm.message
        }
    }

    override fun getItemCount() = listAlarm.size

    //Todo 2 -> Perbarui Kode
    fun setData(list : List<Alarm>){
        val alarmDiffUtil = AlarmDiffUtil(listAlarm, list)
        val alarmDiffUtilResult = DiffUtil.calculateDiff(alarmDiffUtil)
        this.listAlarm = list as ArrayList<Alarm>
        alarmDiffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}