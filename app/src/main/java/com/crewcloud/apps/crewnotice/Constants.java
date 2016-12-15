package com.crewcloud.apps.crewnotice;

public class Constants {
    public static final String GOOGLE_SENDER_ID = "215769320309";
    public static final String ACTION_RECEIVER_NOTIFICATION = "receiver_notification";
    public static final String GCM_DATA_NOTIFICATOON = "gcm_data_notificaiton";
    /*-------------------------URL--------------------------------*/
    public final static String ROOT_URL_ANDROID = "http://www.crewcloud.net/Android";
    public final static String VERSION = "/Version/";
    public final static String PACKGE = "/Package/";
    public final static String MAIL_NO = "MAIL_NO";
    public final static String BOX_NO = "BOX_NO";
    public final static String TITLE = "TITLE";
    public final static String APP_NAME ="CrewNotice";
    /*--------------------------API------------------------------*/
    public final static String URL_INSERT_ANDROID_DEVICE = "/UI/MobileNotice/NoticeService.asmx/InsertAndroidDevice";
    public final static String URL_DELETE_ANDROID_DEVICE = "/UI/MobileNotice/NoticeService.asmx/DeleteAndroidDevice";
    public final static String URL_UPDATE_ANDROID_DEVICE = "/UI/MobileNotice/NoticeService.asmx/UpdateAndroidDevice_NotificationOptions";
    public final static String URL_UPDATE_TIMEZONE_ANDROID_DEVICE = "/UI/MobileNotice/NoticeService.asmx/UpdateAndroidDevice_TimezoneOffset";

    public static final int ACTIVITY_HANDLER_NEXT_ACTIVITY = 1111;
    public static final int ACTIVITY_HANDLER_START_UPDATE = 1112;
    /*------------------------Service-----------------------------*/
    public static final String LOG_TAG = ">>>>>> CrewNotice";

    /**
     * KEY PREFERENCES
     */
    public static final String KEY_PREFERENCES_PIN = "KEY_PREFERENCES_PIN";
    public static final String KEY_PREFERENCES_ADJUST_TO_SCREEN_WIDTH = "KEY_PREFERENCES_ADJUST_TO_SCREEN_WIDTH";
    public static final String KEY_PREFERENCES_NOTIFICATION_NEW_MAIL = "KEY_PREFERENCES_NOTIFICATION_NEW_MAIL";
    public static final String KEY_PREFERENCES_NOTIFICATION_SOUND = "KEY_PREFERENCES_NOTIFICATION_SOUND";
    public static final String KEY_PREFERENCES_NOTIFICATION_VIBRATE = "KEY_PREFERENCES_NOTIFICATION_VIBRATE";
    public static final String KEY_PREFERENCES_NOTIFICATION_TIME = "KEY_PREFERENCES_NOTIFICATION_TIME";
    public static final String KEY_PREFERENCES_NOTIFICATION_TIME_TO_TIME = "KEY_PREFERENCES_NOTIFICATION_TIME_TO_TIME";
    public static final String KEY_PREFERENCES_NOTIFICATION_TIME_FROM_TIME = "KEY_PREFERENCES_NOTIFICATION_TIME_FROM_TIME";

}
