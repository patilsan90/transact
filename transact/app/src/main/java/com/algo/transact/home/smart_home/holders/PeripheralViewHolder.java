package com.algo.transact.home.smart_home.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.algo.transact.R;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;

/**
 * Created by patilsp on 8/7/2017.
 */

public class PeripheralViewHolder extends RecyclerView.ViewHolder {

    public TextView swPeripheralName;
    public ImageView ivPeripheralIcon;
    public TextView tvSeekbarText;
    public SeekBar sbSeekBar;

    IGenericAdapterRecyclerView listener;

    public PeripheralViewHolder(View view, IGenericAdapterRecyclerView listener) {
        super(view);
        swPeripheralName = (TextView) view.findViewById(R.id.peripheral_sw_name);
        ivPeripheralIcon =(ImageView) view.findViewById(R.id.peripheral_iv_icon);
        tvSeekbarText = (TextView) view.findViewById(R.id.peripheral_tv_seekbar_text);
        sbSeekBar = (SeekBar) view.findViewById(R.id.peripheral_sb_seekbar);
        this.listener = listener;
    }
}
