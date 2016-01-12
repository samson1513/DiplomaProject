package com.example.samson.diplomaproject.fragments;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.global.Constants;
import com.example.samson.diplomaproject.utils.BitmapManager;
import com.example.samson.diplomaproject.utils.EffectsUtil;
import com.example.samson.diplomaproject.utils.FileManager;

import java.io.File;

public class LearningFragment extends BaseFragment<MainActivity> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_learning;
    }

    private void prepareImages(int radius) {
        FileManager.createWorkDirectory(FileManager.TypeImage.Blured);
        String[] pathsImages = new File(FileManager.getPathDirectory(FileManager.TypeImage.Base)).list();
        Bitmap bitmap;
        if (pathsImages.length == Constants.COUNT_SAMPLES) {
            for (int i = 1; i <= Constants.COUNT_SAMPLES; ++i) {
                bitmap = EffectsUtil.doGaussianBlur(BitmapManager.getBitmap(pathsImages[i - 1]), radius, getHostActivity());
                FileManager.saveImage(String.valueOf(i), FileManager.TypeImage.Blured, bitmap);
            }
        } else {
            Toast.makeText(getHostActivity(), "Need to add samples", Toast.LENGTH_SHORT).show();
        }
    }

}
