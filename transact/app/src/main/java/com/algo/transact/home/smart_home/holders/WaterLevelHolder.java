package com.algo.transact.home.smart_home.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.algo.transact.R;

/**
 * Created by patilsp on 8/7/2017.
 */

public class WaterLevelHolder extends RecyclerView.ViewHolder {

    public TextView water_level_tv_level;

    public WaterLevelHolder(View view) {
        super(view);
        water_level_tv_level = (TextView) view.findViewById(R.id.water_level_tv_level);
    }

}

