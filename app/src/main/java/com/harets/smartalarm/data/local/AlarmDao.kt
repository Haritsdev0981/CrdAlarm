package com.harets.smartalarm.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.harets.smartalarm.data.Alarm

@Dao
interface AlarmDao {

    @Insert
    fun addAlarm(alarm:Alarm)
}
