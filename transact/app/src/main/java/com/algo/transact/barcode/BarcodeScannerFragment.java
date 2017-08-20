package com.algo.transact.barcode;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.security.Policy;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarcodeScannerFragment extends Fragment {

    // Permission request codes need to be < 256

    IQRResult iQRResult;
    private SurfaceView cameraView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    private String previousScanResult="";
    public BarcodeScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barcode_scanner, container, false);
        cameraView = (SurfaceView) view.findViewById(R.id.barcode_scanner_sv_camera_view);


/*
        Camera cam = Camera.open();
        Policy.Parameters p = cam.getParameters();
        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
*/

        barcodeDetector =
                new BarcodeDetector.Builder(this.getActivity())
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        view.findViewById(R.id.barcode_scanner_sv_camera_view).getLayoutParams().height = height;
        view.findViewById(R.id.barcode_scanner_sv_camera_view).getLayoutParams().width = width;

        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

        cameraSource = new CameraSource
                .Builder(this.getActivity(), barcodeDetector)
                .setRequestedPreviewSize(width, height)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                    final String[] permissions = new String[]{android.Manifest.permission.CAMERA};
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), permissions, Permissions.RC_HANDLE_CAMERA_PERM);
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

        return view;
    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        Log.w(AppState.TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, Permissions.RC_HANDLE_CAMERA_PERM);
            //requestPermissions(permissions,Permissions.RC_HANDLE_CAMERA_PERM);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iQRResult = (IQRResult) getActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(AppState.TAG,"onRequestPermissionsResult fragment");
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
                        if (ActivityCompat.checkSelfPermission(getActivity(),
                                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), permissionss, Permissions.RC_HANDLE_CAMERA_PERM);
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
