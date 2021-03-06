package ch.ost.mge.mini.lanchat.services

import ch.ost.mge.mini.lanchat.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationSender(context: Context) {
    private val channelId = context.getString(R.string.CID)
    private val channelName = context.getString(R.string.CNAME)
    private val channelDescription = context.getString(R.string.CDESC)
    private val channelImportance = NotificationManager.IMPORTANCE_HIGH
    private val manager = NotificationManagerCompat.from(context)
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    private var notificationId = 1


    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, channelImportance).apply {
                description = channelDescription
            }
            manager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context, title: String, text: String) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.btn_star_big_on)
            .setContentTitle(title)
            .setContentText(text)
            .build()

        manager.notify(notificationId++, notification)

        if (Build.VERSION.SDK_INT < 29) {
            vibrator.vibrate(500)
        } else {
            val effectId = VibrationEffect.EFFECT_HEAVY_CLICK
            val effect = VibrationEffect.createPredefined(effectId)
            vibrator.vibrate(effect)
        }
    }
}