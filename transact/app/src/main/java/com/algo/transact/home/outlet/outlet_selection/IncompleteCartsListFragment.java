package com.algo.transact.home.outlet.outlet_selection;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.outlet_front.OutletFront;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
import com.algo.transact.home.outlet.data_retrivals.DataRetriver;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncompleteCartsListFragment extends Fragment implements IGenericAdapter{


    private ListView lvCartsList;
    private ArrayList<Cart> alCartsList;
    private GenericAdapter cartsListGenericAdapter;
    private TextView tvCartsListPlaceholder;

    public IncompleteCartsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_incomplete_carts_list, container, false);

        lvCartsList=(ListView)view.findViewById(R.id.carts_list_lv_carts_list);
        tvCartsListPlaceholder = (TextView) view.findViewById(R.id.carts_list_list_tv_placeholder);

        lvCartsList.setEmptyView(tvCartsListPlaceholder);
        alCartsList= DataRetriver.retriveStoredCarts();

        //Activity activity, IGenericAdapter listener, ListView listView, ArrayList list, int listViewItemId
        cartsListGenericAdapter= new GenericAdapter(this.getActivity(),this, lvCartsList, alCartsList,R.layout.list_item_view_incomplete_cart);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public View addViewItemToList(View view, Object listItem, final int index) {
        TextView shop_display_name = (TextView) view.findViewById(R.id.incomplete_cart_outlet_display_name);
        TextView shop_name = (TextView) view.findViewById(R.id.incomplete_cart_outlet_name);

        TextView outlet_location = (TextView) view.findViewById(R.id.incomplete_cart_outlet_location);
        TextView total_items = (TextView) view.findViewById(R.id.incomplete_cart_total_items);
        TextView total_order_cost = (TextView) view.findViewById(R.id.incomplete_cart_total_cost);

        ImageView trash = (ImageView) view.findViewById(R.id.incomplete_cart_trash);

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AppState.getInstance().getCartItemList().remove(index);
                alCartsList.remove(index);
                cartsListGenericAdapter.notifyDataSetChanged();
            }
        });

        Cart cart=(Cart) listItem;
        shop_display_name.setText(" " + cart.getOutletDisplayName());
        shop_name.setText(" " + cart.getOutletName());

        outlet_location.setText(" " + cart.getLocation());
        int size= cart.getCartList().size();
        double total_cost=0;
        for (int i=0;i<size;i++)
            total_cost=total_cost+cart.getCartList().get(i).getItem_count()* cart.getCartList().get(i).getDiscounted_cost();

        total_items.setText("Total Item: " + size);
        total_order_cost.setText("Total cost: " + total_cost+""+ AppConfig.currency);

        //Log.i(AppState.TAG,"addViewItemToList incompleteCart "+details.getCartShopDisplayName() +"  "+details.getCartShopName());
        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(AppState.TAG,"onItemClick of IncompleteCart ::"+position);

        int shopID = alCartsList.get(position).getOutletID();

        CartsFactory cartsFactory = CartsFactory.getInstance();
        Cart cart = cartsFactory.getCart(alCartsList.get(position));
        if (cart == null)
            Log.i(AppState.TAG, "onItemClick IncompleteCart  ShopID::" + shopID + " has no cart stored");
        else
            Log.i(AppState.TAG, "onItemClick IncompleteCart  ShopID::" + shopID + " has cart stored");

        Intent intent = new Intent(getActivity(), OutletFront.class);
        intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
        intent.putExtra(IntentPutExtras.ID, shopID);
        intent.putExtra(IntentPutExtras.OUTLET_OBJECT, cart);

        getActivity().startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);
        //getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }
}
