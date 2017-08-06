package com.algo.transact.generic_structures;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.algo.transact.AppConfig.AppState;

import java.util.ArrayList;

public class GenericAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList list;
    private ListView listView;
    private int listViewItemId;
    private IGenericAdapter listener;

    private GenericAdapter()
    {

    }
    public GenericAdapter(Activity activity, IGenericAdapter listener, ListView listView, ArrayList list, int listViewItemId) {
        this.activity = activity;
        this.list = list;
        this.listView = listView;
        this.listViewItemId =listViewItemId;
        this.listener = listener;
        this.listView.setAdapter(this);
        this.listView.setOnItemClickListener(this.listener);
        Log.i(AppState.TAG," Object creation of Generic Adapter");

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int index, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                   view = inflater.inflate(listViewItemId, parent, false);
        }

        view= listener.addViewItemToList(view,list.get(index));

      //  TextView shop_display_name = (TextView) view.findViewById(R.id.closest_shop_shop_display_name);
    //    TextView shop_name = (TextView) view.findViewById(R.id.closest_shop_shop_display_name);

//        ShopDetails details=(ShopDetails) list.get(index);
 //       shop_display_name.setText(" " + details.getShopDisplayName());
  //      shop_name.setText(" " + details.getShopName());

        return view;
    }
}