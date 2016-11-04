package com.eatfirst.notificationtutorial

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Send notifications from https://console.firebase.google.com
 */
class NotificationsListenerService: FirebaseMessagingService() {

    companion object {
        val TAG = NotificationsListenerService::class.java.canonicalName
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: " + remoteMessage?.from)
        Log.d(TAG, "Notification Message Body: " + remoteMessage?.notification?.body)
        val notificationBuilder = NotificationBuilder.newInstance(this)
        notificationBuilder.sendBundleNotification(remoteMessage!!)
    }

}