package com.crewcloud.apps.crewnotice.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.crewcloud.apps.crewnotice.Constants;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.data.GCMData;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.crewcloud.apps.crewnotice.util.Util;
import com.google.gson.Gson;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    private int Code = 0;

    /**
     * NOTIFICATION
     */
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

    @Override
    protected void onHandleIntent(Intent intent) {
/*
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            //Log.d(Statics.LOG_TAG, extras.toString());
            //Util.printLogs(extras.toString());

            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error",extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server ",
//                        extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                try {
                    Util.printLogs(extras.toString());
                    //Code = Integer.parseInt(extras.getString("Code"));
                    String title = extras.getString("Title");
                    String fromName = extras.getString("FromName");
                    String content = extras.getString("Content");
                    String receivedDate = extras.getString("ReceivedDate");
                    String toAddress = extras.getString("ToAddress");
                    String mailNo = extras.getString("MailNo");
                    String mailBoxNo = extras.getString("MailBoxNo");

                    System.out.println("aaaaaaaaa title " + title);
                    System.out.println("aaaaaaaaa fromName " + fromName);
                    System.out.println("aaaaaaaaa content " + content);
                    System.out.println("aaaaaaaaa receivedDate " + receivedDate);
                    System.out.println("aaaaaaaaa toAddress " + toAddress);
                    System.out.println("aaaaaaaaa mailNo " + mailNo);
                    System.out.println("aaaaaaaaa mailBoxNo " + mailBoxNo);


                    ShowNotification(title, fromName, content, receivedDate, toAddress, Long.parseLong(mailNo), mailBoxNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(Constants.LOG_TAG, "empty");
        }
        */
    }

    private void ShowNotification(String title, String fromName, String content, String receivedDate, String toAddress, long mailNo, String mailBoxNo) {

        long[] vibrate = new long[]{1000, 1000, 0, 0, 0};
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        /** PendingIntent */
//        Intent intent = new Intent(this, ActivityMailDetail.class);
//        intent.putExtra(StaticsBundle.BUNDLE_MAIL_NO, mailNo);
//        intent.putExtra(StaticsBundle.BUNDLE_MAIL_FROM_NOTIFICATION, true);
//        intent.putExtra(StaticsBundle.BUNDLE_MAIL_FROM_NOTIFICATION_MAILBOX_NO, mailBoxNo);
//        intent.putExtra(StaticsBundle.PREFS_KEY_ISREAD, false);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_notice)
                        .setTicker(getString(R.string.the_new_mail_has_arrived))
                        .setPriority(Notification.PRIORITY_MAX)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.loading_ic_notice))
                        .setContentTitle(fromName)
                        .setContentText(title)
                        .setAutoCancel(true);
//                        .setContentIntent(contentIntent);

        /** GET PREFERENCES */
        boolean isVibrate = new PreferenceUtilities().getBooleanValue(Constants.KEY_PREFERENCES_NOTIFICATION_VIBRATE, true);
        boolean isSound = new PreferenceUtilities().getBooleanValue(Constants.KEY_PREFERENCES_NOTIFICATION_SOUND, true);
        boolean isNewMail = new PreferenceUtilities().getBooleanValue(Constants.KEY_PREFERENCES_NOTIFICATION_NEW_MAIL, true);
        boolean isTime = new PreferenceUtilities().getBooleanValue(Constants.KEY_PREFERENCES_NOTIFICATION_TIME, true);
        String strFromTime = new PreferenceUtilities().getStringValue(Constants.KEY_PREFERENCES_NOTIFICATION_TIME_FROM_TIME, Util.getString(R.string.setting_notification_from_time));
        String strToTime = new PreferenceUtilities().getStringValue(Constants.KEY_PREFERENCES_NOTIFICATION_TIME_TO_TIME, Util.getString(R.string.setting_notification_to_time));


        if (isVibrate) {
            mBuilder.setVibrate(vibrate);
        }

        if (isSound) {
            mBuilder.setSound(soundUri);
        }

        NotificationCompat.BigTextStyle bigTextStyle
                = new NotificationCompat.BigTextStyle();

        /** STYLE BIG TEXT */
        String bigText = "<font color='#878787'>" + title + "</font>";
        if (!TextUtils.isEmpty(content.replaceAll("&nbsp;", "").trim())) {
            bigText = bigText + "<br/>" + content;
        }

        bigTextStyle.bigText(Html.fromHtml(bigText));
        // summary is displayed replacing the position of contentText
        // if summary is not set, contentInfo will not be displayed too
        bigTextStyle.setSummaryText(toAddress);
        // Moves the big view style object into the notification object.
        mBuilder.setStyle(bigTextStyle);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //mNotificationManager.cancelAll();
        // mId allows you to update the notification later on.
        int notificationID = 999;
        Notification notification = mBuilder.build();

        //consider using setTicker of Notification.Builder
//        notification.tickerText = "New Message";
        if (isNewMail) {
            if (isTime) {
                if (TimeUtils.isBetweenTime(strFromTime, strToTime)) {
                    mNotificationManager.notify(notificationID, notification);
                    mNotificationManager.notify(notificationID, mBuilder.build());
                }
            } else {
                mNotificationManager.notify(notificationID, notification);
                mNotificationManager.notify(notificationID, mBuilder.build());
            }
        }
    }
}