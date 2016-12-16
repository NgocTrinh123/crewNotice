package com.crewcloud.apps.crewnotice.backup;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.BuildConfig;
import com.crewcloud.apps.crewnotice.Constants;
import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.loginv2.BaseActivity;
import com.crewcloud.apps.crewnotice.loginv2.LoginV2Activity;
import com.crewcloud.apps.crewnotice.ui.activity.MainActivityV2;
import com.crewcloud.apps.crewnotice.util.DeviceUtilities;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.crewcloud.apps.crewnotice.util.Util;
import com.crewcloud.apps.crewnotice.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class IntroActivity extends BaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_base_color));
        }
        setContentView(R.layout.activity_intro);


        if (!checkPermissions()) {
            setPermissions();
        } else {
            if (Util.isNetworkAvailable()) {
                Thread thread = new Thread(new UpdateRunnable());
                thread.setDaemon(true);
                thread.start();
            } else {
//            firstChecking();
                Toast.makeText(IntroActivity.this, "Can't connect to server", Toast.LENGTH_SHORT);
            }
        }
    }

    private final int MY_PERMISSIONS_REQUEST_CODE = 1;

    private boolean checkPermissions() {
        // android.permission.INTERNET
        // android.permission.WRITE_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE
        }, MY_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }

        boolean isGranted = true;

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            startApplication();
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // ----------------------------------------------------------------------------------------------

    private void startApplication() {
        PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

        if (!TextUtils.isEmpty(preferenceUtilities.getCurrentMobileSessionId())) {
            new WebClientAsync_HasApplication_v2().execute();
        } else {
            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);

            BaseActivity.Instance.callActivity(LoginV2Activity.class);
            finish();
        }
    }

    // ----------------------------------------------------------------------------------------------

    private class WebClientAsync_HasApplication_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsFailed;
        private boolean mHasApplication;
        private String mMessage;

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            Util.printLogs("##### Current domain = " + "http://" + preferenceUtilities.getCurrentCompanyDomain());

            WebClient.HasApplication_v2(DeviceUtilities.getLanguageCode(), DeviceUtilities.getTimeZoneOffset(), CrewCloudApplication.getProjectCode(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                            try {
                                mIsFailed = false;
                                mHasApplication = jsonNode.get("HasApplication").asBoolean();
                                mMessage = jsonNode.get("Message").asText();
                            } catch (Exception e) {
                                e.printStackTrace();

                                mIsFailed = true;
                                mHasApplication = false;
                                mMessage = getString(R.string.loginActivity_message_wrong_server_site);
                            }
                        }

                        @Override
                        public void onFailure() {
                            mIsFailed = true;
                            mHasApplication = false;
                            mMessage = getString(R.string.loginActivity_message_wrong_server_site);
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mIsFailed) {
                Toast.makeText(IntroActivity.this, mMessage, Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (mHasApplication) {
                    new WebClientAsync_CheckSessionUser_v2().execute();
                } else {
                    Toast.makeText(IntroActivity.this, mMessage, Toast.LENGTH_LONG).show();
                    new WebClientAsync_Logout_v2().execute();
                }
            }
        }
    }

    private class WebClientAsync_CheckSessionUser_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsFailed;
        private boolean mIsSuccess;

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            WebClient.CheckSessionUser_v2(DeviceUtilities.getLanguageCode(),
                    DeviceUtilities.getTimeZoneOffset(), preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(),
                    new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                            mIsFailed = false;

                            try {
                                mIsSuccess = (jsonNode.get("success").asInt() == 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                mIsSuccess = false;
                            }
                        }

                        @Override
                        public void onFailure() {
                            mIsFailed = true;
                            mIsSuccess = false;
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mIsSuccess) {
                Intent intent = new Intent(IntroActivity.this, MainActivityV2.class);
                startActivity(intent);
                finish();
            } else {
                PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

                preferenceUtilities.setCurrentMobileSessionId("");
                preferenceUtilities.setCurrentCompanyNo(0);

                Intent intent = new Intent(IntroActivity.this, LoginV2Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private class WebClientAsync_Logout_v2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            WebClient.Logout_v2(preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                        }

                        @Override
                        public void onFailure() {
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();
            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);
            preferenceUtilities.setCurrentServiceDomain("");
            preferenceUtilities.setCurrentCompanyDomain("");
            preferenceUtilities.setCurrentUserID("");

            finish();
        }
    }

    /*------------------------------Update app ---------------------------------------------------*/

    private class ActivityHandler2 extends Handler {
        private final WeakReference<IntroActivity> mWeakActivity;

        public ActivityHandler2(IntroActivity activity) {
            mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final IntroActivity activity = mWeakActivity.get();

            if (activity != null) {
                if (msg.what == Constants.ACTIVITY_HANDLER_NEXT_ACTIVITY) {
                    startApplication();
//                    if (activity.unInstallForOldPackage()) {
//                        return;
//                    }

//                    new WebClientAsyncTask2(activity).execute();
                    //                activity.firstChecking();
                } else if (msg.what == Constants.ACTIVITY_HANDLER_START_UPDATE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(R.string.string_update_content);
                    builder.setPositiveButton(R.string.auto_login_button_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Async_DownloadApkFile(IntroActivity.this, Constants.APP_NAME).execute();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(R.string.auto_login_button_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startApplication();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        }
    }

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            try {
                URL txtUrl = new URL(Constants.ROOT_URL_ANDROID + Constants.VERSION + Constants.APP_NAME + ".txt");
                HttpURLConnection urlConnection = (HttpURLConnection) txtUrl.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String serverVersion = bufferedReader.readLine();
                inputStream.close();
                Util.printLogs("serverVersion: " + serverVersion);
                String appVersion = BuildConfig.VERSION_NAME;

                if (serverVersion.equals(appVersion)) {
                    mActivityHandler2.sendEmptyMessageDelayed(Constants.ACTIVITY_HANDLER_NEXT_ACTIVITY, 1);
                } else {
                    mActivityHandler2.sendEmptyMessage(Constants.ACTIVITY_HANDLER_START_UPDATE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final ActivityHandler2 mActivityHandler2 = new ActivityHandler2(this);

    private class Async_DownloadApkFile extends AsyncTask<Void, Void, Void> {
        private String mApkFileName;
        private final WeakReference<IntroActivity> mWeakActivity;
        private ProgressDialog mProgressDialog = null;

        public Async_DownloadApkFile(IntroActivity activity, String apkFileName) {
            mWeakActivity = new WeakReference<>(activity);
            mApkFileName = apkFileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            IntroActivity activity = mWeakActivity.get();

            if (activity != null) {
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setMessage(getString(R.string.mailActivity_message_download_apk));
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream fileOutputStream = null;

            try {
                //URL apkUrl = new URL("http://www.bizsw.co.kr:8080/Attachments/AppVn/File/" + mApkFileName + ".apk");
                URL apkUrl = new URL(Constants.ROOT_URL_ANDROID + Constants.PACKGE + mApkFileName + ".apk");
                urlConnection = (HttpURLConnection) apkUrl.openConnection();
                inputStream = urlConnection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + mApkFileName + "_new.apk";
                fileOutputStream = new FileOutputStream(filePath);

                byte[] buffer = new byte[4096];
                int readCount;

                while (true) {
                    readCount = bufferedInputStream.read(buffer);
                    if (readCount == -1) {
                        break;
                    }

                    fileOutputStream.write(buffer, 0, readCount);
                    fileOutputStream.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (urlConnection != null) {
                    try {
                        urlConnection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            IntroActivity activity = mWeakActivity.get();

            if (activity != null) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + mApkFileName + "_new.apk";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
                activity.startActivity(intent);
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }
}
