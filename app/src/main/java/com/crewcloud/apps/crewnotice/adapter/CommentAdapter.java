package com.crewcloud.apps.crewnotice.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseAdapter;
import com.crewcloud.apps.crewnotice.data.Comment;
import com.crewcloud.apps.crewnotice.view.CustomLinearLayoutManager;
import com.crewcloud.apps.crewnotice.view.MyRecyclerView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/16/16.
 */

public class CommentAdapter extends BaseAdapter<Comment, CommentAdapter.ViewHolder> {
    public CommentAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

//        @Bind(R.id.item_comment_tv_reply_comment)
//        TextView tvReplyComment;

        @Bind(R.id.item_comment_list_reply)
        MyRecyclerView listReply;

        ReplyAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter = new ReplyAdapter(mActivity);

        }

        public void bind(int position) {
            Comment comment = getItem(position);
            Picasso.with(getContext()).load(comment.getAvatar())
                    .placeholder(R.mipmap.photo_placeholder)
                    .error(R.mipmap.no_photo)
                    .into(ivAvatar);
            tvAuthor.setText(comment.getAuthor());
            tvTime.setText(comment.getTime());
            if (comment.getLstReply() != null && comment.getLstReply().size() > 0) {
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
