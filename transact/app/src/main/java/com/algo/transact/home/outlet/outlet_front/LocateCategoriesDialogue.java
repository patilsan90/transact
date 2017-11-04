package com.algo.transact.home.outlet.outlet_front;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;

public class LocateCategoriesDialogue extends Dialog {

    private TextView tvTitle;
    private TextView tvLocation;

    public LocateCategoriesDialogue(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_locate_categories);
        tvTitle = (TextView)findViewById(R.id.dialogue_locate_cat_title);
        tvLocation = (TextView)findViewById(R.id.dialogue_locate_cat_location);

    }

public void showDialogue(String category, String location)
{
    Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
    }.getClass().getEnclosingMethod().getName());
    this.show();
    tvTitle.setText(category);
    tvLocation.setText(location);

}
}
