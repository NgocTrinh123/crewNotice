package com.crewcloud.apps.crewnotice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseFragment;
import com.crewcloud.apps.crewnotice.dtos.Notice;
import com.crewcloud.apps.crewnotice.factory.DataFactory;
import com.crewcloud.apps.crewnotice.loginv2.Statics;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/16/16.
 */

public class NoticeDetailFragment extends BaseFragment {

    @Bind(R.id.fragment_notice_detail_lv_attach)
    RecyclerView lvAttach;

    @Bind(R.id.fragment_notice_detail_tv_author)
    TextView tvAuthor;

    @Bind(R.id.fragment_notice_detail_tv_des)
    TextView tvDes;

    @Bind(R.id.fragment_notice_detail_tv_time)
    TextView tvTime;

    @Bind(R.id.fragment_notice_detail_tv_title)
    TextView tvTitle;

    @Bind(R.id.fragment_notice_detail_et_comment)
    EditText etComment;

    @Bind(R.id.fragment_notice_detail_lv_comment)
    RecyclerView lvComment;

    @Bind(R.id.fragment_notice_detail_iv_send)
    ImageView ivSend;

    private String idNotice;

    public static BaseFragment newInstance(Bundle bundle) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detail");
        setActionFloat(true);
        if (getArguments() != null) {
            idNotice = getArguments().getString(Statics.ID_NOTICE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        initData(DataFactory.getNotice());
    }

    private void initData(Notice notice) {
        tvTitle.setText(notice.getContent());
        tvAuthor.setText(notice.getAuthor());
        tvTime.setText(notice.getTime());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
