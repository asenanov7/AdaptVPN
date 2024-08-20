package com.example.adaptvpn

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.net.VpnService
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileOutputStream
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class AdaptVpnService : Service() {

    private val actionMonitoring = ActionMonitoring(this)


    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, NotificationManager(this).createNotification())
        Log.d("ARSEN", "startVPN: ")
        actionMonitoring.startLoggingAppLaunches()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("ARSEN", "destroyVPN: ")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}