package com.algo.transact.home.shopatshop.mycart;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.algo.transact.support_packages.SwipeDetector;
import com.algo.transact.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements AdapterView.OnItemClickListener {

    private SwipeDetector swipeDetector;
private  MyCartAdapter cartAdapter;
    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        ListView listView = (ListView) view.findViewById(R.id.my_cart_list);


       // swipeDetector = new SwipeDetector();
       // listView.setOnTouchListener(swipeDetector);
       // listView.setOnItemClickListener(this);
        Log.i("Generic info "," Activity onCreate MyCartFragment ....");
        cartAdapter = new MyCartAdapter(this.getActivity());
        listView.setAdapter(cartAdapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (swipeDetector.swipeDetected()) {
            if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                Log.i("cart_swip"," Swippp from Right to left on %d " +position);
            } else {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cartAdapter.notifyDataSetChanged();
    }
}
