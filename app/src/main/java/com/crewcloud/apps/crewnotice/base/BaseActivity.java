package com.crewcloud.apps.crewnotice.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.listener.OnClickOptionMenu;

/**
 * Created by tunglam on 11/10/16.
 */

public class BaseActivity extends AppCompatActivity {
    private int backPressCount;

    public void setTitle(String title) {

    }

    public void changeFragment(BaseFragment fragment, boolean addBackStack,
                               String name) {

    }

    public void changeFragmentBundle(BaseFragment.FragmentEnums fragment, boolean addBackStack,
                                     Bundle bundle) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setOptionMenu(String title, OnClickOptionMenu listener) {

    }

    public void setOptionMenu(int icon, OnClickOptionMenu listener) {

    }

    public void setLeftMenu(int res) {

    }

    public void setShowActionbarLogo(boolean isShow) {

    }

    public void resetLeftDrawerStatus() {

    }

    public void setActionFloat(boolean isShow) {

    }

    @Override
    public void onBackPressed() {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        }
        backPressCount++;
        if (backPressCount > 1) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(),getString(R.string.mainActivity_message_exit),Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressCount = 0;
                }
            }, 3000);
        }
    }

}
