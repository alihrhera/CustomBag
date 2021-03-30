package com.customs.bag.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.customs.bag.R
import com.customs.bag.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class MFCM : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("FCMMessage", remoteMessage.data.toString())
        if (!DataManger.getInstance().isChatOpen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(ContentValues.TAG, "Message data payload: " + remoteMessage.data)
                showNotification(remoteMessage.data["messg"].toString())
            } else {
                notFor5(remoteMessage.data["messg"].toString())
            }
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        DataManger.getInstance().fcmToken = s
    }


    var notifyID = 1

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun showNotification(message: String) {
        notifyID = (Calendar.getInstance().timeInMillis / 1000).toInt()
        val CHANNEL_ID = "Cooker_Channel_01" // The id of the channel.
        val name: CharSequence =
            getString(R.string.app_name) // The user-visible name of the channel.
        val importance = NotificationManager.IMPORTANCE_HIGH
        val i = Intent(this, MainActivity::class.java)
        val b = Bundle()
        b.putBoolean("NewRequest", true)
        i.putExtras(b)
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            i, 0
        )
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        val notification: Notification =
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("هناك رسائل جديده فى الشات ")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build()
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        mChannel.setSound(
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes
        )
        val mNotificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        mNotificationManager.createNotificationChannel(mChannel)
        mNotificationManager.notify(notifyID++, notification)
    }

    private fun notFor5(msg: String) {
        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(
                this,
                MainActivity::class.java
            ), 0
        )
        val notificationBuilder: Notification.Builder = Notification.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("هناك رسائل جديده فى الشات ")
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
            .setOnlyAlertOnce(true)

        val notificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        notificationManager.notify(notifyID, notificationBuilder.build())
    }

}