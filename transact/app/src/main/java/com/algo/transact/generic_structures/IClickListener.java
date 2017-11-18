package com.algo.transact.generic_structures;

import android.view.View;

/**
 * Created by sandeep on 5/8/17.
 */
public interface IClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}