package com.crewcloud.apps.crewnotice.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Util {
    private static Toast toast;


    public static void showImage(String url, ImageView view) {
        if (url.contains("content") || url.contains("storage")) {
            File f = new File(url);
            if (f.exists()) {
                Picasso.with(view.getContext()).load(f).into(view);
            } else {
                Picasso.with(view.getContext()).load(url).into(view);
            }
        } else {
            Picasso.with(view.getContext()).load(CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentServiceDomain() + url).into(view);
        }
    }

    public static int getTimezoneOffsetInMinutes() {
        TimeZone tz = TimeZone.getDefault();
        int offsetMinutes = tz.getRawOffset() / 60000;
        return offsetMinutes;
    }

    public static void setTypeFacee(Context context, TextView textView) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Regular.ttf");
        textView.setTypeface(tf);
    }

    public static void setTypeFaceMedium(Context context, TextView textView) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Medium.ttf");
        textView.setTypeface(tf);
    }

    public static void showShortMessage(int text) {
        showMessage(getString(text));
    }

    public static void showShortMessage(String text) {
        if (toast == null) {
            toast = Toast.makeText(CrewCloudApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static String getFormatNumber(Double x) {
        DecimalFormat df = new DecimalFormat("#.######");
        String temp = df.format(x);
        Double abc = Double.parseDouble(temp);
        return String.valueOf(abc);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) CrewCloudApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public static boolean isWifiEnable() {
        WifiManager wifi = (WifiManager) CrewCloudApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) CrewCloudApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi.isWifiEnabled() && mWifi.isConnected();
    }

    public static void printLogs(String logs) {
        if (Statics.ENABLE_DEBUG) {
            if (logs == null)
                return;
            int maxLogSize = 1000;
            if (logs.length() > maxLogSize) {
                for (int i = 0; i <= logs.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > logs.length() ? logs.length() : end;
                    Log.d(Statics.TAG, logs.substring(start, end));
                }
            } else {
                Log.d(Statics.TAG, logs);
            }
        }
    }

    public static void displaySimpleAlert(Context context, int icon, String title, String message, String positiveTitle, String negativeTitle, DialogInterface.OnClickListener onPositiveClick, DialogInterface.OnClickListener onNegativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (icon != -1) {
            builder.setIcon(icon);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveTitle)) {
            builder.setPositiveButton(positiveTitle, onPositiveClick);
        }
        if (!TextUtils.isEmpty(negativeTitle)) {
            builder.setNegativeButton(negativeTitle, onNegativeClick);
        }
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public static Dialog displaySimpleAlertNotCancelable(Context context, int icon, String title, String message, String positiveTitle, String negativeTitle, DialogInterface.OnClickListener onPositiveClick, DialogInterface.OnClickListener onNegativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (icon != -1) {
            builder.setIcon(icon);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveTitle)) {
            builder.setPositiveButton(positiveTitle, onPositiveClick);
        }
        if (!TextUtils.isEmpty(negativeTitle)) {
            builder.setNegativeButton(negativeTitle, onNegativeClick);
        }
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    private static AlertDialog dialog;

    public static Dialog displaySimpleAlertNotCancelableAvoidDouble(Context context, int icon, String title, String message, String positiveTitle, String negativeTitle, DialogInterface.OnClickListener onPositiveClick, DialogInterface.OnClickListener onNegativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (icon != -1) {
            builder.setIcon(icon);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveTitle)) {
            builder.setPositiveButton(positiveTitle, onPositiveClick);
        }
        if (!TextUtils.isEmpty(negativeTitle)) {
            builder.setNegativeButton(negativeTitle, onNegativeClick);
        }
        if (dialog != null && dialog.isShowing()) {
            return null;
        } else {
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
        return dialog;
    }

    public static void showMessage(String message) {
        Toast.makeText(CrewCloudApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void showMessage(int string_id) {
        Toast.makeText(CrewCloudApplication.getInstance().getApplicationContext(), getString(string_id), Toast.LENGTH_LONG).show();
    }

    public static String getString(int stringID) {
        return CrewCloudApplication.getInstance().getApplicationContext().getResources().getString(stringID);
    }

    public static Resources getResources() {
        return CrewCloudApplication.getInstance().getApplicationContext().getResources();
    }

    public static void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkStringValue(String... params) {
        for (String param : params) {
            if (param != null) {
                if (TextUtils.isEmpty(param.trim())) {
                    return false;
                }

                if (param.contains("\n") && TextUtils.isEmpty(param.replace("\n", ""))) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameLayout, fragment);

        if (isSaveStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameLayout, fragment);

        if (isSaveStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public static String setDateInfo(long milis, String format) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());

        if (milis >= 0) {
            //cal.setTimeZone();
            // return timeFormat.format(new Date(milis + CreCloudApplication.getInstance().getmPrefs().gettimeoffset()));
        }

        return "N/A";
    }

    public static String setDateInfoList(long milis, String format) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());

        if (milis >= 0) {
            return timeFormat.format(new Date(milis));
        }

        return "N/A";
    }

    public static String setDateInfoListNoTimeZone(long milis, String format) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (milis >= 0) {
            return timeFormat.format(new Date(milis));
        }

        return "N/A";
    }

    public static void drawCycleImage(ImageView profilePic, int imId, int size) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(CrewCloudApplication.getInstance().getResources(), imId);
        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, size, size, false);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(CrewCloudApplication.getInstance().getResources(), imageBitmap);
        roundedBitmapDrawable.setCornerRadius(size / 2);
        roundedBitmapDrawable.setAntiAlias(true);
        profilePic.setImageDrawable(roundedBitmapDrawable);
    }

    public static int getDimenInPx(int id) {
        return (int) CrewCloudApplication.getInstance().getApplicationContext().getResources().getDimension(id);
    }

    public static long getTimeOffsetInHour() {
        return TimeUnit.HOURS.convert(getTimeOffsetInMilis(), TimeUnit.MILLISECONDS);
    }

    public static long getTimeOffsetInMinute() {
        return TimeUnit.MINUTES.convert(getTimeOffsetInMilis(), TimeUnit.MILLISECONDS);
    }

    public static long getTimeOffsetInMilis() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();

        return mTimeZone.getRawOffset();
    }

    public static String getTimeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        String timeZone = new SimpleDateFormat("Z").format(calendar.getTime());

        return timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5);
    }

    public static long getDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        int EARTH_RADIUS_KM = 6371;
        double lat1Rad, lat2Rad, deltaLonRad, dist;
        //List<Long> value = new ArrayList<>();

        lat2Rad = Math.toRadians(lat2);
        lat1Rad = Math.toRadians(lat1);
        deltaLonRad = Math.toRadians(lon2 - lon1);
        dist = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad)) * EARTH_RADIUS_KM;
        return Math.round(dist * 1000);
    }

    public static String covertDistance(long distance) {
        String dist = "";
        if (distance < 0) {
            return dist;
        }

        if (distance >= 10000) {
            double temp = distance / 1000.0000;

            if (distance < 100000) {
                dist = new DecimalFormat("###,###.00").format(temp) + " km";
            } else if (distance < 1000000) {
                dist = new DecimalFormat("###,###.0").format(temp) + " km";
            } else {
                dist = new DecimalFormat("###,###").format(temp) + " km";
            }
        } else {
            dist = new DecimalFormat("###,###").format(distance) + " m";
        }

        return dist;
    }

    public static String getServerSite(String server_site) {
        String[] domains = server_site.split("[.]");
        if (server_site.contains(".bizsw.co.kr") && !server_site.contains("8080")) {
            return server_site.replace(".bizsw.co.kr", ".bizsw.co.kr:8080");
        }

        if (domains.length <= 1 || server_site.contains("crewcloud")) {
            return domains[0] + ".crewcloud.net";
        } else {
            return server_site;
        }
    }

    public static String getFullHour(String tv) {
        String hour = "", minute = "";
        int h = getHour(tv);
        int m = getMinute(tv);
        if (h < 10) hour = "0" + h;
        else hour = "" + h;
        if (m < 10) minute = "0" + m;
        else minute = "" + m;
        String text = hour + ":" + minute;
        return text;
    }

    public static int getHour(String tv) {
        int h = 0;
        String str[] = tv.trim().split(" ");
//        Log.e(TAG, str[1].split(":")[0]);
        h = Integer.parseInt(str[1].split(":")[0]);
        if (str[0].equalsIgnoreCase("PM")) h += 12;
        return h;
    }

    public static int getMinute(String tv) {
        return Integer.parseInt(tv.split(" ")[1].split(":")[1]);
    }

    public static String getTemsServerSite(String server_site) {
        String temp_server_site = server_site;
        if (temp_server_site.contains(".bizsw.co.kr")) {
            temp_server_site = "http://www.bizsw.co.kr:8080";
        } else {
            if (temp_server_site.contains("crewcloud")) {
                temp_server_site = "http://www.crewcloud.net";
            }
        }
        return temp_server_site;
    }

    public static String getPhoneLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static boolean isPhoneLanguageEN() {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("EN");
    }

    public static int getScreenWidth(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}