package com.crewcloud.apps.crewnotice.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseAdapter;
import com.crewcloud.apps.crewnotice.dtos.Notice;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/15/16.
 */

public class ListNoticeAdapter extends BaseAdapter<Notice, ListNoticeAdapter.ViewHolder> {

    private OnClickNewsListener onClickNewsListener;

    public void setOnClickNewsListener(OnClickNewsListener onClickNewsListener) {
        this.onClickNewsListener = onClickNewsListener;
    }

    public ListNoticeAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public ListNoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_notice, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_list_notice_iv_image)
        ImageView ivImage;

        @Bind(R.id.item_list_notice_tv_content)
        TextView tvContent;

        @Bind(R.id.item_list_notice_tv_author)
        TextView tvAuthor;

        @Bind(R.id.item_list_notice_tv_time)
        TextView tvTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickNewsListener != null) {
                        onClickNewsListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }

        void bind(int position) {
            Notice notice = getItem(position);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            if (notice.getCountComment() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.append(notice.getTitle()).append("  ")
                            .append(" ", new ImageSpan(getContext(), R.drawable.ic_comment), 0)
                            .append(String.valueOf(notice.getCountComment())).append("");
                }
            }
            tvContent.setText(builder);
            long time = TimeUtils.getTimeFromString(notice.getDateCreated());
            long timeCreated = TimeUtils.getTimeForMail(time);
            if (timeCreated == -2) {
                //today,hh:mm aa
                tvTime.setText(mActivity.getString(R.string.today) + TimeUtils.displayTimeWithoutOffset(notice.getDateCreated(), true));
            } else {
                //YYY-MM-DD hh:mm aa
                tvTime.setText(TimeUtils.displayTimeWithoutOffset(notice.getDateCreated(), false));
            }

            tvAuthor.setText(notice.getUserName());

            if (!TextUtils.isEmpty(notice.getRepresentativeImageUrl())) {
                Picasso.with(getContext()).load(notice.getRepresentativeImageUrl())
                        .placeholder(R.mipmap.photo_placeholder)
                        .error(R.mipmap.no_photo)
                        .into(ivImage);
            } else {
                Picasso.with(getContext()).load(R.mipmap.no_photo)
                        .into(ivImage);
            }

        }
    }

    public interface OnClickNewsListener {
        void onItemClicked(int position);
    }


}
