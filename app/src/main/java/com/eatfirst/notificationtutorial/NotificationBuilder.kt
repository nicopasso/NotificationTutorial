package com.eatfirst.notificationtutorial

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage

class NotificationBuilder(val context: Context, val notificationManager: NotificationManagerCompat, val sharedPreferences: SharedPreferences) {

    companion object {
        val GROUP_KEY = "Messenger"
        val NOTIFICATION_ID = "com.eatfirst.notificationtutorial.NOTIFICATION_ID"
        val SUMMARY_ID = 0

        fun newInstance(context: Context) : NotificationBuilder {
            val appContext = context.applicationContext
            var safeContext = ContextCompat.createDeviceProtectedStorageContext(appContext)

            if (safeContext == null) safeContext = appContext

            val notificationManager = NotificationManagerCompat.from(safeContext)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(safeContext)
            return NotificationBuilder(safeContext, notificationManager, sharedPreferences)
        }
    }

    val appContext: Context by lazy { context.applicationContext }

    fun sendBundleNotification(message: RemoteMessage) {
        val notification = buildNotification(message, GROUP_KEY)
        notificationManager.notify(getNotificationId(), notification)
        val summary = buildSummary(message, GROUP_KEY)
        notificationManager.notify(SUMMARY_ID, summary)
    }

    fun buildNotification(message: RemoteMessage, groupKey: String) : Notification {
        return NotificationCompat.Builder(appContext)
                .setContentTitle(message.notification.title)
                .setContentText(message.notification.body)
                .setWhen(message.sentTime)
                .setSmallIcon(R.drawable.ic_cloud_white_48dp)
                .setShowWhen(true)
                .setGroup(groupKey)
                .build()
    }

    fun buildSummary(message: RemoteMessage, groupKey: String) : Notification {
        return NotificationCompat.Builder(appContext)
                .setContentTitle("Nougat Messenger")
                .setContentText("You have unread messages")
                .setWhen(message.sentTime)
                .setSmallIcon(R.drawable.ic_cloud_white_48dp)
                .setShowWhen(true)
                .setGroup(groupKey)
                .setGroupSummary(true)
                .build()
    }

    fun getNotificationId() : Int {
        var id = sharedPreferences.getInt(NOTIFICATION_ID, SUMMARY_ID) + 1
        while (id == SUMMARY_ID) {
            id++
        }

        val editor = sharedPreferences.edit()
        editor.putInt(NOTIFICATION_ID, id)
        editor.apply()
        return id
    }
}
