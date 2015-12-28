package com.example.samson.diplomaproject.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.samson.diplomaproject.R;
import com.example.samson.diplomaproject.utils.BitmapCreator;

/**
 * Created by samson on 22.07.15.
 */
public class PhotoHolder {
    private ImageView mPicture;

    public PhotoHolder(){

    }

    public PhotoHolder(View _view){
        mPicture = (ImageView) _view.findViewById(R.id.ivPicture);
    }

    public void setPicture(String _path){
        mPicture.setImageBitmap(BitmapCreator.getCompressedBitmap(256, 1F, _path));
    }
}
