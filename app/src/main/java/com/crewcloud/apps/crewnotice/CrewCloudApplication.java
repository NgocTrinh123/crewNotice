package com.crewcloud.apps.crewnotice;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;

public class CrewCloudApplication extends MultiDexApplication {
    private static CrewCloudApplication mInstance;
    private static PreferenceUtilities mPreferenceUtilities;
    private RequestQueue mRequestQueue;
    private String TAG = "Application";
//    /* authority */
//    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".provider";
//    /* path */
//    private static final String GET_USER_PATH = "request_user";
//    public static final Uri GET_USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GET_USER_PATH);
    public static String getProjectCode() {
        return "Notice";
    }

    public CrewCloudApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized CrewCloudApplication getInstance() {
        return mInstance;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(new DefaultRetryPolicy(Statics.REQUEST_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(Statics.REQUEST_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public synchronized PreferenceUtilities getPreferenceUtilities() {
        if (mPreferenceUtilities == null) {
            mPreferenceUtilities = new PreferenceUtilities();
        }

        return mPreferenceUtilities;
    }
}