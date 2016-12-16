package com.crewcloud.apps.crewnotice.backup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.ui.activity.MainActivityV2;
import com.crewcloud.apps.crewnotice.util.DeviceUtilities;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.crewcloud.apps.crewnotice.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsOfActivity();
        initViewsOfActivity();
    }

    // ----------------------------------------------------------------------------------------------

    AutoCompleteTextView et_login_organization;
    EditText et_login_username, et_login_password;
    RelativeLayout btn_login_start;

    private void findViewsOfActivity() {
        et_login_organization = (AutoCompleteTextView)findViewById(R.id.et_login_organization);
        et_login_username = (EditText)findViewById(R.id.et_login_username);
        et_login_password = (EditText)findViewById(R.id.et_login_password);
        btn_login_start = (RelativeLayout)findViewById(R.id.btn_login_start);
    }

    private void initViewsOfActivity() {
        PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

        et_login_organization.setText(preferenceUtilities.getCurrentCompanyDomain());
        et_login_username.setText(preferenceUtilities.getCurrentUserID());

        btn_login_start.setOnClickListener(this);
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public void onClick(View view) {
        int idOfView = view.getId();

        if (idOfView == R.id.btn_login_start) {
            startLoginProcess();
        }
    }

    // ----------------------------------------------------------------------------------------------

    private String mLogin_userName;
    private String mLogin_password;

    private void startLoginProcess() {
        String organizationId = et_login_organization.getText().toString().toLowerCase();
        mLogin_userName = et_login_username.getText().toString();
        mLogin_password = et_login_password.getText().toString();

        if (TextUtils.isEmpty(organizationId)) {
            Toast.makeText(this, R.string.loginActivity_message_organizationId, Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(mLogin_userName)) {
            Toast.makeText(this, R.string.loginActivity_message_userName, Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(mLogin_password)) {
            Toast.makeText(this, R.string.loginActivity_message_password, Toast.LENGTH_LONG).show();
            return;
        }

        PreferenceUtilities mPreferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

        int firstDotIndex = organizationId.indexOf(".");

        if (firstDotIndex == -1) {
            mPreferenceUtilities.setCurrentServiceDomain("http://www.crewcloud.net");
            mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ".crewcloud.net");
        } else {
            if (organizationId.endsWith("bizsw.co.kr")) {
                mPreferenceUtilities.setCurrentServiceDomain("http://www.bizsw.co.kr:8080");
                mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ":8080");
            } else {
                mPreferenceUtilities.setCurrentServiceDomain("http://" + organizationId);
                mPreferenceUtilities.setCurrentCompanyDomain(organizationId);
            }
        }

        new WebClientAsync_HasApplication_v2().execute();
    }

    private class WebClientAsync_HasApplication_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsFailed;
        private boolean mHasApplication;
        private String mMessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            WebClient.HasApplication_v2(DeviceUtilities.getLanguageCode(),
                    DeviceUtilities.getTimeZoneOffset(), CrewCloudApplication.getProjectCode(),
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
                Toast.makeText(LoginActivity.this, mMessage, Toast.LENGTH_LONG).show();
            } else {
                if (mHasApplication) {
                    new WebClientAsync_Login_v2().execute();
                } else {
                    Toast.makeText(LoginActivity.this, mMessage, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class WebClientAsync_Login_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsSuccess;
        private String mErrorMessage;
        private JsonNode mDataJsonNode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();

            WebClient.Login_v2(DeviceUtilities.getLanguageCode(),
                    DeviceUtilities.getTimeZoneOffset(), preferenceUtilities.getCurrentCompanyDomain(),
                    mLogin_userName, mLogin_password, DeviceUtilities.getOSVersion(),
                    preferenceUtilities.getCurrentServiceDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                            try {
                                mIsSuccess = (jsonNode.get("success").asInt() == 1);

                                if (!mIsSuccess) {
                                    mErrorMessage = jsonNode.get("error").get("message").asText();
                                } else {
                                    mDataJsonNode = jsonNode.get("data");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                                mIsSuccess = false;
                                mErrorMessage = getString(R.string.loginActivity_message_wrong_server_site);
                            }
                        }

                        @Override
                        public void onFailure() {
                            mIsSuccess = false;
                            mErrorMessage = getString(R.string.loginActivity_message_wrong_server_site);
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mIsSuccess) {
                try {
                    PreferenceUtilities preferenceUtilities = CrewCloudApplication.getInstance().getPreferenceUtilities();
                    preferenceUtilities.setCurrentCompanyNo(mDataJsonNode.get("CompanyNo").asInt());
                    preferenceUtilities.setCurrentMobileSessionId(mDataJsonNode.get("session").asText());
                    preferenceUtilities.setCurrentUserID(mLogin_userName);

                    Intent intent = new Intent(LoginActivity.this, MainActivityV2.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(LoginActivity.this, getString(R.string.loginActivity_message_wrong_server_site), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, mErrorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}