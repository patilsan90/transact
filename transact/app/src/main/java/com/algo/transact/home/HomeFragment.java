package com.algo.transact.home;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.algo.transact.AppState;
import com.algo.transact.R;
import com.algo.transact.login.RegisterUserFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout shop_at_shop = (LinearLayout) view.findViewById(R.id.shop_at_shop);
        shop_at_shop.setOnClickListener(new HomeClickListener(this));

        return view;
    }

}
