package com.algo.transact.barcode;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CodeScannerActivity extends AppCompatActivity implements IQRResult{

    // Permission request codes need to be < 256

    IQRResult iQRResult;
    private SurfaceView cameraView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private String previousScanResult="";

    private CodeScannerActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scanner);

        cameraView = (SurfaceView) findViewById(R.id.code_scanner_sv_camera_view);
        activity = this;
        iQRResult = (IQRResult) this;
        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        findViewById(R.id.code_scanner_sv_camera_view).getLayoutParams().height = height;
        findViewById(R.id.code_scanner_sv_camera_view).getLayoutParams().width = width;

        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(width, height)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                final String[] permissions = new String[]{android.Manifest.permission.CAMERA};
                if (ActivityCompat.checkSelfPermission(activity,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, permissions, Permissions.RC_HANDLE_CAMERA_PERM);
                }
                else
                {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    if (!previousScanResult.equals(barcodes.valueAt(0).displayValue)) {
                        iQRResult.codeScannerResult(barcodes.valueAt(0).displayValue);
                    }
                    previousScanResult = barcodes.valueAt(0).displayValue;
                }
            }
        });
    }


    @Override
    public void codeScannerResult(String barcodeResult) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Intent intent = new Intent();
        int outletID = getIntent().getIntExtra(IntentPutExtras.ID, 0);
        final BarcodeDetails codeObj = BarcodeDetails.getCodeObject(barcodeResult);
        if(outletID!=0 && outletID != codeObj.getOutletID())
        {
            Log.i(AppState.TAG, "Invalid Code, this product doesnt belong to this shop");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(activity, "Invalid Code, this product belongs to different "+codeObj.getOutletType(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                }
            });
            return;
        }
        intent.putExtra(IntentPutExtras.ID, outletID);
        intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.CODE_OBJECT);
        intent.putExtra(IntentPutExtras.CODE_OBJECT, codeObj);

      //  intent.putExtra(IntentPutExtras.NEW_ITEM_DATA, newItem);
        setResult(IntentResultCode.TRANSACT_RESULT_OK, intent);
        Log.i(AppState.TAG, "QRCOde Details: "+codeObj);
        finish();

    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        Log.w(AppState.TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_CAMERA_PERM);
            //requestPermissions(permissions,Permissions.RC_HANDLE_CAMERA_PERM);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(AppState.TAG,"onRequestPermissionsResult fragment");
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        /* TODO
        * Call this fragments method from Actual activity onRequestPermissionsResult method, else it wont get called.
        * */
        switch (requestCode)
        {
            case Permissions.RC_HANDLE_CAMERA_PERM:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i(AppState.TAG,"Setting camera resources");
                    try {
                        final String[] permissionss = new String[]{android.Manifest.permission.CAMERA};
                        if (ActivityCompat.checkSelfPermission(this,
                                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, permissionss, Permissions.RC_HANDLE_CAMERA_PERM);
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e(AppState.TAG, "CAMERA SOURCE " + ie.getMessage());
                    }
                }
            }
        }
    }


}