package com.example.samson.diplomaproject.utils;

import android.os.Environment;
import android.util.Log;

import com.example.samson.diplomaproject.global.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class FileExplorer {


    public static String getPathDirectory(){
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.DIRECTORY
        ).getAbsolutePath() + File.separator;
    }

    public static void createDirectory(){
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.DIRECTORY
        );
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdir()){
                Log.e("Project", "failed to create directory: is not made");
            }
        }
    }

    public static File createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(getPathDirectory()
                + "IMG_"
                + timeStamp
                + ".jpg");
    }

}
