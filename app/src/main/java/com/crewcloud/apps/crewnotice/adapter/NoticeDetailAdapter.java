package com.crewcloud.apps.crewnotice.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.data.Comment;
import com.crewcloud.apps.crewnotice.data.NoticeDetail;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.crewcloud.apps.crewnotice.view.CustomLinearLayoutManager;
import com.crewcloud.apps.crewnotice.view.MyRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/26/16.
 */

public class NoticeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NoticeDetail noticeDetail;
    private BaseActivity mActivity;

    public NoticeDetailAdapter(BaseActivity mActivity, NoticeDetail noticeDetail) {
        this.noticeDetail = noticeDetail;
        this.mActivity = mActivity;
    }

    public void addAll(List<Comment> comments) {
        int curr = getItemCount();
        noticeDetail.getCommentList().addAll(comments);
        notifyItemRangeInserted(curr, getItemCount());
    }

    public void clear() {
        if (noticeDetail.getCommentList() != null) {
            noticeDetail.getCommentList().clear();
            notifyDataSetChanged();
        }
    }

    public void add(Comment comment) {
        noticeDetail.getCommentList().add(0, comment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (noticeDetail == null) {
            return 0;
        }
        if (noticeDetail.getCommentList() != null) {
            return noticeDetail.getCommentList().size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setNoticeDetail(NoticeDetail noticeDetail) {
        this.noticeDetail = noticeDetail;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_detail, parent, false));
        } else {
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            ((NoticeViewHolder) holder).bind();
        } else {
            ((CommentViewHolder) holder).bind(position);
        }
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
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

        PhotoAdapter adapter;

        NoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            lvAttach.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
            adapter = new PhotoAdapter(mActivity);
        }

        public void bind() {
            showData();
        }

        private void showData() {
            if (noticeDetail == null) return;
            if (!TextUtils.isEmpty(noticeDetail.getTitle())) {
                tvTitle.setText(Html.fromHtml(noticeDetail.getTitle()));
            } else {
                tvTitle.setText("");
            }
            lvAttach.setAdapter(adapter);
            adapter.addAll(noticeDetail.getAttaches());
            tvAuthor.setText(noticeDetail.getUserName());
            tvDes.setText(Html.fromHtml(noticeDetail.getContent()));
        }

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_comment_civ_avatar)
        ImageView ivAvatar;

        @Bind(R.id.item_comment_tv_author)
        TextView tvAuthor;

        @Bind(R.id.item_comment_tv_time)
        TextView tvTime;

        @Bind(R.id.item_comment_tv_count_comment)
        TextView tvCountComment;

        @Bind(R.id.item_comment_tv_reply)
        TextView tvReply;

        @Bind(R.id.item_comment_tv_content)
        TextView tvContent;

        @Bind(R.id.item_comment_list_reply)
        MyRecyclerView listReply;

        ReplyAdapter adapter;

        public CommentViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter = new ReplyAdapter(mActivity);

        }

        public void bind(int position) {
            Comment comment = noticeDetail.getCommentList().get(position - 1);
//            Picasso.with(mActivity).load(comment.get)
//                    .placeholder(R.mipmap.photo_placeholder)
//                    .error(R.mipmap.no_photo)
//                    .into(ivAvatar);
            tvAuthor.setText(comment.getUserName());
            long time = TimeUtils.getTimeFromString(comment.getRegDate());
            long timeCreated = TimeUtils.getTimeForMail(time);
            if (timeCreated == -2) {
                //today,hh:mm aa
                tvTime.setText(mActivity.getString(R.string.today) + TimeUtils.displayTimeWithoutOffset(comment.getRegDate(), true));
            } else {
                //YYY-MM-DD hh:mm aa
                tvTime.setText(TimeUtils.displayTimeWithoutOffset(comment.getRegDate(), false));
            }
            tvContent.setText(Html.fromHtml(comment.getContent()));
            if (comment.getLstReply() != null && comment.getLstReply().size() > 0) {
//                tvCountComment.setText("View More " + noticeDetail.getCommentList().size() + " Comment");
                tvCountComment.setText(String.valueOf(comment.getLstReply().size()));
                listReply.setLayoutManager(new CustomLinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
                adapter.addAll(comment.getLstReply());
                listReply.setAdapter(adapter);

            } else {
                tvReply.setVisibility(View.GONE);
                tvCountComment.setVisibility(View.GONE);
            }

        }
    }
}