package com.example.samson.diplomaproject.fragments;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.utils.FileManager;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends BaseFragment<MainActivity> implements View.OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback {

    private ImageView mPhoto, mEdit, mBack;
    private TextView mCancel;
    private SurfaceView mPreview;

    private Camera camera;
    private File mImageFile;

    private boolean isPreviewFrame = false;
    private byte[] arrayImage;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findUI();
        setClickListeners(this, mPhoto, mEdit, mBack, mCancel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_camera;
    }

    private void findUI(){
        mPhoto = $(R.id.ivPhoto_AC);
        mEdit = $(R.id.ivToEdit_AC);
        mBack = $(R.id.ivBack_AC);
        mCancel = $(R.id.llCancel);
        mPreview = $(R.id.svPreview_AC);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkCameraHardware()) {
            settingPreview();
        } else {
            mCancel.setText("You have not camera");
        }
    }

    private void settingPreview(){
        camera = Camera.open();
        mPreview.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d("error", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private boolean checkCameraHardware() {
        return getHostActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivBack_AC:
                FragmentReplacer.popSupBackStack(getHostActivity());
                break;
            case R.id.ivPhoto_AC:
                camera.takePicture(null, null, this);
                break;
            case R.id.ivToEdit_AC:
                startEditPhoto();
                break;
            case R.id.llCancel:
                clickCancel();
                break;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        isPreviewFrame = true;
        arrayImage = data.clone();
    }

    private void clickCancel(){
        if(isPreviewFrame){
            camera.startPreview();
        }
    }

    private void startEditPhoto(){
        if(isPreviewFrame) {
            saveImage();
            openEditor();
        }
    }

    private void saveImage(){
        mImageFile = FileManager.saveImage(null, FileManager.TypeImage.Photo, arrayImage);
    }

    private void openEditor(){
        FragmentReplacer.replaceFragment(
                getHostActivity(),
                EditorFragment.newInstance(String.valueOf(mImageFile.getAbsoluteFile()))
        );
    }
}
