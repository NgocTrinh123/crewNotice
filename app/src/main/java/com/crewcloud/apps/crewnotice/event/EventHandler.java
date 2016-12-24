package com.crewcloud.apps.crewnotice.event;

import android.content.Intent;
import android.os.Bundle;

import com.crewcloud.apps.crewnotice.activity.SettingActivity;
import com.crewcloud.apps.crewnotice.backup.LoginActivity;
import com.crewcloud.apps.crewnotice.base.BaseEvent;
import com.crewcloud.apps.crewnotice.base.BaseFragment;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.ui.activity.MainActivityV2;

/**
 * Created by tunglam on 12/16/16.
 */

public class EventHandler {
    private MainActivityV2 activity;

    public EventHandler(MainActivityV2 activity) {
        this.activity = activity;
    }

    public void onEvent(BaseEvent event) {
        switch (event.getType()) {
            case BaseEvent.EventType.SETTING:
                Intent intent = new Intent(activity, SettingActivity.class);
                activity.startActivity(intent);
                break;
            case BaseEvent.EventType.MENU:
                handleMenuEvent(event);
                break;
            case BaseEvent.EventType.NOTICE_DETAIL:
                Bundle bundle = event.getMenuEvent().getBundle();
                activity.changeFragmentBundle(BaseFragment.FragmentEnums.NOTICE_DETAIL, true, bundle);
                break;
        }
    }

    private void handleMenuEvent(BaseEvent event) {
        if (!(event instanceof MenuEvent)) {
            return;
        }
        MenuItem menuItem = ((MenuEvent) event).getMenuItem();
        Bundle data = ((MenuEvent) event).getBundle();
        switch (menuItem.getId()) {
            case Statics.MENU.NOTICE:

                activity.changeFragmentBundle(BaseFragment.FragmentEnums.LIST_NOTICE, false, data);
                break;
        }
    }

    private void gotoLogin() {
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
        activity.finish();
    }
}
