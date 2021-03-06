package com.example.samson.diplomaproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public abstract class BitmapManager {

    public static Bitmap getBitmap(String filePath) {
       return BitmapFactory.decodeFile(filePath);
    }

    public static Bitmap getCompressedBitmap(float width, float ratio, String path){

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if(options.outHeight == 0)
            return null;

        float maxHeight = width / ratio;
        float imgRatio = actualWidth / actualHeight;

        if (actualHeight > maxHeight || actualWidth > width) {
            if (imgRatio < ratio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > ratio) {
                imgRatio = width / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) width;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) width;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        options.inJustDecodeBounds = false;

        try {
            bmp = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError bmp");
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError scale");
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        if(scaledBitmap == null)
            return null;
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        try {
            canvas.drawBitmap(
                    bmp,
                    middleX - bmp.getWidth() / 2,
                    middleY - bmp.getHeight() / 2,
                    new Paint(Paint.FILTER_BITMAP_FLAG)
            );
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        try {
            return Bitmap.createScaledBitmap(scaledBitmap, actualWidth, actualHeight, true);
        } catch (OutOfMemoryError error){
            error.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError scale");
            return null;
        }

    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static byte[] convertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
