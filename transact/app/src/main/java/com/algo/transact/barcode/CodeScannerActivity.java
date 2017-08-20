package com.algo.transact.barcode;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.algo.transact.R;

public class CodeScannerActivity extends AppCompatActivity implements IQRResult{

    private BarcodeScannerFragment barcodeScannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        barcodeScannerFragment = new BarcodeScannerFragment();
        fragmentTransaction.add(R.id.code_scanner_ll_qr_scanner, barcodeScannerFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void codeScannerResult(String barcodeResult) {

    }
}
