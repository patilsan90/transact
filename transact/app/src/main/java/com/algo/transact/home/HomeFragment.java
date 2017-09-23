package com.algo.transact.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.home.outlet.outlet_selection.OutletSelectorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout restaurants = (LinearLayout) view.findViewById(R.id.fragment_home_restaurants);
        restaurants.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragment_home_restaurants:
            {
                Log.i(AppState.TAG, "Clicked on shop @ shop");
                Intent ssaIntent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                //below line is for testing purpose
                this.getActivity().startActivityForResult(ssaIntent, IntentResultCode.TRANSACT_REQUEST);
                break;
            }

        }
    }
}

