package com.algo.transact.generic_structures;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sandeep on 5/8/17.
 */

public interface IGenericAdapter extends AdapterView.OnItemClickListener{

    public View addViewItemToList(View view, Object listItem);

}
