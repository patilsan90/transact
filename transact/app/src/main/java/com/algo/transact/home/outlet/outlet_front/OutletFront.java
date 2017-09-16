package com.algo.transact.home.outlet.outlet_front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.barcode.BarcodeDetails;
import com.algo.transact.barcode.CodeScannerActivity;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapterSpinner;
import com.algo.transact.home.LocateCategories;
import com.algo.transact.home.outlet.ItemCountSelectionActivity;
import com.algo.transact.home.outlet.SASCheckoutActivity;
import com.algo.transact.home.outlet.SASOffersActivity;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.CategoryItem;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
import com.algo.transact.home.outlet.data_retrivals.CatalogueRetriver;
import com.algo.transact.login.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OutletFront extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ViewPager.OnPageChangeListener, IGenericAdapterSpinner {
    private static final String TAG = "CognitionMall";
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static final String LOG_TAG = OutletFront.class.getSimpleName();
    public MyCartFragment myCartFragment;
    private int shopID;
    //public OffersFragment offersFragment;
    private boolean isBack = false;
    // private Button showCartButton;
    private int back_press_counter = 0;
    private GoogleApiClient mGoogleApiClient;
    private String dataType;
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private NestedScrollView nscScroll;
    private FloatingActionButton outlet_fab_code_scanner;

    private FloatingActionButton fabMultiAction;


    public static GenericAdapter cartAdapter;
    private CatalogueFragment catalogueFragment;
    private TextView tvCartTotal;
    private static OutletFront outletFront;
    private Intent codeScannerIntent;
    private Outlet outlet;

    public static OutletFront getInstance() {
        if (outletFront != null)
            return outletFront;

        outletFront = new OutletFront();
        return outletFront;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_front);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.shop_at_shop_drawerPane);

        outlet_fab_code_scanner = (FloatingActionButton) findViewById(R.id.outlet_front_fab_code_scanner);
        outlet_fab_code_scanner.setOnClickListener(this);
        outletFront = this;
        fabMultiAction = (FloatingActionButton) findViewById(R.id.outlet_front_fab_multi_action);
        fabMultiAction.setOnClickListener(this);

        //  nscScroll = (NestedScrollView) findViewById(R.id.shop_at_shop_nsv_scroll);
        //  nscScroll.smoothScrollTo(0,0);
        myCartFragment = new MyCartFragment();
        //  offersFragment = new OffersFragment();
        Log.i(AppState.TAG, " Activity onCreate OutletFront");

        dataType = getIntent().getStringExtra(IntentPutExtras.DATA_TYPE);
        shopID = getIntent().getIntExtra(IntentPutExtras.ID, 0);

        outlet  = (Outlet) getIntent().getSerializableExtra(IntentPutExtras.OUTLET_OBJECT);

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Outlet OBJ:: "+(outlet == null));


