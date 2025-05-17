package com.example.nummo

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.nummo.R

fun createNummoNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "nummo_channel",
            "Nummo Alerts",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for payment requests"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

fun showPaymentRequestNotification(context: Context, amount: String, transactionId: String) {
    // ✅ Android 13+ requires permission check
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return // Don't send notification if permission is denied
    }

    val notification = NotificationCompat.Builder(context, "nummo_channel")
        .setSmallIcon(R.drawable.logo1) // Replace with your app icon
        .setContentTitle("Payment Request Sent")
        .setContentText("You requested $amount from ID: $transactionId")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    NotificationManagerCompat.from(context)
        .notify(System.currentTimeMillis().toInt(), notification)
}