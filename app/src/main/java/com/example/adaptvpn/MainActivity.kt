package com.example.adaptvpn

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))

        startLoggingAppLaunches()

    }

    fun startLoggingAppLaunches() {
        // Запускаем корутину, которая будет выполняться каждые 5 секунд
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Log.d("ARSEN", "try new check")
                logAppLaunches()
                delay(5000) // Ждём 5 секунд
            }
        }
    }

    // Функция для логирования запусков приложений за последние 5 секунд
    private fun logAppLaunches() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 5000 // последние 5 секунд

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)

        val event = UsageEvents.Event()
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                Log.d("ARSEN", "App launched: ${event.packageName} at ${Date(event.timeStamp)}")
            }
        }
    }

    companion object{
        const val INSTAGRAM = "com.instagram.android"
        const val YOUTUBE = "com.google.android.youtube"
    }

}