/*        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Calling sequence is wrong "+shopID);*/

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID " + shopID);


        /*if (requestType.equals(IntentPutExtras.REQUEST_SELECT_SHOP)) {
            shopID = getIntent().getIntExtra(IntentPutExtras.ID, 0);
        } else {
            Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + " Calling sequence is wrong");
            this.finish();
        }*/
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
*/

        //---------------------------

        //     FragmentManager fragmentManager = getFragmentManager();
        //     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //     fragmentTransaction.add(R.id.shop_at_shop_page_frame, myCartFragment);
        //     fragmentTransaction.commit();

        catalogueFragment = new CatalogueFragment();
        viewPager = (ViewPager) findViewById(R.id.shop_at_shop_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.outlet_front_tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab catalogueTab = tabLayout.getTabAt(0);
        TabLayout.Tab cartTab = tabLayout.getTabAt(1);
        catalogueTab.setCustomView(R.layout.tab_view_catalogue);
        cartTab.setCustomView(R.layout.tab_view_cart);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(catalogueFragment, "Catalogue");
        adapter.addFragment(myCartFragment, "Cart");

        // nscScroll.smoothScrollTo(0,0);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        LinearLayout appBar = (LinearLayout) findViewById(R.id.outlet_front_appbar_place);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bar = inflater.inflate(R.layout.app_bar_catalogue, null);
        appBar.removeAllViews();
        appBar.addView(bar);
        Spinner catalogueMenu = (Spinner) bar.findViewById(R.id.catalogue_spinner_cat_list);
        ArrayList alCaterory = CatalogueRetriver.getCategories(shopID);
        GenericAdapter genericAdapter = new GenericAdapter(this, this, catalogueMenu, alCaterory, R.layout.list_item_catalogue_menu);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        // Intent intent = new Intent(getApplicationContext(), ShopScannerActivity.class);
        // intent.putExtra(IntentPutExtras.REQUEST_TYPE,IntentPutExtras.REQUEST_SELECT_SHOP);
        //  startActivityForResult(intent, IntentRequestResponseType.REQUEST_SELECT_SHOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(AppState.TAG, "onActivityResult Outlet");
        if (resultCode == IntentResultCode.TRANSACT_RESULT_CANCEL) {
            Log.e(AppState.TAG, "Error in OnActivityResult of OutletFront RequestCode: " + requestCode + " Outlet Selection cancelled");
            this.finish();
            return;
        }
        if (data == null)
            return;

        //Toast.makeText(this, "ReqCode" + requestCode + " DATA " + data.getStringExtra(IntentPutExtras.SCANNER_RESPONSE), Toast.LENGTH_LONG).show();

        String dataType = data.getStringExtra(IntentPutExtras.DATA_TYPE);

        switch (dataType) {
            /* case IntentPutExtras.REQUEST_SELECT_SHOP: {
                shopID = data.getIntExtra(IntentPutExtras.ID,0);
                Log.i(AppState.TAG, "onActivityResult OutletFront SelectedShopID " + shopID);
                break;
            }*/

            case IntentPutExtras.NEW_ITEM_DATA: {
                if (data != null) {
                    Item newItem = (Item) data.getSerializableExtra(IntentPutExtras.NEW_ITEM_DATA);
                    Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                    }.getClass().getEnclosingMethod().getName() + "REQUEST_SELECT_ITEM_FROM_SHOP adding new item");
                    if (newItem != null) {
                        viewPager.setCurrentItem(1, true);
                        myCartFragment.addItemToCart(newItem);
                    } else
                        Log.e(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                        }.getClass().getEnclosingMethod().getName() + "Item is null... oo");
                }
                break;
            }
            case IntentPutExtras.CODE_OBJECT: {
                BarcodeDetails details = (BarcodeDetails) data.getSerializableExtra(IntentPutExtras.CODE_OBJECT);
                if (details.getItemID() == null) {
                    Intent intent = new Intent(getApplicationContext(), CodeScannerActivity.class);
                    intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
                    intent.putExtra(IntentPutExtras.ID, shopID);
                    startActivityForResult(intent, IntentResultCode.TRANSACT_REQUEST);
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ItemCountSelectionActivity.class);
                intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.CODE_OBJECT);
                intent.putExtra(IntentPutExtras.CODE_OBJECT, details);
                //setResult(RESULT_OK, intent);
                startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);

                break;
            }
            default:
