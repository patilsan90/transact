package com.algo.transact.home.outlet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.algo.transact.R;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;

/**
 * Created by patilsp on 8/7/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView tvItemName, tvQuantity, tvCost, tvNoOfItems;
    public ImageView ivItemImage, ivOverflow;
    public ImageView ivDecrease, ivIncrease;

    public View vParentView;
    IGenericAdapterRecyclerView listener;



    public ItemViewHolder(View view, IGenericAdapterRecyclerView listener) {
        super(view);
        tvItemName = (TextView) view.findViewById(R.id.card_item_tv_name);
        tvQuantity = (TextView) view.findViewById(R.id.card_item_tv_quantity);
        tvCost = (TextView) view.findViewById(R.id.card_item_tv_price);
        tvNoOfItems = (TextView) view.findViewById(R.id.card_item_tv_count);

        ivItemImage = (ImageView) view.findViewById(R.id.card_item_iv_image);
        ivDecrease = (ImageView) view.findViewById(R.id.card_item_iv_dec);
        ivIncrease = (ImageView) view.findViewById(R.id.card_item_iv_inc);

        //ivOverflow = (ImageView) view.findViewById(R.id.overflow);

        this.listener = listener;
        vParentView = view;

    }
}
