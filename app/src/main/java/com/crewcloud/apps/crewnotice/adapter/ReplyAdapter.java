package com.crewcloud.apps.crewnotice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseAdapter;
import com.crewcloud.apps.crewnotice.data.Reply;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tunglam on 12/17/16.
 */

public class ReplyAdapter extends BaseAdapter<Reply, ReplyAdapter.ViewHolder> {
    public ReplyAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_reply_iv_avatar)
        CircleImageView ivAvatar;

        @Bind(R.id.item_reply_tv_comment)
        TextView tvComment;

        @Bind(R.id.item_reply_tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void bind(int position) {
            Reply reply = getItem(position);
            Picasso.with(getContext()).load(reply.getAvatar())
                    .placeholder(R.mipmap.photo_placeholder)
                    .error(R.mipmap.no_photo)
                    .into(ivAvatar);
            tvComment.setText(reply.getComment());
            tvName.setText(reply.getName());
        }
    }
}
