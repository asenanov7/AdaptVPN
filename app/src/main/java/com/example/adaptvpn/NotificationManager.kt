package com.example.adaptvpn

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat

class NotificationManager(
    private val context: Context,
) {

    fun createNotification(): Notification {
        createChannel()
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title))
            .setSmallIcon(R.drawable.baseline_vpn_key_24)
            .build()
    }

    private fun createChannel() {
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).let { channel ->
            (context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    companion object {

        const val CHANNEL_ID = "ADAPT_VPN_CHANNEL_ID"
        const val CHANNEL_NAME = "ADAPT_VPN_CHANNEL_NAME"

    }

}