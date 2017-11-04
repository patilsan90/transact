package com.algo.transact.generic_structures;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;

import java.util.ArrayList;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class GenericAdapterRecyclerView extends RecyclerView.Adapter {

    private ArrayList list;
    private RecyclerView listView;
    private Spinner spinnerView;

    private int listViewItemId;
    private final IGenericAdapterRecyclerView listener;
    private IGenericAdapterSpinner spinnerListener;

    private ArrayList<Boolean> alExpandState;
    private Context mContext;
    private boolean isExpandableList;

    public GenericAdapterRecyclerView(Context mContext, final IGenericAdapterRecyclerView listener, RecyclerView listView, final ArrayList list, int listViewItemId, int noOfColumns, final boolean isExpandableList) {
        this.mContext = mContext;
        this.list = list;
        this.listView = listView;
        this.listViewItemId = listViewItemId;
        this.listener = listener;
        if (noOfColumns > 0) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, noOfColumns);
            this.listView.setLayoutManager(mLayoutManager);
        }
        this.listView.addItemDecoration(new GridSpacingItemDecoration(noOfColumns, dpToPx(0), true));
        this.listView.setItemAnimator(new DefaultItemAnimator());
        this.listView.setAdapter(this);
        this.isExpandableList = isExpandableList;
        alExpandState = new ArrayList<Boolean>();
        for (int i = 0; i < list.size(); i++)
            alExpandState.add(false);

        this.listView.addOnItemTouchListener(new RecyclerTouchListener(mContext, listView, new IClickListener() {

            @Override
            public void onClick(View view, int position) {

                listener.onRVClick(view, position, alExpandState.get(position));

           /*  if (isExpandableList) {
                    alExpandState.set(position, alExpandState.get(position) ? false : true);
                    if (alExpandState.get(position))
                        listener.onRVExpand(view, list, position);
                } */
            }

            @Override
            public void onLongClick(View view, int position) {
                listener.onRVLongClick(view, position);
            }
        }));

        Log.i(AppConfig.TAG, "::GARV:: Object creation of GenericRecyclerView Adapter from " + mContext.getClass().getSimpleName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(listViewItemId, parent, false);

        return listener.addRecyclerViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        listener.bindViewHolder(holder, list, position, this);
        Log.i(AppConfig.TAG, "In onBindViewHolder " + position);
        if (position == (list.size() - 1))
            listener.rvListUpdateCompleteNotification(list, this);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    public void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = mContext.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}