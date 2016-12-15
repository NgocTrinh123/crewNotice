package com.crewcloud.apps.crewnotice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.crewcloud.apps.crewnotice.interfaces.BaseHTTPCallBack;
import com.crewcloud.apps.crewnotice.loginv2.BaseActivity;
import com.crewcloud.apps.crewnotice.loginv2.LoginV2Activity;
import com.crewcloud.apps.crewnotice.util.HttpRequest;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.crewcloud.apps.crewnotice.util.Util;
import com.crewcloud.apps.crewnotice.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * Created by Hung Dinh on 7/9/2016.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    ImageView img_avatar;
    RelativeLayout ln_profile, ln_notify, ln_logout;
    LinearLayout ln_general;
    public PreferenceUtilities prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page_layout);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.myColor_PrimaryDark));
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.nav_back_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ln_profile = (RelativeLayout) findViewById(R.id.ln_profile);
        ln_general = (LinearLayout) findViewById(R.id.ln_general);
        ln_notify = (RelativeLayout) findViewById(R.id.ln_notify);
        ln_logout = (RelativeLayout) findViewById(R.id.ln_logout);
        ln_profile.setOnClickListener(this);
        ln_general.setOnClickListener(this);
        ln_notify.setOnClickListener(this);
        ln_logout.setOnClickListener(this);
//        PreferenceUtilities preferenceUtilities = CreCloudApplication.getInstance().getPreferenceUtilities();
//        String serviceDomain = prefUtils.getCurrentServiceDomain();
//        String avatar = prefUtils.getAvatar();
//        String newAvatar = avatar.replaceAll("\"", "");
//        String mUrl = serviceDomain + newAvatar;
//        ImageLoader imageLoader = ImageLoader.getInstance();
        img_avatar = (ImageView) findViewById(R.id.img_avatar);
//        imageLoader.displayImage(mUrl, img_avatar);
//        UserDto userDto = UserDBHelper.getUser();
        Util.showImage(CrewCloudApplication.getInstance().getPreferenceUtilities().getUserAvatar(), img_avatar);
    }

    @Override
    public void onClick(View v) {
        if (v == ln_profile) {
//            Intent intent = new Intent(SettingActivity.this, LogoutActivity.class);
//            startActivity(intent);
            BaseActivity.Instance.callActivity(ProfileActivity.class);
        } else if (v == ln_general) {
            Toast.makeText(getApplicationContext(), "undev", Toast.LENGTH_SHORT).show();
        } else if (v == ln_notify) {
//            Intent intent = new Intent(SettingActivity.this, NotificationSettingActivity.class);
//            startActivity(intent);
            BaseActivity.Instance.callActivity(NotificationSettingActivity.class);
        } else if (v == ln_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this)
                    .setMessage(R.string.are_you_sure_loguot)
                    .setPositiveButton(Util.getString(R.string.auto_login_button_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            logout();
                        }
                    })
                    .setNegativeButton(Util.getString(R.string.auto_login_button_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

    public void logout() {
        HttpRequest.getInstance().deleteAndroidDevice(new BaseHTTPCallBack() {
            @Override
            public void onHTTPSuccess() {
                new SettingActivity.WebClientAsync_Logout_v2().execute();
            }

            @Override
            public void onHTTPFail(ErrorDto errorDto) {
                new SettingActivity.WebClientAsync_Logout_v2().execute();
            }
        });

    }

    private class WebClientAsync_Logout_v2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            final PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            WebClient.Logout_v2(preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
//                            preferenceUtilities.setCurrentMobileSessionId("");
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
            preferenceUtilities.setUserAvatar("");
            BaseActivity.Instance.startSingleActivity(LoginV2Activity.class);
            finish();
        }
    }

}
