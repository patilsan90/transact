package com.algo.transact.home.outlet.outlet_selection;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.gps_location.GPSListener;
import com.algo.transact.gps_location.GPSTracker;
import com.algo.transact.gps_location.GpsLocationReceiver;
import com.algo.transact.home.outlet.outlet_front.OutletFront;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
import com.algo.transact.home.outlet.data_retrivals.DataRetriver;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearByOutletsListFragment extends Fragment implements IGenericAdapter, GPSListener {

    ArrayList<Outlet> alShops10Closest;
    private ListView lvShopsList;
    private GenericAdapter genericAdapter;
    private TextView tvShopsListPlaceholder;
    private GPSTracker gpsTracker;
    Location currentGPSlocation;

    public NearByOutletsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        GpsLocationReceiver.listener = this;

        View view = inflater.inflate(R.layout.fragment_shops_list, container, false);
        lvShopsList = (ListView) view.findViewById(R.id.shops_list_lv_shops_list);
        tvShopsListPlaceholder = (TextView) view.findViewById(R.id.outlets_list_list_tv_placeholder);


        lvShopsList.setEmptyView(tvShopsListPlaceholder);
        //Activity activity, IGenericAdapter listener, ListView listView, ArrayList list, int listViewItemId

        populateOutletsList();

        return view;
    }

   public void populateOutletsList()
    {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        gpsTracker = new GPSTracker(getApplicationContext(), getActivity());
        if (gpsTracker.canGetLocation()) {

            final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), permissions, Permissions.RC_HANDLE_GPS_PERM);

                Log.i(AppState.TAG, "In RequestPermission");
            } else {
                currentGPSlocation = gpsTracker.getCurrentLocation();
                if(currentGPSlocation!=null) {
                    Log.i(AppState.TAG, "Location getAltitude " + currentGPSlocation.getAltitude());
                    Log.i(AppState.TAG, "Location getLongitude " + currentGPSlocation.getLongitude());
                    Log.i(AppState.TAG, "Location getLatitude " + currentGPSlocation.getLatitude());
                    Log.i(AppState.TAG, "Location getProvider " + currentGPSlocation.getProvider());
                     alShops10Closest = DataRetriver.retriveClosest10Shops();
                     genericAdapter = new GenericAdapter(this.getActivity(), this, lvShopsList, alShops10Closest, R.layout.list_item_view_nearby_outlets);
                }
                else {
                    Log.e(AppState.TAG, "Error in acquiring GPS signal");
                    Toast.makeText(getActivity(), "Error in acquiring GPS signal, Please restart the app", Toast.LENGTH_LONG).show();
                }

                // Toast.makeText(this, "Alt :: " + currentGPSlocation.getAltitude() + " Lon " + currentGPSlocation.getLongitude() + " Lat " + currentGPSlocation.getLatitude(), Toast.LENGTH_LONG).show();
            }
        }
}

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {
        TextView shop_display_name = (TextView) view.findViewById(R.id.closest_shop_shop_display_name);
        TextView shop_name = (TextView) view.findViewById(R.id.closest_shop_shop_name);
        TextView outlet_location = (TextView) view.findViewById(R.id.nearby_outlets_location);

        Outlet details = (Outlet) listItem;
        shop_display_name.setText(" " + details.getOutletDisplayName());
        shop_name.setText(" " + details.getOutletName());
        outlet_location.setText(" " + details.getLocation());

        //  Log.i(AppState.TAG, "addViewItemToList  " + details.getShopDisplayName() + "  " + details.getShopName());
        /*ScrollView scrollView=(ScrollView)view.findViewById(R.id.shop_scanner_scrollView);
        scrollView.setScrollX(0);
        scrollView.setScrollY(0);*/

        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(AppState.TAG, "onItemClick of ShopList ::" + position);

        int shopID = alShops10Closest.get(position).getOutletID();

        CartsFactory cartsFactory = CartsFactory.getInstance();
        Cart cart = cartsFactory.getCart(alShops10Closest.get(position));
        if (cart == null)
            Log.i(AppState.TAG, "onItemClick ShopList  ShopID::" + shopID + " has no cart stored");
        else
            Log.i(AppState.TAG, "onItemClick ShopList  ShopID::" + shopID + " has cart stored");

        Intent intent = new Intent(getActivity(), OutletFront.class);
        intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
        intent.putExtra(IntentPutExtras.ID, shopID);
        intent.putExtra(IntentPutExtras.OUTLET_OBJECT, alShops10Closest.get(position));

        //getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onGPSenable() {
        populateOutletsList();
    }
}
