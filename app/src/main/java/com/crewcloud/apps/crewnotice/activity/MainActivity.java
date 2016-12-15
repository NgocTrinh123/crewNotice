package com.crewcloud.apps.crewnotice.activity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.*;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.WebContentChromeClient;
import com.crewcloud.apps.crewnotice.base.WebContentClient;
import com.crewcloud.apps.crewnotice.loginv2.BaseActivity;
import com.crewcloud.apps.crewnotice.util.DeviceUtilities;
import com.crewcloud.apps.crewnotice.util.HttpRequest;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private WebView wvContent = null;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }

        wvContent = (WebView)findViewById(R.id.wvContent);
        mProgressBar = (ProgressBar) findViewById(R.id.pbProgress);

        initWebContent();
    }

    private void initWebContent() {
        WebSettings webSettings = wvContent.getSettings();

        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);

        wvContent.setWebChromeClient(new WebContentChromeClient());
        wvContent.setWebViewClient(new WebContentClient(this, mProgressBar));

        wvContent.setVerticalScrollBarEnabled(true);
        wvContent.setHorizontalScrollBarEnabled(true);

        wvContent.addJavascriptInterface(new JavaScriptExtension(), "crewcloud");
//        wvContent.addJavascriptInterface(new OpenSetting(),"setting");

        PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

        String domain = preferenceUtilities.getCurrentCompanyDomain();

        CookieManager.getInstance().setCookie("http://" + domain, "skey0=" + preferenceUtilities.getCurrentMobileSessionId());
        CookieManager.getInstance().setCookie("http://" + domain, "skey1=" + "123123123123132");
        CookieManager.getInstance().setCookie("http://" + domain, "skey2=" + DeviceUtilities.getLanguageCode());
        CookieManager.getInstance().setCookie("http://" + domain, "skey3=" + preferenceUtilities.getCurrentCompanyNo());

        wvContent.loadUrl("http://" + domain + "/UI/MobileNotice/");
    }

    private final class JavaScriptExtension {
        @JavascriptInterface
        public void openSetting() {
            BaseActivity.Instance.callActivity(SettingActivity.class);
        }
    }

    // ----------------------------------------------------------------------------------------------

    private boolean mIsBackPressed = false;

    private static class ActivityHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public ActivityHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.setBackPressed(false);
            }
        }
    }

    private final ActivityHandler mActivityHandler = new ActivityHandler(this);

    public void setBackPressed(boolean isBackPressed) {
        mIsBackPressed = isBackPressed;
    }

    @Override
    public void onBackPressed() {
        if (wvContent.canGoBack()) {
            wvContent.goBack();
        } else {
            if (!mIsBackPressed) {
                Toast.makeText(this, R.string.mainActivity_message_exit, Toast.LENGTH_SHORT).show();
                mIsBackPressed = true;
                mActivityHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                finish();
            }
        }
    }

    // ----------------------------------------------------------------------------------------------

    private DownloadManager FileDownloadManager = null;
    private DownloadManager.Request FileDownloadRequest = null;
    private Uri UriToDownload = null;
    private long FileDownloadLatestId = -1;
    private final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment\\s*;\\s*filename\\s*=\\s*\"*([^\"]*)\"*");

    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
            String fileName = parseContentDisposition(contentDisposition);
            UriToDownload = Uri.parse(url);
            FileDownloadRequest = new DownloadManager.Request(UriToDownload);
            FileDownloadRequest.setTitle(fileName);
            FileDownloadRequest.setDescription("공지사항 첨부파일 다운로드");
            FileDownloadRequest.setDestinationInExternalPublicDir("/Download", fileName);
            FileDownloadRequest.setVisibleInDownloadsUi(true);
            FileDownloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Environment.getExternalStoragePublicDirectory("/Download").mkdir();
            FileDownloadLatestId = FileDownloadManager.enqueue(FileDownloadRequest);

            Toast.makeText(MainActivity.this, "다운로드를 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        private String parseContentDisposition(String contentDisposition) {
            try {
                Matcher m = CONTENT_DISPOSITION_PATTERN.matcher(contentDisposition);
                if (m.find()) {
                    return java.net.URLDecoder.decode(m.group(1), "UTF-8");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();
        int timezone = preferenceUtilities.getTIME_ZONE();
        int Cur = DeviceUtilities.getTimeZoneOffset();
        if (timezone != Cur) {
            preferenceUtilities.setTIME_ZONE(Cur);
            HttpRequest.getInstance().updateTimeZone(preferenceUtilities.getGCMregistrationid());
        }
    }
}