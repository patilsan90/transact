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
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.outlet_selection.OutletSelectorActivity;
import com.algo.transact.home.smart_home.InitialOptionsActivity;
import com.algo.transact.home.smart_home.SmartHomeActivity;

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

        LinearLayout outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_restaurants);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_groceries);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_vegetables);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_stationary);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_meat);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_newspapers);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_laundry);
        outlet_object.setOnClickListener(this);

        outlet_object = (LinearLayout) view.findViewById(R.id.fragment_home_smart_home);
        outlet_object.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragment_home_restaurants:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.RESTAURANT);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_groceries:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.GROCERY_STORE);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_vegetables:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.VEGETABLES);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_stationary:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.STATIONARY);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_meat:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.MEAT);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_newspapers:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.NEWSPAPERS);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_laundry:
            {
                Intent intent = new Intent(this.getActivity(), OutletSelectorActivity.class);
                intent.putExtra(IntentPutExtras.OUTLET_REQUEST_TYPE, Outlet.OUTLET_TYPE.LAUNDRY);
                this.getActivity().startActivity(intent);
                break;
            }
            case R.id.fragment_home_smart_home:
            {

                //Intent intent = new Intent(this.getActivity(), SmartHomeActivity.class);
                Intent intent = new Intent(this.getActivity(), InitialOptionsActivity.class);
                this.getActivity().startActivity(intent);
                break;
            }


        }
    }
}

