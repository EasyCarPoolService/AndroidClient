package com.example.easycarpoolapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService(){

    private val TAG : String = "CustomFCMService"
    private val CHANNEL_ID : String = "ChannerID"
    private lateinit var notificationManager : NotificationManager

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title.toString()
        val body = remoteMessage.notification?.body.toString()

        // key : value로 이루어진 data
        /*val title = message.data["title"].toString()
        val body = message.data["content"].toString()*/

        Log.e(TAG, title)
        Log.e(TAG, body)
        createNotificationChannel()
        sendNotification(title, body)

    }//onMessageReceive

    private fun createNotificationChannel(){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val CHANNEL_NAME = "ChannerName"
            val CHANNEL_DESCRIPTION = "ChannerDescription"
            val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH


            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE)
            mChannel.description = CHANNEL_DESCRIPTION
            mChannel.enableLights(true)
            mChannel.enableVibration(true)
            mChannel.setShowBadge(true)
            //mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            //mChannel구성

            notificationManager.createNotificationChannel(mChannel)
        }

    }//createNotificationChannel


    private fun sendNotification(title : String, body : String){

        var notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_car_maincolor)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)
            .setContentTitle(title)  //fcm title
            .setContentText(body) //fcm message

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){ //VERSION 8.0 미만

            Log.e("VERSION", "UNDER O")
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setVibrate(longArrayOf(500, 500))
        }

        notificationManager.notify(0, notificationBuilder.build())
    }//sendNotification

}