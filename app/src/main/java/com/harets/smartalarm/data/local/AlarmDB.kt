package com.harets.smartalarm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harets.smartalarm.data.Alarm
import java.util.concurrent.locks.Lock

@Database(entities = [Alarm::class], version = 2)
abstract class AlarmDB : RoomDatabase(){
    abstract fun alarmDao() : AlarmDao

    companion object{
        @Volatile
        var instance: AlarmDB? = null
         val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(AlarmDB::class.java){
            instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AlarmDB::class.java, "smart_alarm.db").build()
    }
}