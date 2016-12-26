package com.crewcloud.apps.crewnotice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseAdapter;
import com.crewcloud.apps.crewnotice.data.Attachments;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/16/16.
 */

public class PhotoAdapter extends BaseAdapter<Attachments, PhotoAdapter.ViewHolder> {

    public PhotoAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_photo_image)
        ImageView ivImage;

        @Bind(R.id.item_photo_name)
        TextView tvName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            Attachments photo = getItem(position);
            tvName.setText(photo.getFileName());
            Picasso.with(getContext()).load(R.mipmap.no_photo)
                    .placeholder(R.mipmap.photo_placeholder)
                    .error(R.mipmap.no_photo)
                    .into(ivImage);
        }
    }
}
