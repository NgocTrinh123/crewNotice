package com.crewcloud.apps.crewnotice.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.listener.OnClickOptionMenu;
import com.crewcloud.apps.crewnotice.ui.fragment.NoticeDetailFragment;
import com.crewcloud.apps.crewnotice.ui.fragment.NoticeFragment;


/**
 * Created by tunglam on 11/10/16.
 */

public class BaseFragment extends Fragment {
    private BaseActivity activity;
    private String title;
    private String optionMenuName;
    private OnClickOptionMenu listener;
    private int optionIcon;
    private OnViewCreatedListener onViewCreatedListener;
    private int viewPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    public void setTitle(String title) {
        this.title = title;
        activity.setTitle(title);
    }

    public void setOptionMenu(String name, OnClickOptionMenu listener) {
        optionMenuName = name;
        this.listener = listener;
        activity.setOptionMenu(name, listener);
    }

    public void setOptionMenu(int icon, OnClickOptionMenu listener) {
        this.optionIcon = icon;
        this.listener = listener;
        activity.setOptionMenu(icon, listener);
    }

    public void setLeftMenu(int res) {
        activity.setLeftMenu(res);
    }

    public void setActionFloat(boolean isShow) {
        activity.setActionFloat(isShow);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMenu();
        if (onViewCreatedListener != null) {
            onViewCreatedListener.onViewCreated(viewPosition);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.setOptionMenu("", null);
        activity.setOptionMenu(0, null);
    }

    public void onOptionMenuClick(View view) {
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public int getColorTheme() {
        return ContextCompat.getColor(getBaseActivity(), R.color.colorAccent);
    }

    /**
     * Remove supper, override empty code for fragments inside BaseTabFragment
     */
    protected void setUpMenu() {
        if (activity == null) {
            return;
        }
        activity.setTitle(title);
        activity.setOptionMenu(optionMenuName, listener);
        activity.setOptionMenu(optionIcon, listener);
    }

    public enum FragmentEnums {
        NOTICE_DETAIL(NoticeDetailFragment.class.getName()),
        LIST_NOTICE(NoticeFragment.class.getName());
        private String fragmentName;

        FragmentEnums(String name) {
            this.fragmentName = name;
        }

        public String getFragmentName() {
            return this.fragmentName;
        }
    }

    public void setOnViewCreatedListener(OnViewCreatedListener onViewCreatedListener, int viewPosition) {
        this.onViewCreatedListener = onViewCreatedListener;
        this.viewPosition = viewPosition;
    }

    public boolean onBackPressed() {
        return false;
    }

    public interface OnViewCreatedListener {
        void onViewCreated(int position);
    }

    public boolean hasOptionMenu() {
        return !TextUtils.isEmpty(optionMenuName) || optionIcon > 0;
    }

    public String getTitle() {
        return title;
    }

    public OnClickOptionMenu getListener() {
        return listener;
    }

    public String getOptionMenuName() {
        return optionMenuName;
    }

    public int getOptionIcon() {
        return optionIcon;
    }
}
