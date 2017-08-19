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
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_retrivals.CartsFactory;
import com.algo.transact.home.shopatshop.data_retrivals.DataRetriver;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartsListFragment extends Fragment implements IGenericAdapter{


    private ListView lvCartsList;
    private ArrayList<Cart> alCartsList;
    private GenericAdapter genericAdapter;

    public CartsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_carts_list, container, false);

        lvCartsList=(ListView)view.findViewById(R.id.carts_list_lv_carts_list);
        alCartsList= DataRetriver.retriveStoredCarts();

        //Activity activity, IGenericAdapter listener, ListView listView, ArrayList list, int listViewItemId
        genericAdapter= new GenericAdapter(this.getActivity(),this, lvCartsList, alCartsList,R.layout.list_item_view_incomplete_cart);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {
        TextView shop_display_name = (TextView) view.findViewById(R.id.incomplete_cart_display_name);
        TextView shop_name = (TextView) view.findViewById(R.id.incomplete_cart_shop_name);

        Cart details=(Cart) listItem;
        shop_display_name.setText(" " + details.getCartShopDisplayName());
        shop_name.setText(" " + details.getCartShopName());
        //Log.i(AppState.TAG,"addViewItemToList incompleteCart "+details.getCartShopDisplayName() +"  "+details.getCartShopName());
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(AppState.TAG,"onItemClick of IncompleteCart ::"+position);

        int shopID = alCartsList.get(position).getShopID();

        CartsFactory cartsFactory = CartsFactory.getInstance();
        Cart cart = cartsFactory.getCart(shopID);
        if (cart == null)
            Log.i(AppState.TAG, "onItemClick IncompleteCart  ShopID::" + shopID + " has no cart stored");
        else
            Log.i(AppState.TAG, "onItemClick IncompleteCart  ShopID::" + shopID + " has cart stored");

        Intent intent = new Intent(getActivity(), ShopAtShop.class);
        intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP);
        intent.putExtra(IntentPutExtras.ID, shopID);
        getActivity().startActivityForResult(intent, IntentResultCode.RESULT_OK_SHOP_SELECTION);
        //getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }
}
