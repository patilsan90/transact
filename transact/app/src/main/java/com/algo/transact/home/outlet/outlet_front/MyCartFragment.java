package com.algo.transact.home.outlet.outlet_front;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.outlet.SASCheckoutActivity;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
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
    private TextView tvMyCartPlaceholder;
    private Outlet outlet;

    public MyCartFragment() {
        // Required empty public constructor
    }

    ListView lvCartsList;
    Cart cart;
    Fragment fragment;



    //TextView tvCartTotal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        lvCartsList = (ListView) view.findViewById(R.id.my_cart_list);
        tvMyCartPlaceholder = (TextView) view.findViewById(R.id.my_cart_list_tv_placeholder);
       // tvCartTotal = (TextView) view.findViewById(R.id.cart_total);
        fragment = this;

        shopID = getActivity().getIntent().getIntExtra(IntentPutExtras.ID, 0);
        outlet  = (Outlet) getActivity().getIntent().getSerializableExtra(IntentPutExtras.OUTLET_OBJECT);

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID "+shopID);

  //      lvCartsList.addFooterView(view.findViewById(R.id.my_cart_checkout));
        // swipeDetector = new SwipeDetector();
       // listView.setOnTouchListener(swipeDetector);
       // listView.setOnItemClickListener(this);
       // getActivity().getIntent().getStringExtra();

        Log.i("Generic info "," Activity onCreate MyCartFragment ....");
        cart = CartsFactory.getInstance().getCart(outlet);

        lvCartsList.setEmptyView(tvMyCartPlaceholder);
        genericAdapter = new GenericAdapter(this.getActivity(), this, lvCartsList, cart.getCartList(), R.layout.list_item_view_mycart);
        OutletFront.cartAdapter = genericAdapter;

    /*    int noOfItems = cart.getCartList().size();
            double cart_total = 0;
            Item item;
            for (int i = 0; i < noOfItems; i++) {
                item = cart.getCartList().get(i);
                cart_total = cart_total + item.getDiscounted_cost() * item.getItem_count();
            }
            tvCartTotal.setText("Cart Total: " + cart_total+" Rs.");
*/
       // lvCartsList.setAdapter(cartAdapter);
        return view;
    }

    public void checkoutCart(View v)
    {
        if(cart!=null) {
            // Click action
            if (cart.getCartList().size() != 0)
            {
                Log.i("Home", "checkoutCart Clicked");
            Intent intent = new Intent(fragment.getActivity(), SASCheckoutActivity.class);
            intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
            intent.putExtra(IntentPutExtras.ID, shopID);
                intent.putExtra(IntentPutExtras.OUTLET_OBJECT, outlet);
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Outlet OBJ:: "+(outlet == null));

                //this.startActivityForResult(intent, IntentResultCode.RESULT_OK_SHOP_SELECTION);
            //startActivity(intent);
            startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);
            return;
        }
    }
            Toast toast = Toast.makeText(getActivity(), "Your cart is empty, Nothing to checkout", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
            toast.show();
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
    }

    @Override
    public View addViewItemToList(final View view, Object listItem, final int index) {

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID " + shopID);

        Item cartItem =(Item) listItem;
        TextView item_view = (TextView) view.findViewById(R.id.item_name);
        item_view.setText("" + cartItem.getItem_name());

        item_view = (TextView) view.findViewById(R.id.item_quantity);
        item_view.setText("("+cartItem.getItem_quantity()+" "+cartItem.qtTypeInString(cartItem.getItem_form())+")");

        item_view = (TextView) view.findViewById(R.id.actual_cost);
        item_view.setText(cartItem.getActual_cost()+""+ AppConfig.currency);
        item_view.setPaintFlags(item_view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        item_view = (TextView) view.findViewById(R.id.discounted_cost);
        item_view.setText("Per Item Cost: " + cartItem.getDiscounted_cost()+""+ AppConfig.currency+" / ");

        item_view = (TextView) view.findViewById(R.id.total_cost);
        double total_cost = cartItem.getDiscounted_cost() * cartItem.getItem_count();

        item_view.setText("Total Cost: " + total_cost+""+ AppConfig.currency);

        TextView total_items_view = (TextView) view.findViewById(R.id.total_items);

        total_items_view.setText("" + cartItem.getItem_count());

        ImageButton trashCartItem = (ImageButton) view.findViewById(R.id.trash_cart_item);
        trashCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AppState.getInstance().getCartItemList().remove(index);

                Item item = cart.getCartList().get(index);
                item.setItem_count(0);
                cart.getCartList().remove(index);
                genericAdapter.notifyDataSetChanged();
                OutletFront.getInstance().updateCartTotal();

            }
        });

        ImageButton decreaseCartItem = (ImageButton) view.findViewById(R.id.decrease_item_count);
        decreaseCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Item item = AppState.getInstance().getCartItemList().get(index);
                Item item = cart.getCartList().get(index);
                if (item.getItem_count() > 1) {
                    item.decreaseItem_count();
                    genericAdapter.notifyDataSetChanged();
                    OutletFront.getInstance().updateCartTotal();
                }
            }
        });

        ImageButton increaseCartItem = (ImageButton) view.findViewById(R.id.increase_item_count);
        increaseCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = cart.getCartList().get(index);
                item.increaseItem_count();
                genericAdapter.notifyDataSetChanged();
                OutletFront.getInstance().updateCartTotal();
            }
        });


        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {
        //OutletFront.getInstance().updateCartTotal();
    }

    public void addItemToCart(Item newItem) {
        cart.getCartList().add(newItem);
        genericAdapter.notifyDataSetChanged();
/*
        Item cartItem;
        int noOfItems = cart.getCartList().size();
        double cart_total = 0;
        for (int i = 0; i < noOfItems; i++) {
            cartItem = cart.getCartList().get(i);
            cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_count();
        }
        tvCartTotal.setText("Cart Total: " + cart_total+" Rs.");
*/

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName()+" adding new item");

    }
}
