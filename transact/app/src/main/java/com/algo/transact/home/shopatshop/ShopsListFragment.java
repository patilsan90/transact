package com.algo.transact.home.shopatshop;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.ShopDetails;
import com.algo.transact.home.shopatshop.data_retrivals.CartsFactory;
import com.algo.transact.home.shopatshop.data_retrivals.DataRetriver;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopsListFragment extends Fragment implements IGenericAdapter {

    ArrayList<ShopDetails> alShops10Closest;
    private ListView lvShopsList;
    private GenericAdapter genericAdapter;

    public ShopsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_shops_list, container, false);
        lvShopsList = (ListView) view.findViewById(R.id.shops_list_lv_shops_list);
        alShops10Closest = DataRetriver.retriveClosest10Shops();

        //Activity activity, IGenericAdapter listener, ListView listView, ArrayList list, int listViewItemId
        genericAdapter = new GenericAdapter(this.getActivity(), this, lvShopsList, alShops10Closest, R.layout.list_item_view_10_closest_shops);

        return view;
    }

    @Override
    public View addViewItemToList(View view, Object listItem) {
        TextView shop_display_name = (TextView) view.findViewById(R.id.closest_shop_shop_display_name);
        TextView shop_name = (TextView) view.findViewById(R.id.closest_shop_shop_name);

        ShopDetails details = (ShopDetails) listItem;
        shop_display_name.setText(" " + details.getShopDisplayName());
        shop_name.setText(" " + details.getShopName());
      //  Log.i(AppState.TAG, "addViewItemToList  " + details.getShopDisplayName() + "  " + details.getShopName());
        /*ScrollView scrollView=(ScrollView)view.findViewById(R.id.shop_scanner_scrollView);
        scrollView.setScrollX(0);
        scrollView.setScrollY(0);*/

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(AppState.TAG, "onItemClick of ShopList ::" + position);

        int shopID = alShops10Closest.get(position).getShopID();

        CartsFactory cartsFactory = CartsFactory.getInstance();
        Cart cart = cartsFactory.getCart(shopID);
        if (cart == null)
            Log.i(AppState.TAG, "onItemClick ShopList  ShopID::" + shopID + " has no cart stored");
        else
            Log.i(AppState.TAG, "onItemClick ShopList  ShopID::" + shopID + " has cart stored");

        Intent intent = new Intent();
        intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP);
        intent.putExtra(IntentPutExtras.ID, shopID);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

}
