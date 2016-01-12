package com.example.samson.diplomaproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import com.example.samson.diplomaproject.global.Constants;


public abstract class EffectsUtil {

    public static Bitmap doMotionBlur(Bitmap bitmap, int xSpeed, int ySpeed) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int[] returnPixels = motionBlurFilter(pixels, width, height, xSpeed, ySpeed);
        return Bitmap.createBitmap(returnPixels, width, height, Bitmap.Config.ARGB_8888);
    }


    public static Bitmap doGaussianBlur(@NonNull Bitmap src, int radius, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();
        return bitmap;
    }

    private static int[] motionBlurFilter(int[] original, int width, int height, int xSpeed, int ySpeed) {

        int DELAY = 1;
        int absXSpeed = Math.abs(xSpeed);
        int absYSpeed = Math.abs(ySpeed);
        int routeX = (int) Math.signum(xSpeed), routeY = (int) Math.signum(ySpeed);
        int[] pixels = original.clone();
        int pixel, xOff, yOff, finalXCoord, finalYCoord;
        for (int y = 0; y < height - DELAY; y++) {
            for (int x = 0; x < width - DELAY; x++) {
                pixel = pixels[y * width + x];
                int sumR = Color.red(pixel);
                int sumG = Color.green(pixel);
                int sumB = Color.blue(pixel);
                for (int xOffset = 1; xOffset <= absXSpeed; xOffset++) {
                    for (int yOffset = 1; yOffset <= absYSpeed; yOffset++) {
                        if (xOffset <= x) {
                            xOff = xOffset;
                        } else {
                            xOff = xOffset + x;
                        }
                        if (yOffset <= y) {
                            yOff = yOffset;
                        } else {
                            yOff = yOffset + y;
                        }
                        finalXCoord = minMax(width - 1, x + routeX * xOff);
                        finalYCoord = minMax(height - 1, y + routeY * yOff);
                        pixel = pixels[finalYCoord * width + finalXCoord];
                        sumR += Color.red(pixel);
                        sumG += Color.green(pixel);
                        sumB += Color.blue(pixel);
                    }
                }
                int absXY = absXSpeed * absYSpeed + 1;
                sumR /= absXY;
                sumG /= absXY;
                sumB /= absXY;
                sumR = minMax(255, sumR);
                sumG = minMax(255, sumG);
                sumB = minMax(255, sumB);
                original[y * width + x] = Color.rgb(sumR, sumG, sumB);
            }
        }
        return original;
    }

    private static int minMax(int min, int max) {
        return Math.min(min, Math.max(max, 0));
    }
}
