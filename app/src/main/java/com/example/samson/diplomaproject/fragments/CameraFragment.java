package com.example.samson.diplomaproject.fragments;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.utils.FileExplorer;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraFragment extends Fragment implements View.OnClickListener, SurfaceHolder.Callback, Camera.PictureCallback {

    private MainActivity mActivity;

    private ImageView mPhoto, mEdit, mBack;
    private TextView mCancel;
    private SurfaceView mPreview;

    private Camera camera;
    private File mImageFile;

    private boolean isPreviewFrame = false;
    private byte[] arrayImage;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera,null);

        findUI(view);
        setListeners();

        return view;
    }

    private void findUI(View _view){
        mPhoto = (ImageView) _view.findViewById(R.id.ivPhoto_AC);
        mEdit = (ImageView) _view.findViewById(R.id.ivToEdit_AC);
        mBack = (ImageView) _view.findViewById(R.id.ivBack_AC);
        mCancel = (TextView) _view.findViewById(R.id.llCancel);
        mPreview = (SurfaceView) _view.findViewById(R.id.svPreview_AC);
    }

    private void setListeners(){
        mPhoto.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCancel.setOnClickListener(this);
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
        return mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivBack_AC:
                FragmentReplacer.popSupBackStack(mActivity);
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
        mImageFile = FileExplorer.createImageFile();

        try {
            FileOutputStream outputStream = new FileOutputStream(mImageFile);
            outputStream.write(arrayImage);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Project", "failed to save directory");
        }

    }

    private void openEditor(){
        FragmentReplacer.replaceFragment(
                mActivity,
                EditorFragment.newInstance(String.valueOf(mImageFile.getAbsoluteFile()))
        );
    }
}
