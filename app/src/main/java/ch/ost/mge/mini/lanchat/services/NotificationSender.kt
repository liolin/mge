package ch.ost.mge.mini.lanchat.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationSender(context: Context) {
    private val CID = "LANChat_Channel"
    private val CNAME = "LANChat Notifications"
    private val CDESC = "A Channel for LANChat"
    private val CIMP = NotificationManager.IMPORTANCE_HIGH
    private var notificationId = 1

    private lateinit var manager: NotificationManagerCompat

    init {
        manager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CID, CNAME, CIMP).apply {
                description = CDESC
            }
            manager.createNotificationChannel(channel)
            Log.d("NotificationSender", manager?.toString())
        }
    }

    fun sendNotification(context: Context, title: String, text: String) {
        val notification = NotificationCompat.Builder(context, CID)
            .setSmallIcon(android.R.drawable.btn_star_big_on)
            .setContentTitle(title)
            .setContentText(text)
            .build()

        manager.notify(notificationId++, notification)
    }
}