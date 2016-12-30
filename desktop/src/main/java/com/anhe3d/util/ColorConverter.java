package com.anhe3d.util;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.IOException;

/**
 * Created by jimmy on 2016/12/19.
 */
public class ColorConverter {
    private final static String pathToCMYKProfile = "";

    public static float[] rgbToCmyk(float... rgb) throws IOException {
        if (rgb.length != 3) {
            throw new IllegalArgumentException();
        }
        ColorSpace instance = new ICC_ColorSpace(ICC_Profile.getInstance(pathToCMYKProfile));
        return instance.fromRGB(rgb);
    }

    public static float[] cmykToRgb(float... cmyk) throws IOException {
        if (cmyk.length != 4) {
            throw new IllegalArgumentException();
        }
        ColorSpace instance = new ICC_ColorSpace(ICC_Profile.getInstance(pathToCMYKProfile));
        return instance.toRGB(cmyk);
    }
}
