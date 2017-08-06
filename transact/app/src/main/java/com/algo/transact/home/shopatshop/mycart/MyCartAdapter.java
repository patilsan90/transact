package com.algo.transact.home.shopatshop.mycart;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.home.shopatshop.data_beans.CartItem;

public class MyCartAdapter extends BaseAdapter implements View.OnClickListener {

    //  public static boolean load=true;
    private static int item_counter;
    private Activity activity;

    public static AppState.LIST_TYPE list_type;

    public MyCartAdapter(Activity activity) {
        this.activity = activity;
        Log.i("Generic info "," Activity onCreate MyCartAdapter");
    }

    @Override
    public int getCount() {
        return AppState.getInstance().getCartItemList().size();
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
            if(list_type == AppState.LIST_TYPE.NORMAL_CART)
                view = inflater.inflate(R.layout.list_item_view_mycart, parent, false);
            else
                view = inflater.inflate(R.layout.list_item_view_mycart_checkout, parent, false);

        }

        CartItem cartItem;
        Log.i("TESTING", item_counter + " :: item_counter + size ::" + AppState.getInstance().getCartItemList().size());
        item_counter++;
        if (item_counter == AppState.getInstance().getCartItemList().size()) {
            item_counter = 0;
            int noOfItems = AppState.getInstance().getCartItemList().size();
            double cart_total = 0;
            for (int i = 0; i < noOfItems; i++) {
                cartItem = AppState.getInstance().getCartItemList().get(i);
                cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
            }
            TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
            cart_total_view.setText("Cart Total: " + cart_total);
        }

        cartItem = AppState.getInstance().getCartItemList().get(index);

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

        if(list_type == AppState.LIST_TYPE.NORMAL_CART)
            total_items_view.setText("" + cartItem.getItem_quantity());
        else
            total_items_view.setText("Total Items: " + cartItem.getItem_quantity());



            if(list_type == AppState.LIST_TYPE.NORMAL_CART) {
            view.setOnClickListener(this);

            ImageButton trashCartItem = (ImageButton) view.findViewById(R.id.trash_cart_item);
            trashCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppState.getInstance().getCartItemList().remove(index);
                    notifyDataSetChanged();
                    double cart_total = 0;
                    CartItem cartItem;
                    int noOfItems = AppState.getInstance().getCartItemList().size();
                    for (int i = 0; i < noOfItems; i++) {
                        cartItem = AppState.getInstance().getCartItemList().get(i);
                        cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                    }
                    TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
                    cart_total_view.setText("Cart Total: " + cart_total);

                }
            });

            ImageButton decreaseCartItem = (ImageButton) view.findViewById(R.id.decrease_item_count);
            decreaseCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem item = AppState.getInstance().getCartItemList().get(index);
                    if (item.getItem_quantity() > 1) {
                        item.decreaseItem_quantity();
                        notifyDataSetChanged();
                        int noOfItems = AppState.getInstance().getCartItemList().size();
                        double cart_total = 0;
                        CartItem cartItem;
                        for (int i = 0; i < noOfItems; i++) {
                            cartItem = AppState.getInstance().getCartItemList().get(i);
                            cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                        }
                        TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
                        cart_total_view.setText("Cart Total: " + cart_total);


                    }
                }
            });

            ImageButton increaseCartItem = (ImageButton) view.findViewById(R.id.increase_item_count);
            increaseCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem item = AppState.getInstance().getCartItemList().get(index);
                    item.increaseItem_quantity();
                    notifyDataSetChanged();
                    int noOfItems = AppState.getInstance().getCartItemList().size();
                    double cart_total = 0;
                    CartItem cartItem;
                    for (int i = 0; i < noOfItems; i++) {
                        cartItem = AppState.getInstance().getCartItemList().get(i);
                        cart_total = cart_total + cartItem.getDiscounted_cost() * cartItem.getItem_quantity();
                    }
                    TextView cart_total_view = (TextView) activity.findViewById(R.id.cart_total);
                    cart_total_view.setText("Cart Total: " + cart_total);

                }
            });
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        //  Toast.makeText(activity, "clicked on contact", Toast.LENGTH_SHORT).show();
        // v.setBackgroundColor(Color.BLACK);

    }


}