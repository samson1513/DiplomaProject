package com.example.samson.diplomaproject.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public abstract class FileExplorer {

    public static final String TAG_BLUR = "_B";
    public static final String TAG_DEFOCUS = "_D";
    public static final String FORMAT_JPG = ".jpg";
    public static final String DIRECTORY = "Project";
    public static final String DIRECTORY_NORMAL = "Normal";
    public static final String DIRECTORY_BLURED = "Blured";
    public static final String DIRECTORY_DEFOCUSED = "Defocused";

    public enum TypeImage {Normal, Blured, Defocused}

    public static String getPathDirectory(TypeImage type) {
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getNameDirectory(type)
        ).getAbsolutePath() + File.separator;
    }

    public static void createDirectory(TypeImage type) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getNameDirectory(type)
        );
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.e("Project", "failed to create directory: is not made");
            }
        }
    }

    private static String getNameDirectory(TypeImage type) {
        String dirName = DIRECTORY + File.separator;
        switch (type) {
            case Blured:
                dirName += DIRECTORY_BLURED;
                break;
            case Defocused:
                dirName += DIRECTORY_DEFOCUSED;
                break;
            default:
                dirName += DIRECTORY_NORMAL;
                break;
        }
        return dirName;
    }

    public static File createImageFile(String imageName, TypeImage type) {
        String fileName = imageName;
        switch (type) {
            case Blured:
                fileName += TAG_BLUR + FORMAT_JPG;
                break;
            case Defocused:
                fileName += TAG_DEFOCUS + FORMAT_JPG;
                break;
            case Normal:
                fileName += FORMAT_JPG;
                break;
        }
        return new File(getPathDirectory(type) + fileName);
    }
}