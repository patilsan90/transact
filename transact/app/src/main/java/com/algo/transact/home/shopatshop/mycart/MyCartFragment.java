package com.algo.transact.home.shopatshop.mycart;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.CartItem;
import com.algo.transact.home.shopatshop.data_retrivals.CartsFactory;
import com.algo.transact.support_packages.SwipeDetector;
import com.algo.transact.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements AdapterView.OnItemClickListener, IGenericAdapter {

    private int shopID;
    private SwipeDetector swipeDetector;
    private GenericAdapter genericAdapter;

    public MyCartFragment() {
        // Required empty public constructor
    }

    ListView lvCartsList;
    Cart cart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        lvCartsList = (ListView) view.findViewById(R.id.my_cart_list);


        shopID = getActivity().getIntent().getIntExtra(IntentPutExtras.ID, 0);

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID "+shopID);


        // swipeDetector = new SwipeDetector();
       // listView.setOnTouchListener(swipeDetector);
       // listView.setOnItemClickListener(this);
       // getActivity().getIntent().getStringExtra();

        Log.i("Generic info "," Activity onCreate MyCartFragment ....");
       // MyCartAdapter.list_type= AppState.LIST_TYPE.NORMAL_CART;
      //  cartAdapter = new MyCartAdapter(this.getActivity());
        cart = CartsFactory.getInstance().getCart(shopID);
        genericAdapter = new GenericAdapter(this.getActivity(), this, lvCartsList, cart.getCartList(), R.layout.list_item_view_mycart);

       // lvCartsList.setAdapter(cartAdapter);
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
        MyCartAdapter.list_type= AppState.LIST_TYPE.NORMAL_CART;
    }

    @Override
    public View addViewItemToList(final View view, Object listItem, final int index) {
        CartItem cartItem =(CartItem) listItem;
        TextView item_view = (TextView) view.findViewById(R.id.item_name);
        item_view.setText(" " + cartItem.getItem_name());

        item_view = (TextView) view.findViewById(R.id.actual_cost);
        item_view.setText("Actual Cost: " + cartItem.getActual_cost());

        item_view = (TextView) view.findViewById(R.id.discounted_cost);
        item_view.setText("Disc. Cost: " + cartItem.getDiscounted_cost());

        item_view = (TextView) view.findViewById(R.id.total_cost);
        double total_cost = cartItem.getDiscounted_cost() * cartItem.getItem_quantity();

        item_view.setText("Total Cost: " + total_cost);

        TextView total_items_view = (TextView) view.findViewById(R.id.total_items);

        total_items_view.setText("" + cartItem.getItem_quantity());


        //view.setOnClickListener(this);

        ImageButton trashCartItem = (ImageButton) view.findViewById(R.id.trash_cart_item);
        trashCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppState.getInstance().getCartItemList().remove(index);
                cart.getCartList().remove(index);
                genericAdapter.notifyDataSetChanged();
                double cart_total = 0;
                CartItem cartItem;
              //  int noOfItems = AppState.getInstance().getCartItemList().size();
                int noOfItems = cart.getCartList().size();
                for (int i = 0; i < noOfItems; i++) {
                    cartItem = cart.getCartList().get(i);
                    cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                }
             //   TextView cart_total_view = (TextView) .findViewById(R.id.cart_total);
              //  cart_total_view.setText("Cart Total: " + cart_total);

            }
        });

        ImageButton decreaseCartItem = (ImageButton) view.findViewById(R.id.decrease_item_count);
        decreaseCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CartItem item = AppState.getInstance().getCartItemList().get(index);
                CartItem item = cart.getCartList().get(index);
                if (item.getItem_quantity() > 1) {
                    item.decreaseItem_quantity();
                    genericAdapter.notifyDataSetChanged();
                    int noOfItems = cart.getCartList().size();
                    double cart_total = 0;
                    CartItem cartItem;
                    for (int i = 0; i < noOfItems; i++) {
                        cartItem = cart.getCartList().get(i);
                        cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                    }
                   // TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
                    //cart_total_view.setText("Cart Total: " + cart_total);


                }
            }
        });

        ImageButton increaseCartItem = (ImageButton) view.findViewById(R.id.increase_item_count);
        increaseCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = cart.getCartList().get(index);
                item.increaseItem_quantity();
                genericAdapter.notifyDataSetChanged();
                int noOfItems = cart.getCartList().size();
                double cart_total = 0;
                CartItem cartItem;
                for (int i = 0; i < noOfItems; i++) {
                    cartItem = cart.getCartList().get(i);
                    cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                }
             //   TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
              //  cart_total_view.setText("Cart Total: " + cart_total);

            }
        });


    /*    Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    */
        return view;
    }

    public void addItemToCart(CartItem newItem) {
        cart.getCartList().add(newItem);
        genericAdapter.notifyDataSetChanged();
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName()+" adding new item");

    }
}
