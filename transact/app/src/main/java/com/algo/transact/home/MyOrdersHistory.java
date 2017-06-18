package com.algo.transact.home;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.algo.transact.R;
import com.algo.transact.interfaces.IMall;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersHistory extends Fragment implements IMall {


    public MyOrdersHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders_history, container, false);
    }

    @Override
    public boolean isUpdated() {
        return false;
    }
}
