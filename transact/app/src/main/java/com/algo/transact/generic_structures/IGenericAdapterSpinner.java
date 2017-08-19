package com.algo.transact.generic_structures;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sandeep on 5/8/17.
 */

public interface IGenericAdapterSpinner extends AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    public View addViewItemToList(View view, Object listItem, int index);

}
