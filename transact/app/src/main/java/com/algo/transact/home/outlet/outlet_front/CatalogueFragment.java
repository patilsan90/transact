package com.algo.transact.home.outlet.outlet_front;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_beans.SubCategory;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
import com.algo.transact.home.outlet.data_retrivals.CatalogueRetriver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment implements IGenericAdapterRecyclerView {


   // ArrayList<Category> alCaterory;
    HashMap<SubCategory, ArrayList<Item>> hmCatalogueItems = new HashMap<>();
    private int shopID;
    private int selectedCatagory;
  //  private GenericAdapter genericAdapter;
    private GenericAdapterRecyclerView rvSubCategoryAdapter;
    private GenericAdapterRecyclerView rvItemsAdapter;
    private RecyclerView rvCatalogueList;
    private RecyclerView rvItemsList;
    private View vPrevExpanded;
    private View vCurrentExpanded;
    private ArrayList<Boolean> alisExpanded = new ArrayList<>();
    private ArrayList<SubCategory> alSubCaterory;
    private int scrollState;
    private Outlet outlet;
    private TextView tvcataloguePlaceholder;
    private Cart currentCart;
    private LocateCategoriesDialogue locateCategoriesDialogue;

    // ProgressDialog catalogueItemsLoadingdialog;
    public CatalogueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        shopID = getActivity().getIntent().getIntExtra(IntentPutExtras.ID, 0);
        outlet  = (Outlet) getActivity().getIntent().getSerializableExtra(IntentPutExtras.OUTLET_OBJECT);

        currentCart = CartsFactory.getInstance(getActivity()).getCart(outlet);

       // Spinner catalogueMenu = (Spinner) view.findViewById(R.id.catalogue_spinner_cat_list);

       // alCaterory = CatalogueRetriver.getCategories(shopID);
       // genericAdapter = new GenericAdapter(this.getActivity(), this, catalogueMenu, alCaterory, R.layout.list_item_catalogue_menu);

        rvCatalogueList = (RecyclerView) view.findViewById(R.id.catalogue_rv_catalogue);
        tvcataloguePlaceholder = (TextView) view.findViewById(R.id.catalogue_tv_placeholder);

       // catalogueItemsLoadingdialog = ProgressDialog.show(getActivity(), "Catalogue", "Loading...", true);
        locateCategoriesDialogue = new LocateCategoriesDialogue(getActivity());
        return view;
    }

void updateSubCategory(int category)
    {
        if(selectedCatagory != category) {
            selectedCatagory = category;
            alSubCaterory = CatalogueRetriver.getSubCategories(shopID, selectedCatagory);

            if(alSubCaterory.size()==0)
            {
                rvCatalogueList.setVisibility(View.GONE);
                tvcataloguePlaceholder.setVisibility(View.VISIBLE);
            }
            else
            {
                tvcataloguePlaceholder.setVisibility(View.GONE);
                rvCatalogueList.setVisibility(View.VISIBLE);
            }

            initializeHashMap(alSubCaterory);

            rvSubCategoryAdapter = new GenericAdapterRecyclerView(this.getContext(), this, rvCatalogueList, alSubCaterory, R.layout.list_item_view_header, 1, true);
        }
    }

