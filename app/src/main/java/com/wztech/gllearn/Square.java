package com.wztech.gllearn;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * <pre>
 *     author : daihailin
 *     e-mail : daihl@kdxfilm.com
 *     time   : 2018/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Square {

    public static float vertices[] = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
    };

    public static float textures[] = {
            0f,0f,
            1f,0f,
            0f,1f,
            1f,0f,
            0f,1f,
            1f,1f
    };

    public static String vShader = "" +
            " layout(location = 0)attribute vec4 vPosition;\n" +
            " layout(location = 1)attribute vec2 vCoordinate;\n" +
            " varying vec2 aCoordinate;\n" +
            " void main() {\n" +
            "     gl_Position = vPosition;\n" +
            "     aCoordinate = vCoordinate;\n" +
            " }";

    public static String fShader = "" +
            "precision mediump float;\n" +
            "\n" +
            "uniform sampler2D vTexture;\n" +
            "varying vec2 aCoordinate;\n" +
            "\n" +
            "void main(){\n" +
            "    gl_FragColor=texture2D(vTexture,aCoordinate);\n" +
            "}";

    public static Bitmap getTexture(Resources resources){
        Bitmap bitmap = BitmapFactory.decodeResource(resources,R.raw.square);
        return bitmap;
    }
}
