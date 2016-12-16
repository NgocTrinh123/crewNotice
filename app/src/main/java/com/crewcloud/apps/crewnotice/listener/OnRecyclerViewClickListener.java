package com.crewcloud.apps.crewnotice.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class OnRecyclerViewClickListener {
    public abstract void onClick(RecyclerView.Adapter adapter, View view, int position);

    public void onLongClick(RecyclerView.Adapter adapter, View view, int position) {
    }

    public void onTouch(RecyclerView.Adapter adapter, View view, int position) {
    }
}
