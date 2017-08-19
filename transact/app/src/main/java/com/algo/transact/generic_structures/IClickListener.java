package com.algo.transact.generic_structures;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */
public interface IClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}