package com.crewcloud.apps.crewnotice.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.adapter.LeftMenuAdapter;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseEvent;
import com.crewcloud.apps.crewnotice.base.BaseFragment;
import com.crewcloud.apps.crewnotice.data.LeftMenu;
import com.crewcloud.apps.crewnotice.event.EventHandler;
import com.crewcloud.apps.crewnotice.event.MenuEvent;
import com.crewcloud.apps.crewnotice.event.MenuItem;
import com.crewcloud.apps.crewnotice.listener.OnClickOptionMenu;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.module.leftmenu.LeftMenuPresenter;
import com.crewcloud.apps.crewnotice.module.leftmenu.LeftMenuPresenterImp;
import com.crewcloud.apps.crewnotice.ui.fragment.NoticeDetailFragment;
import com.crewcloud.apps.crewnotice.ui.fragment.NoticeFragment;
import com.crewcloud.apps.crewnotice.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tunglam on 12/15/16.
 */

public class MainActivityV2 extends BaseActivity implements LeftMenuPresenter.view {

    private BaseFragment currentFragment;
    @Bind(R.id.lv_menu)
    RecyclerView lvMenu;

    LeftMenuPresenterImp leftMenuPresenterImp;

    private LeftMenuAdapter leftMenuAdapter;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.ic_left)
    ImageView imvLeft;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.left_menu)
    View leftMenuLayout;

    @Bind(R.id.option_menu)
    TextView tvOptionMenu;

    @Bind(R.id.imv_option_menu)
    ImageView imvOptionMenu;

    @Bind(R.id.imv_logo)
    ImageView imvActionbarLogo;

    @Bind(R.id.avatar)
    CircleImageView avatar;

    @Bind(R.id.user_name)
    TextView user_name;

    @Bind(R.id.email)
    TextView email;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    private int leftIcon;
    private EventHandler eventHandler;
    private LeftDrawer leftDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        ButterKnife.bind(this);
        setupToolbar();
        changeFragment(new NoticeFragment(), false, NoticeFragment.class.getSimpleName());
        EventBus.getDefault().register(this);
        eventHandler = new EventHandler(this);

        leftMenuAdapter = new LeftMenuAdapter(this);
        leftMenuPresenterImp = new LeftMenuPresenterImp(this);
        leftMenuPresenterImp.attachView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lvMenu.setLayoutManager(linearLayoutManager);
        lvMenu.setAdapter(leftMenuAdapter);

        leftMenuAdapter.setLeftMenuListener(new LeftMenuAdapter.ItemLeftMenuListener() {
            @Override
            public void onClickItem(int position) {
                drawerLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (drawerLayout != null) {
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putParcelable("OBJECT", leftMenuAdapter.getItem(position));

                MenuEvent menuEvent = new MenuEvent(BaseEvent.EventType.MENU);
                MenuItem item = new MenuItem(Statics.MENU.NOTICE);
                menuEvent.setMenuItem(item);
                menuEvent.setBundle(bundle);
                EventBus.getDefault().post(menuEvent);
            }
        });

        initView();

        leftMenuPresenterImp.getLeftMenu();

    }

    private void initView() {
        String ava = CrewCloudApplication.getInstance().getPreferenceUtilities().getUserAvatar();
        String name = CrewCloudApplication.getInstance().getPreferenceUtilities().getFullName();
        String email = CrewCloudApplication.getInstance().getPreferenceUtilities().getEmail();
        Util.showImage(ava, avatar);
        this.user_name.setText(name);
        this.email.setText(email);
    }


    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event.getType() == BaseEvent.EventType.MENU) {
//            leftDrawer.highlightMenu(((MenuEvent) event).getMenuItem().getId());
        }
        eventHandler.onEvent(event);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        tvTitle.setSelected(true);
    }

    public void changeFragmentBundle(BaseFragment.FragmentEnums tag, boolean addBackStack, Bundle bundle) {
        BaseFragment fragment = null;
        switch (tag) {
            case LIST_NOTICE:
                fragment = NoticeFragment.newInstance(bundle);
                break;
            case NOTICE_DETAIL:
                fragment = NoticeDetailFragment.newInstance(bundle);
        }
        if (fragment != null) {
            changeFragment(fragment, addBackStack, tag.getFragmentName());
        }
    }


    @Override
    public void changeFragment(BaseFragment fragment, boolean addBackStack,
                               String name) {
        if (fragment == null) {
            return;
        }
        currentFragment = fragment;

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!addBackStack) {
            setLeftMenu(R.drawable.ic_menu_white_24dp);
            //clear stack
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            for (int i = 0; i < count; ++i) {
                fm.popBackStackImmediate();
            }
        }
        transaction.replace(R.id.frame_container, fragment, name);

        if (addBackStack) {
            setLeftMenu(R.drawable.ic_keyboard_backspace_white_24dp);
            transaction.addToBackStack(name);
        }

        transaction.commitAllowingStateLoss();

    }

    public void setLeftMenu(int drawable) {
        leftIcon = drawable;
        imvLeft.setImageResource(leftIcon);
        switch (leftIcon) {
            case R.drawable.ic_keyboard_backspace_white_24dp:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
                break;
            case R.drawable.ic_menu_white_24dp:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Util.hideKeyboard(this);
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else if (currentFragment != null && currentFragment.onBackPressed()) {
            Log.d("BACK", "Fragment back pressed");
        } else {
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0 && !(currentFragment instanceof NoticeFragment)) {
//                changeFragment(new NoticeFragment(), false, NoticeFragment.class.getSimpleName());
//                return;
//            } else
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                currentFragment = getActiveFragment();
            }
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                setLeftMenu(R.drawable.ic_menu_white_24dp);
            }
        }
    }

    @Override
    public void setShowActionbarLogo(boolean isShow) {
        if (imvActionbarLogo != null && tvTitle != null) {
            imvActionbarLogo.setVisibility(isShow ? View.VISIBLE : View.GONE);
            tvTitle.setVisibility(!isShow ? View.VISIBLE : View.GONE);
        }
    }

    public BaseFragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (currentFragment != null) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void setTitle(String title) {
        if (tvTitle != null && title != null) {
            tvTitle.setText(title);
        }
    }

    @OnClick(R.id.ic_left)
    void onClickLeftIcon() {
        switch (leftIcon) {
            case R.drawable.ic_keyboard_backspace_white_24dp:
                onBackPressed();
                break;
            case R.drawable.ic_menu_white_24dp:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @OnClick(R.id.option_menu)
    void onClickOptionMenu(View view) {
        if (currentFragment != null) {
            currentFragment.onOptionMenuClick(view);
        }
    }

    @Override
    public void setOptionMenu(String name, final OnClickOptionMenu listener) {
        if (tvOptionMenu == null) {
            return;
        }

        if (TextUtils.isEmpty(name)) {
            name = "";
            tvOptionMenu.setOnClickListener(null);
            tvOptionMenu.setVisibility(View.GONE);
        } else {
            tvOptionMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick();
                    }
                }
            });
            tvOptionMenu.setVisibility(View.VISIBLE);
        }
        tvOptionMenu.setText(name);
    }

    @Override
    public void setOptionMenu(int icon, final OnClickOptionMenu listener) {
        if (imvOptionMenu == null) {
            return;
        }
        if (icon != 0) {
            imvOptionMenu.setVisibility(View.VISIBLE);
            imvOptionMenu.setImageResource(icon);
            imvOptionMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick();
                    }
                }
            });
        } else {
            imvOptionMenu.setVisibility(View.GONE);
            imvOptionMenu.setImageDrawable(null);
            imvOptionMenu.setOnClickListener(null);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.fab)
    public void onClickFab() {
        Toast.makeText(this, "Add News", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setActionFloat(boolean isShow) {
        if (!isShow) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetMenuSuccess(List<LeftMenu> leftMenu) {
        LeftMenu importionLeftMenu = new LeftMenu();
        importionLeftMenu.setName("Important");

        LeftMenu all = new LeftMenu();
        all.setName("All");

        leftMenu.add(0, importionLeftMenu);
        leftMenu.add(1, all);
        leftMenuAdapter.addAll(leftMenu);
    }

    @Override
    public void onError(String message) {

    }

    @OnClick(R.id.setting_drawer)
    void onClickSetting() {
        BaseEvent event = new BaseEvent(BaseEvent.EventType.SETTING);
        EventBus.getDefault().post(event);
    }

}
