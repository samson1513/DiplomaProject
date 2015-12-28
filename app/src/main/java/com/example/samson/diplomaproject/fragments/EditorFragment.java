package com.example.samson.diplomaproject.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samson.ScriptC_mono;
import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.global.Constants;
import com.example.samson.diplomaproject.utils.FileExplorer;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditorFragment extends Fragment implements View.OnClickListener {

    private MainActivity mActivity;

    private ImageView mDeblur, mBinar, mSave, mRefresh;
    private ImageView mPhoto;

    private Bitmap mStartBitmap, mEndBitmap;

    private String mPath;

    public static EditorFragment newInstance(String _path){
        EditorFragment fragment = new EditorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE, _path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
        mPath = getArguments().getString(Constants.IMAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor,null);

        findUI(view);
        setListeners();
        getBaseBitmap();
        setStartImage();

        return view;
    }

    private void findUI(View _view) {
        mDeblur = (ImageView) _view.findViewById(R.id.ivDeblur);
        mBinar = (ImageView) _view.findViewById(R.id.ivMono);
        mSave = (ImageView) _view.findViewById(R.id.ivSave_AE);
        mRefresh = (ImageView) _view.findViewById(R.id.ivRefresh);

        mPhoto = (ImageView) _view.findViewById(R.id.ivPhoto_AE);
    }

    private void setListeners() {
        mDeblur.setOnClickListener(this);
        mBinar.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
    }

    private void getBaseBitmap() {
        mStartBitmap = BitmapFactory.decodeFile(mPath);
    }

    private void setStartImage() {
        mEndBitmap = mStartBitmap.copy(Bitmap.Config.ARGB_8888, true);
        mPhoto.setImageBitmap(mEndBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivDeblur:
                test();
                break;
            case R.id.ivMono:
                binarizateImage();
                break;
            case R.id.ivRefresh:
                setStartImage();
                break;
            case R.id.ivSave_AE:
                onSave();
                break;
        }
    }

    private void binarizateImage() {
        RenderScript mRS = RenderScript.create(mActivity);
        Allocation mInAllocation = Allocation.createFromBitmap(
                mRS,
                mEndBitmap,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT
        );
        Allocation mOutAllocation = Allocation.createTyped(mRS, mInAllocation.getType());
        ScriptC_mono mScript = new ScriptC_mono(mRS);
        mScript.forEach_root(mInAllocation, mOutAllocation);
        mOutAllocation.copyTo(mEndBitmap);

        mPhoto.setImageBitmap(mEndBitmap);
    }

    private void onSave() {
        saveImage();
        openMainMenu();
    }

    private void saveImage() {
        File mImageFile = FileExplorer.createImageFile();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mEndBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] arrayImage = stream.toByteArray();

        try {
            FileOutputStream outputStream = new FileOutputStream(mImageFile);
            outputStream.write(arrayImage);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Project", "failed to save directory");
        }
    }

    private void openMainMenu() {
        FragmentReplacer.replaceFragment(mActivity, new MenuFragment());
    }

    private void test() {
        int[][] filter = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0},
        };
        int offset = 0;
        float div = 1;
        int width = mEndBitmap.getWidth();
        int height = mEndBitmap.getHeight();
        int w1 = --width, h1 = --height;
        int pixel, r, g, b;
        int[][] outImage = new int[width][height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {

                r = 0;
                g = 0;
                b = 0;
                for(int j = 0; j < filter.length; ++j){
                    int xv = Math.min(Math.max(x - 1 + j, 0), w1);
                    for(int i = 0; i < filter.length; ++i){
                        int yv = Math.min(Math.max(y - 1 + i, 0), h1);

                        pixel = mEndBitmap.getPixel(xv, yv);

                        r += ((pixel >> 16) & 0xFF) * filter[i][j];
                        g += ((pixel >> 8) & 0xFF) * filter[i][j];
                        b += ((pixel) & 0xFF) * filter[i][j];

                    }
                }

                r = (int)(r / div) + offset;
                g = (int)(g / div) + offset;
                b = (int)(b / div) + offset;

                r = (r > 255)? 255 : ((r < 0)? 0:r);
                g = (g > 255)? 255 : ((g < 0)? 0:g);
                b = (b > 255)? 255 : ((b < 0)? 0:b);

                outImage[x][y] = Color.argb(0xFF,r,g,b);
            }
        }

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                mEndBitmap.setPixel(x, y, outImage[x][y]);
            }
        }

        mPhoto.setImageBitmap(mEndBitmap);
    }

    private int getColor(int pixel) {
        int r = (pixel >> 16) & 0xFF;
        int g = (pixel >> 8) & 0xFF;
        int b = (pixel) & 0xFF;
        return Color.argb(0xFF,r,g,b);
    }

}
