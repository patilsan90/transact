package com.algo.transact.home.outlet.outlet_front;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;

/**
 * Created by patilsp on 8/7/2017.
 */

public class ListItemHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tvItemName;
    public ImageView ivIndicator;
    public RecyclerView rvItemsListView;
    public LinearLayout llHeader;
    public ImageView ivLocation;

    public View vParentView;
    IGenericAdapterRecyclerView listener;

    private static View vPrevExpanded = null;

    public ListItemHeaderHolder(View view, IGenericAdapterRecyclerView listener) {
        super(view);
        tvItemName = (TextView) view.findViewById(R.id.header_tv_item_name);
        ivIndicator = (ImageView) view.findViewById(R.id.header_iv_indicator);
        rvItemsListView = (RecyclerView) view.findViewById(R.id.header_rv_items_list);
        llHeader = (LinearLayout) view.findViewById(R.id.header_ll_header);
        ivLocation = (ImageView) view.findViewById(R.id.header_iv_location);

        llHeader.setOnClickListener(this);
        this.listener = listener;
        vParentView = view;

    }

    @Override
    public void onClick(View v) {
        int pos=getAdapterPosition();
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Position "+ pos);

        listener.onRVExpand(vParentView,null,pos, vPrevExpanded);
        vPrevExpanded = vParentView;

    }
}
