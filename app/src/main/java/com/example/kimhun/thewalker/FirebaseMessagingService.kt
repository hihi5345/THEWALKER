package com.example.kimhun.thewalker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.RemoteMessage
import java.util.*


/**
 * Created by kimhun on 2017-12-02.
 */
class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {
    private val TAG = "FirebaseMsgService"

    private lateinit var mAuth: FirebaseAuth
    private lateinit var path:String
    private lateinit var DBinfoRef : DatabaseReference

    private lateinit var msg:String
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: " + remoteMessage!!.getFrom())

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body)
        }

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val userId = currentUser!!.email
        var index = userId!!.indexOf("@")
        path = userId.substring(0,index)

        msg = remoteMessage.notification.body!!
        if(msg == "FEVER"){
            DBinfoRef = FirebaseDatabase.getInstance().getReference("/info/" + path)
            DBinfoRef.child("event").setValue(1)
        } else {
            DBinfoRef = FirebaseDatabase.getInstance().getReference("/info/" + path)
            DBinfoRef.child("event").setValue(0)
        }

        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var contentIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java),0)

        val mBuilder = NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM")
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(1, 1000))

        Timer().schedule(object : TimerTask(){
            override fun run() {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.notify(0 /* ID of notification */, mBuilder.build())


                mBuilder.setContentIntent(contentIntent)
            }
        }, 4000)

    }
}