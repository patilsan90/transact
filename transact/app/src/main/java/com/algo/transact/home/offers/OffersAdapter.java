package com.algo.transact.home.offers;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppState;
import com.algo.transact.R;

public class OffersAdapter extends BaseAdapter implements View.OnClickListener {

    //  public static boolean load=true;
    private static int item_counter;
    private Activity activity;

    public OffersAdapter(Activity activity) {
        this.activity = activity;
        OfferItem temporary_item = new OfferItem("Item Id", "Offer 1");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 2");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 3");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 4");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 5");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 6");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 7");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 8");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 9");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 10");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 11");
        AppState.getInstance().addOfferItem(temporary_item);
        temporary_item = new OfferItem("Item Id", "Offer 12");
        AppState.getInstance().addOfferItem(temporary_item);


        Log.i("Generic info "," Activity onCreate OfferAdapter");
    }

    @Override
    public int getCount() {
        return AppState.getInstance().getOfferItemList().size();
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
            view = inflater.inflate(R.layout.list_item_view_offers, parent, false);
        }

        OfferItem item = AppState.getInstance().getOfferItemList().get(index);

        TextView offer_item_view = (TextView) view.findViewById(R.id.offer_name);
        offer_item_view.setText(" " + item.getOffer_name());

        view.setOnClickListener(this);

        offer_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Offer Selected "+index, Toast.LENGTH_SHORT).show();
            }
        });
      return view;
    }

    @Override
    public void onClick(View v) {
        //  Toast.makeText(activity, "clicked on contact", Toast.LENGTH_SHORT).show();
        // v.setBackgroundColor(Color.BLACK);

    }


}