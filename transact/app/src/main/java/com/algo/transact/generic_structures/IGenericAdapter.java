package com.algo.transact.generic_structures;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public interface IGenericAdapter extends AdapterView.OnItemClickListener {

    View addViewItemToList(View view, Object listItem, int index);

    void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter);
}
