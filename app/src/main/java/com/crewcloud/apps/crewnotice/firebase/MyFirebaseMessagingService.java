package com.crewcloud.apps.crewnotice.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.ui.activity.MainActivityV2;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> mapData = remoteMessage.getData();

        ShowNotification(
                mapData.get("title"),
                mapData.get("writer"));
    }

    private void ShowNotification(String title, String writer) {
        long[] vibrate = new long[] { 1000, 1000, 0, 0, 0 };
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivityV2.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_notice)
                .setPriority(Notification.PRIORITY_MAX)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentText(title)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        builder.setTicker(getString(R.string.notification_message)).setContentTitle(getString(R.string.notification_message));

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(Html.fromHtml("<font color='#878787'>" + title + "</font>" + "<br/>" + writer));
        builder.setStyle(bigTextStyle);

        PreferenceUtilities prefs = CrewCloudApplication.getInstance().getPreferenceUtilities();

        boolean isVibrate = prefs.getNOTIFI_VIBRATE();
        boolean isSound = prefs.getNOTIFI_SOUND();
        boolean isNewMail = prefs.getNOTIFI_MAIL();
        boolean isTime = prefs.getNOTIFI_TIME();
        String strFromTime = prefs.getSTART_TIME();
        String strToTime = prefs.getEND_TIME();

        if (isVibrate) {
            builder.setVibrate(vibrate);
        }

        if (isSound) {
            builder.setSound(soundUri);
        }

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();

        if (isNewMail) {
            if (isTime) {
                if (TimeUtils.isBetweenTime(strFromTime, strToTime)) {
                    mNotificationManager.notify(1985, notification);
                }
            } else {
                mNotificationManager.notify(1985, notification);
            }
        }
    }
}