package com.crewcloud.apps.crewnotice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.adapter.ListNoticeAdapter;
import com.crewcloud.apps.crewnotice.base.BaseEvent;
import com.crewcloud.apps.crewnotice.base.BaseFragment;
import com.crewcloud.apps.crewnotice.event.MenuEvent;
import com.crewcloud.apps.crewnotice.event.SearchEvent;
import com.crewcloud.apps.crewnotice.factory.DataFactory;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.view.MyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/15/16.
 */

public class NoticeFragment extends BaseFragment {

    @Bind(R.id.fragment_notice_list)
    MyRecyclerView rvList;

    ListNoticeAdapter adapter;

    public static BaseFragment newInstance(Bundle bundle) {
        NoticeFragment noticeFragment = new NoticeFragment();
        noticeFragment.setArguments(bundle);
        return noticeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setTitle("Notice");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new ListNoticeAdapter(getBaseActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);

        rvList.setAdapter(adapter);
        adapter.addAll(DataFactory.getListNotice());

//        rvList.setMyRecyclerViewListener(new MyRecyclerView.MyRecyclerViewListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//
//            }
//        });

        adapter.setOnClickNewsListener(new ListNoticeAdapter.OnClickNewsListener() {
            @Override
            public void onItemClicked(int position) {
                BaseEvent baseEvent = new BaseEvent(BaseEvent.EventType.NOTICE_DETAIL);
                Bundle data = new Bundle();
                data.putString(Statics.ID_NOTICE, adapter.getItem(position).getId());
                MenuEvent menuEvent = new MenuEvent();
                menuEvent.setBundle(data);
                baseEvent.setMenuEvent(menuEvent);
                EventBus.getDefault().post(baseEvent);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Subscribe
    public void onSearchEvent(SearchEvent event) {
        if (event != null) {
            if (event.getId() == Statics.SEARCH) {
                // API Search
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