//                Log.e(AppState.TAG, String.format(getString(R.string.barcode_error_format), CommonStatusCodes.getStatusCodeString(resultCode)));
                Log.e(AppState.TAG, "Error in Outlet Default onActivityResult");
                break;
        }
    }

    public void openDrawer(View v) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    /*
    public void showMyCart(View view) {

        if (AppState.checkProccedStatus() == false) {
            Toast.makeText(this, "Please select mall first !!!", Toast.LENGTH_SHORT).show();
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (isBack == false) {
            showCartButton.setText("Back");
            fragmentTransaction.replace(R.id.shop_at_shop_page_frame, myCartFragment);
            isBack = true;
        } else {
            showCartButton.setText("My Cart");
            fragmentTransaction.replace(R.id.shop_at_shop_page_frame, offersFragment);
            isBack = false;
        }

        fragmentTransaction.commit();
    }
*/
    public void locateCategories(View view) {
        Log.i("Home", "locateCategories Clicked");
        Intent myIntent = new Intent(this, LocateCategories.class);
        this.startActivity(myIntent);

    }

    public void gotoHomefromSAS(View v) {
        Log.i("Home", "Select mall Clicked");
        // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
        this.finish();
    }

    public void browseOffers(View v) {
        Log.i("Home", "Select mall Clicked");
        Intent intent = new Intent(getApplicationContext(), SASOffersActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void logout(View view) {
        Log.i("Home", "Logout clicked");

        File sessionFile = new File(AppState.sessionFile);
        if (sessionFile.exists()) {
            signOutFromGmail();
            LoginManager.getInstance().logOut();
            Log.i("Home", "Logout, file exists, deleting");
            sessionFile.delete();
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
            this.finish();
        } else {
            Log.i("Home", "Logout, file does not exists, its a error case");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.outlet_front_fab_code_scanner: {

                if (codeScannerIntent == null) {
                    codeScannerIntent = new Intent(getApplicationContext(), CodeScannerActivity.class);
                    codeScannerIntent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
                    codeScannerIntent.putExtra(IntentPutExtras.ID, shopID);
                }
                startActivityForResult(codeScannerIntent, IntentResultCode.TRANSACT_REQUEST);
                break;
            }
            case R.id.outlet_front_fab_multi_action: {
                if (viewPager.getCurrentItem() == 0) {
                    catalogueFragment.collapseList(v);
                } else {
                    myCartFragment.checkoutCart(v);
                }
                break;
            }


        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void onPageSelected(int position) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

    }


    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "  Current Item:: " + viewPager.getCurrentItem());

        switch (viewPager.getCurrentItem()) {
            case 0:
                LinearLayout appBar = (LinearLayout) findViewById(R.id.outlet_front_appbar_place);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View bar = inflater.inflate(R.layout.app_bar_catalogue, null);
                appBar.removeAllViews();
                appBar.addView(bar);
                Spinner catalogueMenu = (Spinner) bar.findViewById(R.id.catalogue_spinner_cat_list);
                ArrayList alCaterory = CatalogueRetriver.getCategories(shopID);
                GenericAdapter genericAdapter = new GenericAdapter(this, this, catalogueMenu, alCaterory, R.layout.list_item_catalogue_menu);

                fabMultiAction.setImageResource(R.drawable.ic_collapse_catalogue);

                break;

            case 1:


                updateCartTotal();
                fabMultiAction.setImageResource(R.drawable.ic_cart_checkout);
                break;
        }

        // viewPager.setCurrentItem(2,true);


    }

    public void updateCartTotal() {
        if (viewPager.getCurrentItem() == 1) {
            LinearLayout appBar = (LinearLayout) findViewById(R.id.outlet_front_appbar_place);
            appBar.removeAllViews();

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View bar = inflater.inflate(R.layout.app_bar_my_cart, null);

            tvCartTotal = (TextView) bar.findViewById(R.id.cart_total);

            Cart cart = CartsFactory.getInstance().getCart(outlet);
            int noOfItems = cart.getCartList().size();
            double cart_total = 0;
            Item item;
            for (int i = 0; i < noOfItems; i++) {
                item = cart.getCartList().get(i);
                cart_total = cart_total + item.getDiscounted_cost() * item.getItem_count();
            }
            tvCartTotal.setText("Cart Total: " + cart_total + " Rs.");
            appBar.addView(bar);

        }
    }

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {
        ImageView tvCatalogueItemImage = (ImageView) view.findViewById(R.id.catalogue_menu_item_image);
        TextView tvCatalogueItemName = (TextView) view.findViewById(R.id.catalogue_menu_item_name);

        CategoryItem item = (CategoryItem) listItem;
        tvCatalogueItemName.setText("" + item.getCategoryName());

        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(AppState.TAG, "onBackPressed of OutletFront");

    }

    // [START signOut]
    private void signOutFromGmail() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //  updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
// [END signOut]
}