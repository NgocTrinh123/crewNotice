package com.crewcloud.apps.crewnotice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.adapter.CommentAdapter;
import com.crewcloud.apps.crewnotice.adapter.PhotoAdapter;
import com.crewcloud.apps.crewnotice.base.BaseFragment;
import com.crewcloud.apps.crewnotice.data.NoticeDetail;
import com.crewcloud.apps.crewnotice.factory.DataFactory;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.module.noticedetail.NoticeDetailPresenter;
import com.crewcloud.apps.crewnotice.module.noticedetail.NoticeDetailPresenterImp;
import com.crewcloud.apps.crewnotice.view.MyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/16/16.
 */

public class NoticeDetailFragment extends BaseFragment implements NoticeDetailPresenter.view {

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
    MyRecyclerView lvComment;

    @Bind(R.id.fragment_notice_detail_iv_send)
    ImageView ivSend;

    NoticeDetailPresenterImp noticeDetailPresenterImp;
    private int idNotice;

    PhotoAdapter photoAdapter;
    CommentAdapter commentAdapter;

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
        setHasOptionsMenu(true);
        noticeDetailPresenterImp = new NoticeDetailPresenterImp(getActivity());
        noticeDetailPresenterImp.attachView(this);
        photoAdapter = new PhotoAdapter(getBaseActivity());
        commentAdapter = new CommentAdapter(getBaseActivity());
        if (getArguments() != null) {
            idNotice = getArguments().getInt(Statics.ID_NOTICE);
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice_detail, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_notice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        lvAttach.setLayoutManager(linearLayoutManager);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lvComment.setLayoutManager(llm);

        lvComment.setAdapter(commentAdapter);
        lvAttach.setAdapter(photoAdapter);

        noticeDetailPresenterImp.getNoticeDetail(idNotice);

    }

    private void initData(NoticeDetail notice) {
        tvTitle.setText(notice.getTitle());
        tvAuthor.setText(notice.getUserName());
        tvTime.setText(notice.getStartDate());

        photoAdapter.addAll(DataFactory.getPhoto());
//        for (Comment comment : DataFactory.getComments()) {
//            if (comment.getLstReply() != null && comment.getLstReply().size() > 0) {
//                RecyclerView.LayoutParams layout = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//                lvComment.setLayoutParams(layout);
//            }
//        }
        commentAdapter.addAll(notice.getCommentList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noticeDetailPresenterImp.detachView();
    }

    @Override
    public void onGetDetailSuccess(NoticeDetail noticeDetail) {
        initData(noticeDetail);
    }

    @Override
    public void onError(String message) {

    }
}
