package com.example.samson.diplomaproject.holders;

import android.view.View;
import android.widget.ImageView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.utils.BitmapCreator;

public class PhotoHolder {
    private ImageView mPicture;

    public PhotoHolder(View _view){
        mPicture = (ImageView) _view.findViewById(R.id.ivPicture);
    }

    public void setPicture(String _path){
        mPicture.setImageBitmap(BitmapCreator.getCompressedBitmap(256, 1F, _path));
    }
}
