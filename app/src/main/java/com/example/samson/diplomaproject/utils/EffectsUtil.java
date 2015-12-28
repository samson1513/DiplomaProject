package com.example.samson.diplomaproject.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.example.samson.diplomaproject.global.Constants;


public abstract class EffectsUtil {

    public Bitmap setGaussFilter(@NonNull final Bitmap srcBitmap){
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap);
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
