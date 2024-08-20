package com.example.adaptvpn

import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileOutputStream
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class AdaptVpnService : VpnService() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ARSEN", "startVPN: ")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("ARSEN", "destroyVPN: ")
        super.onDestroy()
    }
}