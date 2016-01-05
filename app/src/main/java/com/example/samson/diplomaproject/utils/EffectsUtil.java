package com.example.samson.diplomaproject.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.example.samson.diplomaproject.global.Constants;


public abstract class EffectsUtil {

    public static Bitmap doGaussianBlur(Bitmap original, int radius) {
        Bitmap result = original.copy(original.getConfig(), true);
        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = new int[width * height];
        original.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int r = radius; r >= 1; r /= 2) {
            for(int i = r; i < height - r; i++) {
                for(int j = r; j < width - r; j++) {
                    int tl = pixels[(i - r) * width + j - r];
                    int tr = pixels[(i - r) * width + j + r];
                    int tc = pixels[(i - r) * width + j];
                    int bl = pixels[(i + r) * width + j - r];
                    int br = pixels[(i + r) * width + j + r];
                    int bc = pixels[(i + r) * width + j];
                    int cl = pixels[i * width + j - r];
                    int cr = pixels[i * width + j + r];

                    pixels[i * width + j] = 0xFF000000 |
                            (((tl & 0xFF) + (tr & 0xFF) + (tc & 0xFF) + (bl & 0xFF) + (br & 0xFF) + (bc & 0xFF) + (cl & 0xFF) + (cr & 0xFF)) >> 3) & 0xFF |
                            (((tl & 0xFF00) + (tr & 0xFF00) + (tc & 0xFF00) + (bl & 0xFF00) + (br & 0xFF00) + (bc & 0xFF00) + (cl & 0xFF00) + (cr & 0xFF00)) >> 3) & 0xFF00 |
                            (((tl & 0xFF0000) + (tr & 0xFF0000) + (tc & 0xFF0000) + (bl & 0xFF0000) + (br & 0xFF0000) + (bc & 0xFF0000) + (cl & 0xFF0000) + (cr & 0xFF0000)) >> 3) & 0xFF0000;
                }
            }
        }
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    public static Bitmap doMotionBlur(Bitmap bitmap, int xSpeed, int ySpeed){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

//        int[] returnPixels = NativeFilterFunc.motionBlurFilter(pixels, width, height, xSpeed, ySpeed);
        int[] returnPixels = new int[pixels.length];
        return Bitmap.createBitmap(returnPixels, width, height, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap setGaussFilter(@NonNull final Bitmap srcBitmap){
        Bitmap bitmap = srcBitmap.copy(srcBitmap.getConfig(), true);
        int[][] filter = Constants.FILTER_GAUSS;
        int offset = Constants.GAUSS_OFFSET;
        float div = Constants.GAUSS_DIV;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
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

                        pixel = bitmap.getPixel(xv, yv);

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

                outImage[x][y] = Color.rgb(r, g, b);
            }
        }

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                bitmap.setPixel(x, y, outImage[x][y]);
            }
        }
        return bitmap;
    }

}
