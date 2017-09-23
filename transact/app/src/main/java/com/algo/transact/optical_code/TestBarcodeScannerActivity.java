package com.algo.transact.optical_code;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;

public class TestBarcodeScannerActivity extends AppCompatActivity implements IQRResult {

    CodeScannerFragment codeScannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_barcode_scanner);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        codeScannerFragment = new CodeScannerFragment();
        fragmentTransaction.add(R.id.cam_test, codeScannerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void codeScannerResult(String barcodeResult) {
        Log.i(AppState.TAG, "in ScannerResult " + barcodeResult);
        Intent intent = new Intent();
       // intent.putExtra(IntentRequestResponseType.SCANNER_RESPONSE, barcodeResult);
        setResult(RESULT_OK, intent);
        finish();
    }

}
