package com.algo.transact.home.smart_home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;

public class NewRoomDialogue extends Dialog {

    private EditText etRoomName;

    public NewRoomDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialogue_new_room);

        etRoomName = (EditText) findViewById(R.id.dialogue_new_room_et_room_name);

    }

    public void showDialogue() {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.show();
    }

}
