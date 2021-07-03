package com.example.mediraj.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.mediraj.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String TYPE = "type";
    public static final String ACTION_NOTIFICATION_BROADCAST = "action.orderstatus.changed";
    public static final String TAG = "QKMessagingService";
    public static final String DATA = "data";
    String otp_code = "";
    Intent homeIntent;
    TaskStackBuilder taskStackBuilder;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        Log.e(TAG, "Data Payload23: " + data);
        for (String entry : data.keySet()) {
            String value = data.get(entry);
            Log.e("From: ", entry + "  " + value + " ");
        }

        if (data.size() > 0) {
            sendNotification(data);
        }
        if (remoteMessage.getNotification() != null) {
            Log.e("FCM", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }


    private void sendNotification(Map<String, String> data) {
        String notification_type = "", body = "", title = "";
        Bitmap bitmap = null;
        JSONObject object = null;
        notification_type = data.get("notification_type");
        try {
            String notification = data.get("notification");
            object = new JSONObject(notification);
            body = object.getString("body");
            title = object.getString("title");
            otp_code = object.getString("otp_code");
        } catch (Exception e) {
            e.printStackTrace();
        }


//    if (notification_type.equals("OTP Verification")){
//            try {
//                homeIntent = new Intent(this, ForgetPassActivity.class).putExtra("otp_code",otp_code);
//                taskStackBuilder = TaskStackBuilder.create(this);
//                taskStackBuilder.addParentStack(ForgetPassActivity.class);
//                taskStackBuilder.addNextIntent(homeIntent);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


        // PendingIntent notificationIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        final int not_nu = generateRandom();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        String id = getString(R.string.app_name);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setColor(getResources().getColor(R.color.medi_color))
                        .setSmallIcon(R.mipmap.mediraj_logo)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        // .setContentIntent(notificationIntent);


        NotificationChannel mChannel;

        final long[] VIBRATE_PATTERN = {0, 500};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, "MediRaj", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.CYAN);
            mChannel.enableLights(true);
            mChannel.setDescription(title);
            mChannel.setVibrationPattern(VIBRATE_PATTERN);
            mChannel.enableVibration(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }

        notificationManager.notify(not_nu, notificationBuilder.build());


    }


    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
