package com.harets.smartalarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import com.harets.smartalarm.data.Alarm
import java.util.*

class AlarmService : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val type = intent?.getIntExtra(EXTRA_TYPE, 0)
        val title = when (type) {
            TYPE_ONE_TIME -> "One Time Alarm"
            TYPE_REPEATING -> "Repeating Alarm"
            else -> "Something wrong here"
        }

//        val notificationId = if (type == TYPE_ONE_TIME) ID_ONE_TIME else ID_REPEATING

        val requestCode = when(type){
            TYPE_ONE_TIME -> ID_ONE_TIME
            TYPE_REPEATING -> ID_REPEATING
            else -> -1
        }

        if (context != null && message != null) {
            showNotificationAlarm(context, title, message, requestCode)
        }
    }

    fun cancelAlarm(context: Context, type: Int?){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmService::class.java)
        val requestCode = when(type){
            TYPE_ONE_TIME -> ID_ONE_TIME
            TYPE_REPEATING -> ID_REPEATING
            else -> Log.d("CancelAlarm", "Unknow type of Alarm")
        }
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        if (type == TYPE_ONE_TIME){
            Toast.makeText(context, "One time Alarm Cancel", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Repeating Alarm Cancel", Toast.LENGTH_SHORT).show()
        }
    }

    fun setRepeatingAlarm(context: Context, type: Int, time: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmService::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_MESSAGE, type) // 2, 2, 2022


        // data dri date -> 2-2-2033
        val timeArray = time.split(":").toTypedArray()

        val calender = Calendar.getInstance()
        //time
        calender.set(Calendar.HOUR, Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)
        Toast.makeText(context, "Success set RepeatinAlarm", Toast.LENGTH_SHORT).show()
        Log.i("SetNotifAlarm", "setRepeatingAlarm: Alarm will rings on ${calender.time}")
    }


    fun setOneTimeAlarm(context: Context, type: Int, date: String, time: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmService::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type) // 2, 2, 2022


        // data dri date -> 2-2-2033
        // seteleh di split -> 2 2 2022
        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calender = Calendar.getInstance()
        //date
        calender.set(Calendar.YEAR, Integer.parseInt(dateArray[2]))
        calender.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
        calender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]))
        //time
        calender.set(Calendar.HOUR, Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONE_TIME, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)
        Toast.makeText(context, "Success set OnTimeAlarm", Toast.LENGTH_SHORT).show()
        Log.i("SetNotifAlarm", "setOneTimeAlarm: Alarm will rings on ${calender.time}")
    }


    private fun showNotificationAlarm(
        context: Context,
        title: String,
        message: String,
        notificationId: Int
    ) {
        val channelName = "SmartAlarm"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val channelId = "smart_alarm"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_one_time)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(ringtone)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(channel)
        }
        val notif = builder.build()
        notificationManager.notify(notificationId, notif)
    }

    companion object {
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        const val ID_ONE_TIME = 101
        const val ID_REPEATING = 102

        const val TYPE_ONE_TIME = 0
        const val TYPE_REPEATING = 1
    }
}