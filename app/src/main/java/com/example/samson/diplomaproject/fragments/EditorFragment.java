package com.example.samson.diplomaproject.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.view.View;
import android.widget.ImageView;

import com.example.samson.ScriptC_mono;
import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.activities.MainActivity;
import com.example.samson.diplomaproject.base.BaseFragment;
import com.example.samson.diplomaproject.global.Constants;
import com.example.samson.diplomaproject.utils.EffectsUtil;
import com.example.samson.diplomaproject.utils.FileManager;
import com.example.samson.diplomaproject.utils.FragmentReplacer;

import java.io.File;

public class EditorFragment extends BaseFragment<MainActivity> implements View.OnClickListener {

    private ImageView mDeblur, mBinar, mMotion, mSave, mRefresh;
    private ImageView mPhoto;

    private Bitmap mStartBitmap, mEndBitmap;

    private String mPath;

    public static EditorFragment newInstance(String _path) {
        EditorFragment fragment = new EditorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.IMAGE, _path);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPath = getArguments().getString(Constants.IMAGE);

        findUI();
        setClickListeners(this, mDeblur, mBinar, mMotion, mSave, mRefresh);
        getBaseBitmap();
        setStartImage();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_editor;
    }

    private void findUI() {
        mDeblur = $(R.id.ivDeblur);
        mBinar = $(R.id.ivMono);
        mSave = $(R.id.ivSave_AE);
        mRefresh = $(R.id.ivRefresh);
        mPhoto = $(R.id.ivPhoto_AE);
        mMotion = $(R.id.ivMotion);
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
                mEndBitmap = EffectsUtil.doGaussianBlur(mStartBitmap, 3, getHostActivity());
                mPhoto.setImageBitmap(mEndBitmap);
                break;
            case R.id.ivMotion:
                mEndBitmap = EffectsUtil.doMotionBlur(mStartBitmap, 20, 1);
                mPhoto.setImageBitmap(mEndBitmap);
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
        RenderScript mRS = RenderScript.create(getHostActivity());
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
        FileManager.saveImage(
                mPath.substring(mPath.lastIndexOf(File.separator)),
                FileManager.TypeImage.Photo,
                mEndBitmap
        );
    }

    private void openMainMenu() {
        FragmentReplacer.replaceFragment(getHostActivity(), new MenuFragment());
    }

}
