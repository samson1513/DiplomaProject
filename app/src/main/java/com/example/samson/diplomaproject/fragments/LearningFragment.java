package com.example.samson.diplomaproject.fragments;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.global.Constants;
import com.example.samson.diplomaproject.utils.BitmapManager;
import com.example.samson.diplomaproject.utils.EffectsUtil;
import com.example.samson.diplomaproject.utils.FileManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LearningFragment extends BaseFragment<MainActivity> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_learning;
    }

    private void prepareImages() {
        int radius = 3;
        FileManager.createWorkDirectory(FileManager.TypeImage.Blured);
        String pathBaseDir = FileManager.getPathDirectory(FileManager.TypeImage.Base);
        File[] images = new File(pathBaseDir).listFiles();
        File imageBluredFile;
        for (int i = 1; i <= Constants.COUNT_EXAMPLES; ++i) {
            Bitmap bitmap = BitmapManager.getCompressedBitmap(256, 1f, images[i - 1].getAbsolutePath());

            bitmap = EffectsUtil.doGaussianBlur(bitmap, radius);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] pixels = stream.toByteArray();

            imageBluredFile = FileManager.createImageFile(String.valueOf(i), FileManager.TypeImage.Base);

            try {
                FileOutputStream outputStream = new FileOutputStream(imageBluredFile);
                outputStream.write(pixels);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Learning", "failed to save image");
            }
        }
    }

}
