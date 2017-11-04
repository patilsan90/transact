package com.algo.transact.home.outlet.outlet_front;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.optical_code.OpticalCode;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_retrivals.ShopsRetriver;


public class ItemCountSelectionActivity extends AppCompatActivity implements View.OnClickListener{

    String itemID;
    int shopID;
    Item newItem;
    private TextView tvItemName;
    private TextView tvActualCost;
    private TextView tvDiscountedCost;
    private TextView tvTotalCost;
    private TextView tvTotalItems;
    private ImageButton ibIncreaseItems;
    private ImageButton ibDecreaseItems;

    private Button count_selection_item1;
    private Button count_selection_item2;
    private Button count_selection_item3;
    private Button count_selection_item4;
    private Button count_selection_item5;
    private Button count_selection_item6;
    private Button count_selection_item7;
    private Button count_selection_item8;
    private Button count_selection_item9;
    private Button count_selection_item10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sas_item_count_selection);

        tvItemName=(TextView) findViewById(R.id.count_selection_item_name);
        tvActualCost =(TextView) findViewById(R.id.count_selection_actual_cost);
        tvDiscountedCost =(TextView) findViewById(R.id.count_selection_discounted_cost);
        tvTotalCost =(TextView) findViewById(R.id.count_selection_total_cost);
        tvTotalItems =(TextView) findViewById(R.id.count_selection_total_items);
        tvTotalItems.setText(""+1);
        ibIncreaseItems =(ImageButton) findViewById(R.id.count_selection_increase_item_count);
        ibIncreaseItems.setOnClickListener(this);
        ibDecreaseItems =(ImageButton) findViewById(R.id.count_selection_decrease_item_count);
        ibDecreaseItems.setOnClickListener(this);

        count_selection_item1 =(Button) findViewById(R.id.count_selection_item1);
        count_selection_item1.setOnClickListener(this);
        count_selection_item2 =(Button) findViewById(R.id.count_selection_item2);
        count_selection_item2.setOnClickListener(this);
        count_selection_item3 =(Button) findViewById(R.id.count_selection_item3);
        count_selection_item3.setOnClickListener(this);
        count_selection_item4 =(Button) findViewById(R.id.count_selection_item4);
        count_selection_item4.setOnClickListener(this);
        count_selection_item5 =(Button) findViewById(R.id.count_selection_item5);
        count_selection_item5.setOnClickListener(this);
        count_selection_item6 =(Button) findViewById(R.id.count_selection_item6);
        count_selection_item6.setOnClickListener(this);
        count_selection_item7 =(Button) findViewById(R.id.count_selection_item7);
        count_selection_item7.setOnClickListener(this);
        count_selection_item8 =(Button) findViewById(R.id.count_selection_item8);
        count_selection_item8.setOnClickListener(this);
        count_selection_item9 =(Button) findViewById(R.id.count_selection_item9);
        count_selection_item9.setOnClickListener(this);
        count_selection_item10 =(Button) findViewById(R.id.count_selection_item10);
        count_selection_item10.setOnClickListener(this);




        String dataType = getIntent().getStringExtra(IntentPutExtras.DATA_TYPE);

       if (dataType.equals(IntentPutExtras.CODE_OBJECT)) {
            Log.i(AppConfig.TAG, "ItemCountSelectionActivity Request Type:: " + IntentPutExtras.CODE_OBJECT);
           OpticalCode barcodeDetails = (OpticalCode) getIntent().getSerializableExtra(IntentPutExtras.CODE_OBJECT);
           /*
           itemID = getIntent().getStringExtra(IntentPutExtras.MODULE_ID);
           shopID = getIntent().getIntExtra(IntentPutExtras.ID,0);
            */
           itemID = barcodeDetails.getItemId();
           shopID = barcodeDetails.getOutletId();

           newItem = ShopsRetriver.getItemDetailsFromShop(shopID, itemID);

           tvItemName.setText(newItem.getItem_name());
           tvActualCost.setText("Actual Cost : "+newItem.getActual_cost());
           tvDiscountedCost.setText("Discounted Cost : "+newItem.getDiscounted_cost());
           tvTotalCost.setText("Total Cost : " + (newItem.getDiscounted_cost()));
       }
        else
       {
           Toast.makeText(this, "Error in calling sequence", Toast.LENGTH_SHORT).show();
           finish();
       }
    }

   public void itemSelected(View view)
    {
    //add item to cart here
       // Item item = ShopsRetriver.getItemDetailsFromShop(shopID, itemID);

        //AppConfig.getInstance().addCartItem(temporary_item);
        Log.e(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName()+" NewItem is null? -> "+(newItem==null));

        Intent intent = new Intent();
        intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.NEW_ITEM_DATA);
        intent.putExtra(IntentPutExtras.NEW_ITEM_DATA, newItem);
        setResult(IntentResultCode.TRANSACT_RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.count_selection_increase_item_count:
                newItem.increaseItem_count();
                break;
            case R.id.count_selection_decrease_item_count:
                if(newItem.getItem_count()>1)
                  newItem.decreaseItem_count();
                break;
            case R.id.count_selection_item1:
                newItem.setItem_count(1);
                break;
            case R.id.count_selection_item2:
                newItem.setItem_count(2);
                break;
            case R.id.count_selection_item3:
                newItem.setItem_count(3);
                break;
            case R.id.count_selection_item4:
                newItem.setItem_count(4);
                break;
            case R.id.count_selection_item5:
                newItem.setItem_count(5);
                break;
            case R.id.count_selection_item6:
                newItem.setItem_count(6);
                break;
            case R.id.count_selection_item7:
                newItem.setItem_count(7);
                break;
            case R.id.count_selection_item8:
                newItem.setItem_count(8);
                break;
            case R.id.count_selection_item9:
                newItem.setItem_count(9);
                break;
            case R.id.count_selection_item10:
                newItem.setItem_count(10);
                break;
        }

        tvTotalItems.setText(""+newItem.getItem_count());
        tvTotalCost.setText("Total Cost : " + (newItem.getDiscounted_cost()*newItem.getItem_count()));

    }
}
