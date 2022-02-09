package com.harets.smartalarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.core.app.NotificationCompat
import java.util.*

class AlarmService : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message")
        intent?.getIntExtra("type", 0)

        if (context != null && message != null) {
                showNotificationAlarm(context, "Ini Alarm", message, 101)
            }
        }


    fun setOneTimeAlarm(context: Context, type: Int, date: String, time:String, message: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmService::class.java)
        intent.putExtra("message", message)
        intent.putExtra("type", type) // 2, 2, 2022



        // data dri date -> 2-2-2033
        // seteleh di split -> 2 2 2022
        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        val calender = Calendar.getInstance()
        //date
        calender.set(Calendar.YEAR, Integer.parseInt(dateArray[2]))
        calender.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1)
        calender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]))
        //time
        calender.set(Calendar.HOUR, Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)
        Toast.makeText(context, "Success set OnTimeAlarm", Toast.LENGTH_SHORT).show()
    }


    private fun showNotificationAlarm(
        context: Context,
        title: String,
        message: String,
        notificationId: Int
    ) {
        val channelName = "SmartAlarm"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        val builder = NotificationCompat.Builder(context, "alarm_1")
            .setSmallIcon(R.drawable.ic_one_time)
            .setContentTitle(title)
            .setContentText(message)
            .setSound(ringtone)

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel("alarm_1", channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId("alarm_1")
            notificationManager.createNotificationChannel(channel)
        }
        val notif = builder.build()
        notificationManager.notify(notificationId, notif)
    }
}