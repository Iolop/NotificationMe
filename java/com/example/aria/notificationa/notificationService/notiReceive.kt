package com.example.aria.notificationa.notificationService

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import com.example.aria.notificationa.contract.TodoContract
import com.example.aria.notificationa.contract.TodoDBHelper
import java.util.*

class notiReceive : BroadcastReceiver(){
    companion object{
        val NOTI_FUNC: String = "noti_func"
        val NOTI_ID: String = "noti_id"
    }

    override fun onReceive(p0: Context, p1: Intent) {
        var notiBuilder: Notification = p1.getParcelableExtra(NOTI_FUNC)
        var notification: NotificationManager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notiId: Int = p1.getIntExtra(NOTI_ID,0)

        notification.notify(notiId,notiBuilder)


    }
}