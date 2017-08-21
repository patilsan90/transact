package com.algo.transact.home.shopatshop;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterSpinner;
import com.algo.transact.home.shopatshop.data_beans.CategoryItem;
import com.algo.transact.home.shopatshop.data_beans.Item;
import com.algo.transact.home.shopatshop.data_beans.SubCategoryItem;
import com.algo.transact.home.shopatshop.data_retrivals.CatalogueRetriver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment implements IGenericAdapterSpinner, IGenericAdapterRecyclerView, View.OnClickListener {


    ArrayList<CategoryItem> alCaterory;
    HashMap<SubCategoryItem, ArrayList<Item>> hmCatalogueItems = new HashMap<>();
    private int shopID;
    private int selectedCatagory;
    private GenericAdapter genericAdapter;
    private GenericAdapterRecyclerView rvGenericAdapter;
    private GenericAdapterRecyclerView rvItemsAdapter;
    private RecyclerView rvCatalogueList;
    private RecyclerView rvItemsList;
    private View vPrevExpanded;
    private View vCurrentExpanded;
    private ArrayList<Boolean> alisExpanded = new ArrayList<>();
    private ArrayList<SubCategoryItem> alSubCaterory;
    private int scrollState;
    private FloatingActionButton fabCollapse;


    public CatalogueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        shopID = getActivity().getIntent().getIntExtra(IntentPutExtras.ID, 0);

        Spinner catalogueMenu = (Spinner) view.findViewById(R.id.catalogue_spinner_cat_list);
        alCaterory = CatalogueRetriver.getCategories(shopID);
        genericAdapter = new GenericAdapter(this.getActivity(), this, catalogueMenu, alCaterory, R.layout.list_item_catalogue_menu);


        fabCollapse = (FloatingActionButton) view.findViewById(R.id.catalogue_fab_collapse_catalogue);
        fabCollapse.setOnClickListener(this);

        alSubCaterory = CatalogueRetriver.getSubCategories(shopID, 1);

        for (int i = 0; i < alSubCaterory.size(); i++) {
            alisExpanded.add(false);
            hmCatalogueItems.put(alSubCaterory.get(i), null);
        }

        rvCatalogueList = (RecyclerView) view.findViewById(R.id.catalogue_rv_catalogue);
        rvGenericAdapter = new GenericAdapterRecyclerView(this.getContext(), this, rvCatalogueList, alSubCaterory, R.layout.list_item_view_header, 1, true);



        return view;
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
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID " + shopID);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID " + shopID);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + "Selected ShopID " + shopID);
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);

        if (genericAdapterRecyclerView == rvGenericAdapter)
            return new ListItemHeaderHolder(itemView, this);
        if (genericAdapterRecyclerView == rvItemsAdapter) {
            rvCatalogueList.scrollToPosition(scrollState);
            return new ItemViewHolder(itemView, this);
        }

        return null;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {


        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);

        if (genericAdapterRecyclerView == rvGenericAdapter) {
            SubCategoryItem subCat = (SubCategoryItem) list.get(position);
            final ListItemHeaderHolder viewHolder = (ListItemHeaderHolder) holder;
            viewHolder.tvItemName.setText(subCat.getCategoryName());
        } else if (genericAdapterRecyclerView == rvItemsAdapter) {
            Item item = (Item) list.get(position);
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.tvItemName.setText("" + item.getItem_name());
            viewHolder.tvNoOfItems.setText("" + item.getItem_count());
            viewHolder.tvQuantity.setText("" + item.getItem_quantity() + " " + item.qtTypeInString(item.getItem_form()));
            viewHolder.tvCost.setText("" + item.getActual_cost());

        }
    }

    @Override
    public void onRVClick(View view, int position, Boolean state) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID + " State : " + state);
    }

    @Override
    public void onRVLongClick(View view, int position) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);
    }

    @Override
    public void onRVExpand(View view, ArrayList list, int position, View vPrevExpanded) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());

        rvItemsList = (RecyclerView) view.findViewById(R.id.header_rv_items_list);
        vCurrentExpanded = view;
        this.vPrevExpanded = vPrevExpanded;
        ImageView ivCollapseState = null;
        RecyclerView rvPrevExpanded = null;

        scrollState = position;
        if (rvItemsList != null) {
            Log.i(AppState.TAG, "Found view");
            if (alisExpanded.get(position) == false) {
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());

                alisExpanded.set(position, true);
                ArrayList<Item> alItemsList = hmCatalogueItems.get(alSubCaterory.get(position));
                if (alItemsList == null) {
                    alItemsList = CatalogueRetriver.getItems(shopID, selectedCatagory, position);
                    hmCatalogueItems.put(alSubCaterory.get(position), alItemsList);
                }
                ivCollapseState = (ImageView) view.findViewById(R.id.header_iv_indicator);
                ivCollapseState.setImageResource(R.drawable.ic_header_expanded_arrow);
                rvItemsAdapter = new GenericAdapterRecyclerView(this.getContext(), this, rvItemsList, alItemsList, R.layout.list_item_catalogue_menu_card_view, 3, false);
                for (int i = 0; i < alisExpanded.size(); i++)
                    if (i != position)
                        alisExpanded.set(i, false);
            } else {
                ArrayList<SubCategoryItem> alEmptySubCaterory = new ArrayList<>();
                if (vPrevExpanded != null) {
                    ivCollapseState = (ImageView) vPrevExpanded.findViewById(R.id.header_iv_indicator);
                    rvPrevExpanded = (RecyclerView) vPrevExpanded.findViewById(R.id.header_rv_items_list);
                    ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                    new GenericAdapterRecyclerView(this.getContext(), this, rvPrevExpanded, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
                    alisExpanded.set(position, false);
                }
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());
            }

            if (vPrevExpanded != null && vPrevExpanded != view) {
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());

                ivCollapseState = (ImageView) vPrevExpanded.findViewById(R.id.header_iv_indicator);
                rvPrevExpanded = (RecyclerView) vPrevExpanded.findViewById(R.id.header_rv_items_list);
                ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                ArrayList<SubCategoryItem> alEmptySubCaterory = new ArrayList<>();
                new GenericAdapterRecyclerView(this.getContext(), this, rvPrevExpanded, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
            }
        } else
            Log.i(AppState.TAG, "Found not view");

    }

   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catalogue_fab_collapse_catalogue: {
                if (rvItemsList != null) {
                    for (int i = 0; i < alisExpanded.size(); i++)
                        alisExpanded.set(i, false);

                    ImageView ivCollapseState = (ImageView) vCurrentExpanded.findViewById(R.id.header_iv_indicator);
                    ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                    ArrayList<SubCategoryItem> alEmptySubCaterory = new ArrayList<>();
                    new GenericAdapterRecyclerView(this.getContext(), this, rvItemsList, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
                    rvItemsList = null;
                }
                break;
            }
        }
    }
}
