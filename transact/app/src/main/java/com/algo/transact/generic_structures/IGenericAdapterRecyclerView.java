package com.algo.transact.generic_structures;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public interface IGenericAdapterRecyclerView {

    RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView);

    void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView);

    public void onRVClick(View view, int position, Boolean collapseState);

    public void onRVLongClick(View view, int position);

    public void onRVExpand(View view, ArrayList list, int position, View rvPrevExpanded);

}
