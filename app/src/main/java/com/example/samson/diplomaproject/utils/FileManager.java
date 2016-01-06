package com.example.samson.diplomaproject.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class FileManager {

    private static final String TAG = "FileManager";

    public static final String TAG_BLUR = "_B";
    public static final String TAG_DEFOCUS = "_D";
    public static final String FORMAT_JPG = ".jpg";
    public static final String DIRECTORY = "Project";
    public static final String DIRECTORY_BASE = "Base";
    public static final String DIRECTORY_BLURED = "Blured";
    public static final String DIRECTORY_DEFOCUSED = "Defocused";
    public static final String DIRECTORY_PHOTO = "Photo";

    public enum TypeImage {Base, Blured, Defocused, Photo}

    public static String getPathDirectory(TypeImage type) {
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getNameDirectory(type)
        ).getAbsolutePath();
    }

    public static void createWorkDirectory(TypeImage type) {
        createDirectory(getNameDirectory(type));
    }

    public static void createProjectDirectory(){
        createDirectory(DIRECTORY);
    }

    private static void createDirectory(String nameDirectory) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                nameDirectory
        );
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.e(TAG, "directory is not created");
            }
        }
    }

    public static void deleteDirectory(String path) {
        File dir = new File(path);
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file : files) {
                deleteDirectory(file.getPath());
            }
        }
        if (!dir.delete()){
            Log.e(TAG, "directory is not deleted");
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
            case Photo:
                dirName += DIRECTORY_PHOTO;
                break;
            default:
                dirName += DIRECTORY_BASE;
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
            case Base:
                fileName += FORMAT_JPG;
                break;
        }
        return new File(getPathDirectory(type) + File.separator + fileName);
    }

    public static void saveImage(@Nullable String name, TypeImage type, Bitmap bitmap) {
        saveImage(name, type, BitmapManager.convertToByteArray(bitmap));
    }

    public static File saveImage(@Nullable String name, TypeImage type, byte[] bytes) {
        if(name == null){
            name = new SimpleDateFormat("MMdd_HHmm", Locale.getDefault()).format(new Date());
        }
        File fileImage = createImageFile(name, type);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileImage);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Learning", "failed to save image");
        }
        return fileImage;
    }
}