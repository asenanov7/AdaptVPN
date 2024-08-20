package com.example.adaptvpn

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActionMonitoring(private val context: Context) {

    private val monitoringList = listOf(INSTAGRAM, YOUTUBE)
    private var isVPNRunning = false

    fun startLoggingAppLaunches() {
        // Запускаем корутину, которая будет выполняться каждые 5 секунд
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                logAppLaunches()
                delay(5000) // Ждём 5 секунд
            }
        }
    }

    // Функция для логирования запусков приложений за последние 5 секунд
    private fun logAppLaunches() {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 4000 // последние 4 секунды

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)

        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED && monitoringList.contains(event.packageName)) {
                isVPNRunning = true
                Log.d("ARSEN", "VPN ON")
            } else if (event.eventType == UsageEvents.Event.ACTIVITY_PAUSED && isVPNRunning) {
                isVPNRunning = false
                Log.d("ARSEN", "VPN OFF")
            }

        }
    }

    companion object {

        const val INSTAGRAM = "com.instagram.android"
        const val YOUTUBE = "com.google.android.youtube"

    }
}