void initializeHashMap(ArrayList<SubCategory> sc)
    {
        for (int i = 0; i < sc.size(); i++) {
            alisExpanded.add(false);
            hmCatalogueItems.put(sc.get(i), null);
        }
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
   /*     Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);
*/
        if (genericAdapterRecyclerView == rvSubCategoryAdapter)
            return new ListItemHeaderHolder(itemView, this);
        if (genericAdapterRecyclerView == rvItemsAdapter) {
            rvCatalogueList.scrollToPosition(scrollState);
            return new ItemViewHolder(itemView, this);
        }

        return null;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {

 /*      Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);
*/
        if (genericAdapterRecyclerView == rvSubCategoryAdapter) {
            final SubCategory subCat = (SubCategory) list.get(position);
            final ListItemHeaderHolder viewHolder = (ListItemHeaderHolder) holder;
            viewHolder.tvItemName.setText(subCat.getSubCategoryName());

            if(subCat.getCategoryLocation() == null)
                viewHolder.ivLocation.setVisibility(View.INVISIBLE);
            else if(subCat.getCategoryLocation().trim().length()==0)
                viewHolder.ivLocation.setVisibility(View.INVISIBLE);
            else {
                viewHolder.ivLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locateCategoriesDialogue.showDialogue(subCat.getSubCategoryName(), subCat.getCategoryLocation());
                    }
                });
            }
            Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID +" SubCategoryAdapter");
        } else {
            if (genericAdapterRecyclerView == rvItemsAdapter) {
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID +" ItemsAdapter");
                final Item item = (Item) list.get(position);
                final ItemViewHolder viewHolder = (ItemViewHolder) holder;
                viewHolder.tvItemName.setText("" + item.getItem_name());
                int cartSize= currentCart.getCartList().size();
                int total_items_in_cart=0;

                for(int i =0; i< cartSize;i++)
                {
                    if(currentCart.getCartList().get(i).getItem_id() == item.getItem_id())
                    {
                        total_items_in_cart = currentCart.getCartList().get(i).getItem_count();
                    }
                }

                viewHolder.tvNoOfItems.setText("" + total_items_in_cart);
                viewHolder.tvQuantity.setText("" + item.getItem_quantity() + " " + item.qtTypeInString(item.getItem_form()));
                viewHolder.tvCost.setText("" + item.getActual_cost());
                viewHolder.ivIncrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cart cart = CartsFactory.getInstance(getActivity()).getCart(outlet);
                        int count = item.getItem_count();
                        boolean flag = false;
                        ArrayList<Item> cartlist = cart.getCartList();
                        for (int i=0;i<cartlist.size();i++)
                        {
                            if(cartlist.get(i).getItem_id()==item.getItem_id())
                            {
                                count++;
                                item.setItem_count(count);
                                cartlist.get(i).setItem_count(count);
                                flag = true;
                                break;
                            }
                        }

                        if(flag == false)
                        {
                            count++;
                            item.setItem_count(count);
                            cart.getCartList().add(item);
                        }

                        OutletFront.cartAdapter.notifyDataSetChanged();
                        viewHolder.tvNoOfItems.setText("" + count);
                    }
                });
                viewHolder.ivDecrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cart cart = CartsFactory.getInstance(getActivity()).getCart(outlet);
                        int count = item.getItem_count();
                        ArrayList<Item> cartlist = cart.getCartList();
                        for (int i=0;i<cartlist.size();i++)
                        {
                            if(cartlist.get(i).getItem_id()==item.getItem_id())
                            {
                                if(count > 1)
                                {
                                    count--;
                                    item.setItem_count(count);
                                    cartlist.get(i).setItem_count(count);
                                }
                                else if(count == 1)
                                {
                                    count--;
                                    item.setItem_count(0);
                                    cartlist.remove(i);
                                }
                                OutletFront.cartAdapter.notifyDataSetChanged();
                                viewHolder.tvNoOfItems.setText("" + count);
                                break;
                            }
                        }



                    }
                });

            }
        }
    }

    @Override
    public void rvListUpdateCompleteNotification(ArrayList list, GenericAdapterRecyclerView genericAdapterRecyclerView) {
   //OutletFront.getInstance().updateCartTotal();

    }

    @Override
    public void onRVClick(View view, int position, Boolean state) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID + " State : " + state);
    }

    @Override
    public void onRVLongClick(View view, int position) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID);
    }

    ArrayList<View> expandedViewList = new ArrayList<>();
    @Override
    public void onRVExpand(View view, ArrayList list, int position, View vPrevExpanded) {
        Log.i(AppConfig.TAG, " --------------------------------------------------------  Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Selected ShopID " + shopID + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());

        rvItemsList = (RecyclerView) view.findViewById(R.id.header_rv_items_list);
        vCurrentExpanded = view;
        this.vPrevExpanded = vPrevExpanded;
        ImageView ivCollapseState = null;
        RecyclerView rvPrevExpanded = null;

        scrollState = position;

            if (alisExpanded.get(position) == false)
            {
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());
                expandedViewList.add(view);
                alisExpanded.set(position, true);
                ArrayList<Item> alItemsList = hmCatalogueItems.get(alSubCaterory.get(position));
                if (alItemsList == null) {
                    Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                    }.getClass().getEnclosingMethod().getName() + " Category :: "+selectedCatagory +" Sub Category:: "+alSubCaterory.get(position).getSubCategoryID());
                    alItemsList = CatalogueRetriver.getCatalogueItems(shopID, selectedCatagory,alSubCaterory.get(position).getSubCategoryID());
                    hmCatalogueItems.put(alSubCaterory.get(position), alItemsList);
                }
                ivCollapseState = (ImageView) view.findViewById(R.id.header_iv_indicator);
                ivCollapseState.setImageResource(R.drawable.ic_header_expanded_arrow);
                rvItemsAdapter = new GenericAdapterRecyclerView(this.getContext(), this, rvItemsList, alItemsList, R.layout.list_item_catalogue_menu_card_view, 3, false);

            }
            else
            {
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + " Line Number: " + new Throwable().getStackTrace()[0].getLineNumber());
                alisExpanded.set(position, false);
                ArrayList<SubCategory> alEmptySubCaterory = new ArrayList<>();
                ivCollapseState = (ImageView) view.findViewById(R.id.header_iv_indicator);
                ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                new GenericAdapterRecyclerView(this.getContext(), this, rvItemsList, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);

                int size =expandedViewList.size();
                for (int i=0;i< size;i++)
                {
                    if(view == expandedViewList.get(i))
                        {
                            expandedViewList.remove(i);
                            break;
                        }
                }
            }
    }

    public void onLostFocusNotification()
    {
        if (rvItemsList != null) {
            int size =expandedViewList.size()-1;
            for (int i = 0; i < size; i++)
                alisExpanded.set(i, false);


            for (int i=0;i< size;i++)
            {
                Log.i(AppConfig.TAG, "Removing Item --------------------------------------------------");

                View rView = expandedViewList.get(i);
                ImageView ivCollapseState = (ImageView) rView.findViewById(R.id.header_iv_indicator);
                RecyclerView rvList = (RecyclerView) rView.findViewById(R.id.header_rv_items_list);
                ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                ArrayList<SubCategory> alEmptySubCaterory = new ArrayList<>();
                new GenericAdapterRecyclerView(this.getContext(), this, rvList, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
            }

            while(expandedViewList.size()>1)
            {
                Log.i(AppConfig.TAG, "Removing Item ----------------------------------------------fff----");
                expandedViewList.remove(0);
            }
        }

    }
    public void onFocusNotification()
    {
        if(rvItemsAdapter!=null)
            rvItemsAdapter.notifyDataSetChanged();
    }

public void collapseList(View view)
    {
        if (rvItemsList != null) {
            for (int i = 0; i < alisExpanded.size(); i++)
                alisExpanded.set(i, false);

            int size =expandedViewList.size();
            for (int i=0;i< size;i++)
            {
                Log.i(AppConfig.TAG, "Removing Item --------------------------------------------------");

                View rView = expandedViewList.get(i);
                ImageView ivCollapseState = (ImageView) rView.findViewById(R.id.header_iv_indicator);
                RecyclerView rvList = (RecyclerView) rView.findViewById(R.id.header_rv_items_list);
                ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
                ArrayList<SubCategory> alEmptySubCaterory = new ArrayList<>();
                new GenericAdapterRecyclerView(this.getContext(), this, rvList, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
                //expandedViewList.remove(0);
               // rvItemsList = null;

            }

            while(expandedViewList.size()!=0)
            {
                Log.i(AppConfig.TAG, "Removing Item ----------------------------------------------fff----");
                expandedViewList.remove(0);
                // rvItemsList = null;

            }

/*
            ImageView ivCollapseState = (ImageView) vCurrentExpanded.findViewById(R.id.header_iv_indicator);
            ivCollapseState.setImageResource(R.drawable.ic_header_collapsed_arrow);
            ArrayList<SubCategory> alEmptySubCaterory = new ArrayList<>();
            new GenericAdapterRecyclerView(this.getContext(), this, rvItemsList, alEmptySubCaterory, R.layout.list_item_view_header, 1, false);
            rvItemsList = null;
*/
        }
    }

}
