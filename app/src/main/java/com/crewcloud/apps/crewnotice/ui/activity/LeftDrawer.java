package com.crewcloud.apps.crewnotice.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.adapter.LeftMenuAdapter;
import com.crewcloud.apps.crewnotice.base.BaseEvent;
import com.crewcloud.apps.crewnotice.factory.DataFactory;
import com.crewcloud.apps.crewnotice.module.leftmenu.LeftMenuPresenterImp;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tunglam on 12/16/16.
 */
public class LeftDrawer {
    @Bind(R.id.lv_menu)
    RecyclerView lvMenu;

    LeftMenuPresenterImp leftMenuPresenterImp;

    @OnClick(R.id.setting_drawer)
    void onClickSetting() {
        BaseEvent event = new BaseEvent(BaseEvent.EventType.SETTING);
        EventBus.getDefault().post(event);
    }


    private DrawerLayout drawerLayout;
    private LeftMenuAdapter leftMenuAdapter;

    public LeftDrawer(MainActivityV2 activity, DrawerLayout drawerLayout, View menu) {
        ButterKnife.bind(this, menu);
        this.drawerLayout = drawerLayout;



    }
}

