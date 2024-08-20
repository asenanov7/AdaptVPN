package com.example.adaptvpn

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActionMonitoring(private val context: Context) {

    private val monitoringList = listOf(INSTAGRAM, YOUTUBE)

    private var isVPNRunning = MutableStateFlow(false)

    fun startLoggingAppLaunches() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                logAppLaunches()
                delay(1000) // Ждём 5 секунд
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            isVPNRunning.collectLatest {
                Log.d("ARSEN", "VPN $it")
            }
        }
    }

    private fun logAppLaunches() {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 2000

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)

        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED && monitoringList.contains(event.packageName) && isVPNRunning.value.not()) {
                isVPNRunning.value = true
            } else if (event.eventType == UsageEvents.Event.ACTIVITY_PAUSED && isVPNRunning.value) {
                isVPNRunning.value = false
            }

        }
    }

    companion object {

        const val INSTAGRAM = "com.instagram.android"
        const val YOUTUBE = "com.google.android.youtube"

    }
}