package com.example.aria.notificationa.notificationService

import android.app.*
import android.content.Context
import android.content.Intent
import com.example.aria.notificationa.R
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.graphics.drawable.Icon

class reminderNoti: IntentService("reminderNoti"){
companion object {
    var pendingItentId: Int = 4312
}
    override fun onHandleIntent(p0: Intent) {
        val todoTime: String = p0.getStringExtra("TODO_TIME")
        val todoThing: String = p0.getStringExtra("TODO_THING")
        val mseconds: Long = transTime(todoTime)

        scheduleNotification(mseconds,todoThing)
    }

    fun transTime(futureTime: String): Long{
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int =  c.get(Calendar.MONTH)
        val day: Int =  c.get(Calendar.DAY_OF_MONTH)
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        val timeNow : String = "%d-%02d-%02d %02d:%02d:00".format(year,month+1,day,hour, minute)

        var dataFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val futureDate = dataFormat.parse(futureTime)
        val nowDate = dataFormat.parse(timeNow)
        val diff: Long = futureDate.getTime() - nowDate.getTime()
        return diff
    }

    fun scheduleNotification(mseconds: Long,todoThing: String){
        val notification: Intent = Intent(this@reminderNoti,notiReceive::class.java)
        notification.putExtra(notiReceive.NOTI_ID,1)
        notification.putExtra(notiReceive.NOTI_FUNC,notiBuild(todoThing))

        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this@reminderNoti,pendingItentId,notification,PendingIntent.FLAG_UPDATE_CURRENT)
        pendingItentId = pendingItentId + 1

        var c: Calendar = Calendar.getInstance()
        c.add(Calendar.MINUTE,(mseconds/60000).toInt())
        var alarmManager:  AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.timeInMillis,pendingIntent)
    }

    fun notiBuild(todoThing: String): Notification{
        val notificationID: String = "NOTI_CHANNEL_ID"

        val notificationChannel = NotificationChannel(notificationID, "channelName", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel);

        var builder: Notification = Notification.Builder(this@reminderNoti,notificationID)
                .setColor(0x3F51B5)
                .setContentTitle("There is one thing todo")
                .setContentText(todoThing)
                .setSmallIcon(R.drawable.plus_a)
                .setLargeIcon(Icon.createWithResource(this@reminderNoti,R.drawable.largeicon))
                .build()
        return builder
    }

    fun largeIcon(context: Context): Bitmap {
        // COMPLETED (5) Get a Resources object from the context.
        val res = context.resources
        // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
        // resources object and R.drawable.ic_local_drink_black_24px
        return BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px)
    }
}
