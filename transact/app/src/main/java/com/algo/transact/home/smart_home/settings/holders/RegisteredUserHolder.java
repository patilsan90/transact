package com.algo.transact.home.smart_home.settings.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.algo.transact.R;

/**
 * Created by patilsp on 8/7/2017.
 */

public class RegisteredUserHolder extends RecyclerView.ViewHolder {

    public TextView sh_registered_user_tv_remove;
    public TextView sh_registered_user_tv_mobile_no;

    public RegisteredUserHolder(View view) {
        super(view);
        sh_registered_user_tv_mobile_no = (TextView) view.findViewById(R.id.sh_registered_user_tv_mobile_no);
        sh_registered_user_tv_remove = (TextView) view.findViewById(R.id.sh_registered_user_tv_remove);


    }


}

