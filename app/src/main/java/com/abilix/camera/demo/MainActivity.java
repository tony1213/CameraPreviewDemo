package com.abilix.camera.demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    private FrameLayout preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkCameraHardware(this)) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPreview = new CameraPreview(this, mCamera);
            preview.addView(mPreview);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Log.e("Camera", "onPreviewFrame():" + bytes.length);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onDestroy();
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
}
