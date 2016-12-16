package com.crewcloud.apps.crewnotice.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BaseActivity;
import com.crewcloud.apps.crewnotice.base.BaseAdapter;
import com.crewcloud.apps.crewnotice.event.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tunglam on 12/15/16.
 */
public class LeftMenuAdapter extends BaseAdapter<MenuItem, LeftMenuAdapter.ViewHolder> {


    public LeftMenuAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public LeftMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_menu, parent, false);
        return new LeftMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LeftMenuAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_menu)
        TextView tvMenu;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "to be continue", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bind(int position) {
            MenuItem menuItem = getItem(position);
            tvMenu.setText(menuItem.getName());
        }
    }
}
