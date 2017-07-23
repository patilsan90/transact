package com.algo.transact.barcode;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;

public class TestBarcodeScannerActivity extends AppCompatActivity implements IQRResult {

    BarcodeScannerFragment barcodeScannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_barcode_scanner);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        barcodeScannerFragment = new BarcodeScannerFragment();
        fragmentTransaction.add(R.id.cam_test, barcodeScannerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void scannerResult(String barcodeResult) {
        Log.i(AppState.TAG, "in ScannerResult " + barcodeResult);
        Intent intent = new Intent();
        intent.putExtra(BarcodeRequestType.SCANNER_RESPONSE, barcodeResult);
        setResult(RESULT_OK, intent);
        finish();
    }

}
