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

public class Triangle {

    public static float vertices[] = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f,  0.5f, 0.0f
    };

    public static String vShader = "" +
            " layout(location = 0)attribute vec4 vPosition;\n" +
            " void main() {\n" +
            "     gl_Position = vPosition;\n" +
            " }";

    public static String fShader = "" +
            " precision mediump float;\n" +
            " void main() {\n" +
            "     gl_FragColor = vec4(0.0,1.0,0.0,0.0);\n" +
            " }";

}
