package com.uva.moviles.savethefrog;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Jennifer on 06/11/2015.
 */
public class Terreno {
    //Buffers para las coordenadas y el orden en el que estan se van a dibujar
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    //Matriz del modelo diagonal
    //TODO: GETTERs Y SETTERs
    //TODO: Añadir texturas
    public float[] mModelMatrix = new float[16];

    //Numero de coordenadas en que componen cada figura
    static final int COORD_PER_VERTEX = 3;

    //Vetor de coordenadas (Cada linea es un vertice)


    static float coord[] = {
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
    };

    private short drawOrder[] = {0,1,2,2,1,3};

    // Orden en el que se dibujan las vertices del vector de coordenadas

    private final String vertexShaderCode = "uniform mat4 uMVPMatrix;attribute vec4 vPosition;void main() {gl_Position = uMVPMatrix * vPosition;}";
    private final String fragmentShaderCode = "precision mediump float;uniform vec4 vColor;void main() {gl_FragColor = vColor;}";

    private final int mProgram;

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexStride = COORD_PER_VERTEX * 4;

    private int mMVPMatrixHandle;

    public Terreno() {

        ByteBuffer bb = ByteBuffer.allocateDirect(
                coord.length * 4);

        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coord);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);

        GLES20.glLinkProgram(mProgram);

        Matrix.setIdentityM(mModelMatrix, 0);

    }

    public void draw(float[] color, float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram,"vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle,COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);

        mColorHandle = GLES20.glGetUniformLocation(mProgram,"vColor");

        GLES20.glUniform4fv(mColorHandle,1,color,0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mvpMatrix,0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,drawOrder.length,GLES20.GL_UNSIGNED_SHORT,drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
