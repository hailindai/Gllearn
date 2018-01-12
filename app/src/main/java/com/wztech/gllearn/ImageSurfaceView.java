package com.wztech.gllearn;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * <pre>
 *     author : daihailin
 *     e-mail : daihl@kdxfilm.com
 *     time   : 2018/01/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ImageSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    /**
     * 程序句柄
     */
    protected int mProgram;

    /**
     * 顶点坐标句柄
     */
    protected int mPosition = 0;

    /**
     * 纹理坐标句柄
     */
    protected int mCoordinate = 1;

    /**
     * 顶点坐标Buffer
     */
    protected FloatBuffer mVertexBuffer;

    /**
     * 纹理坐标Buffer
     */
    protected FloatBuffer mTextureBuffer;


    public ImageSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public ImageSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        setEGLContextClientVersion(2);
        setRenderer(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        prepare(Triangle.vertices,Triangle.vShader,Triangle.fShader,null,null);
//        prepare(Square.vertices, Square.vShader, Square.fShader, Square.textures, Square.getTexture(getResources()));
    }

    public void prepare(float vertices[], String vShader, String fShader, float textures[],Bitmap bitmap) {
        //将背景设置为灰色
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        //创建一个空的OpenGLES程序
        mProgram = GLUtil.uCreateGlProgram(vShader, fShader);

        //申请底层空间
        ByteBuffer bb = ByteBuffer.allocateDirect(
                vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        //将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        if(textures != null) {
            //申请底层空间
            ByteBuffer bb2 = ByteBuffer.allocateDirect(
                    textures.length * 4);
            bb2.order(ByteOrder.nativeOrder());
            //将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
            mTextureBuffer = bb2.asFloatBuffer();
            mTextureBuffer.put(textures);
            mTextureBuffer.position(0);
        }

        int texture[] = new int[1];
        if (bitmap != null && !bitmap.isRecycled()) {
            GLES20.glGenTextures(1, texture, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
            // 为当前绑定的纹理对象设置环绕、过滤方式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        }


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPosition);
        //告诉OpenGL该如何解析顶点数据，同时复制数据
        GLES20.glVertexAttribPointer(mPosition, 3,
                GLES20.GL_FLOAT, false,
                0, mVertexBuffer);
        if(mTextureBuffer != null){
            GLES20.glEnableVertexAttribArray(mCoordinate);
            GLES20.glVertexAttribPointer(mCoordinate, 2,
                    GLES20.GL_FLOAT, false,
                    0, mTextureBuffer);
        }
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexBuffer.capacity() / 3);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPosition);
        if(mTextureBuffer != null){
            GLES20.glDisableVertexAttribArray(mCoordinate);
        }
    }

//    @Override
//    public void onDrawFrame(GL10 gl) {
//        //将程序加入到OpenGLES2.0环境
//        GLES20.glUseProgram(mProgram);
//
//        //顶点数据复制到缓冲的内存
//        int VBO[] = new int[1];
//        GLES20.glGenBuffers(1,VBO,0);
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,VBO[0]);
//        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,Triangle.vertices.length * 4,mVertexBuffer,GLES20.GL_STATIC_DRAW);
//        //启用三角形顶点的句柄
//        GLES20.glEnableVertexAttribArray(mPosition);
//        //告诉OpenGL该如何解析顶点数据
//        GLES20.glVertexAttribPointer(mPosition, 3,
//                GLES20.GL_FLOAT, false,
//                0, 0);
//
//        //绘制三角形
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//        //禁止顶点数组的句柄
//        GLES20.glDisableVertexAttribArray(mPosition);
//    }

}
