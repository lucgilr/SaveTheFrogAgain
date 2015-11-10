package com.uva.moviles.savethefrog;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jennifer on 03/11/2015.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    //TODO: Metodo para añadir coches y camiones a la carretera
    //TODO: Metodo para añadir arbustos y arboles al cesped
    //TODO: Metodo para añadir troncos y nenufares al rio
    //TODO: Metodo para detectar colisiones entre la rana y los coches
    //TODO: Metodo para detectar si la rana esta sobre agua o sobre un tronco o nenufar (MISMO QUE EL ANTERIOR)
    //TODO: Metodo para detectar ver se si puede mover a la posicion deseada
    //TODO: Deteccion del evento de "click"
    //TODO: Deteccion del evento de deslizamiento y su direccion
    //TODO: Metodo de movimiento de la rana (salto = Parabola?)
    //TODO: Cambiar la camara de sitio (Vista de las laminas del suelo al estilo crossy road)
    //TODO: Metodo generacion aleatoria de cesped



    private Terreno[] cespeds = new Terreno[5];
    private Terreno[] carreteras = new Terreno[5];
    private Terreno[] rios = new Terreno[5];
    //Color Verde en formato R G B A 0 = min 1 = max
    private float[] colorCesped = { 0.0f, 0.5f,0.0f,1.0f};
    private float[] colorCarretera = { 0.5f, 0.5f,0.5f,1.0f};
    private float[] colorRio = { 0.0f, 1.0f,1.0f,1.0f};

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mScalationMatrix = new float[16];
    private final float[] mTempMatrix = new float[16];
    private int[] _viewport;

    private float movY = 4f;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


        for (int i = 0; i < 5; i++) {
            cespeds[i] = new Terreno();
            carreteras[i] = new Terreno();
            rios[i] = new Terreno();


            Matrix.translateM(cespeds[i].mModelMatrix, 0, -5f, i, -3.0f);

            if (i % 2 == 0) {
                Matrix.translateM(rios[i].mModelMatrix, 0, -5f, i+1, -3.0f);
            }else{
                Matrix.translateM(carreteras[i].mModelMatrix, 0, -5f, i+1, -3.0f);
            }



        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        _viewport = new int[]{0,0,width,height};

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);



    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(mViewMatrix, 0, 2.5f, -1.0f, 3f, 2.5f, 0.8f, -3.0f, 0f, 2.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        movY -= 0.001f;

        for (int i = 0; i < 5; i++){
            Matrix.scaleM(mScalationMatrix, 0, cespeds[i].mModelMatrix, 0, 20.0f, 1.0f, 1.0f);
            Matrix.multiplyMM(mTempMatrix, 0, mMVPMatrix, 0, mScalationMatrix, 0);
            cespeds[i].draw(colorCesped, mTempMatrix);

            Matrix.scaleM(mScalationMatrix, 0, carreteras[i].mModelMatrix, 0, 20.0f, 1.0f, 1.0f);
            Matrix.multiplyMM(mTempMatrix, 0, mMVPMatrix, 0, mScalationMatrix, 0);
            carreteras[i].draw(colorCarretera, mTempMatrix);

            Matrix.scaleM(mScalationMatrix, 0, rios[i].mModelMatrix, 0, 20.0f, 1.0f, 1.0f);
            Matrix.multiplyMM(mTempMatrix, 0, mMVPMatrix, 0, mScalationMatrix, 0);
            rios[i].draw(colorRio, mTempMatrix);

            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

            Matrix.setIdentityM(cespeds[i].mModelMatrix, 0);
            Matrix.translateM(cespeds[i].mModelMatrix, 0, -5f, movY, -3.0f);
            Matrix.rotateM(cespeds[i].mModelMatrix, 0, -20.0f, 0.0f, 0.0f, 1.0f);

            if( i % 2 == 0){
                Matrix.setIdentityM(rios[i].mModelMatrix, 0);
                Matrix.translateM(rios[i].mModelMatrix, 0, -5f, movY + 1, -3.0f);
                Matrix.rotateM(rios[i].mModelMatrix, 0, -20.0f, 0.0f, 0.0f, 1.0f);
            }else{
                Matrix.setIdentityM(carreteras[i].mModelMatrix, 0);
                Matrix.translateM(carreteras[i].mModelMatrix, 0, -5f, movY + 1, -3.0f);
                Matrix.rotateM(carreteras[i].mModelMatrix, 0, -20.0f, 0.0f, 0.0f, 1.0f);
            }



        }






    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    private Terreno getNewTerreno(){
        Terreno t = null;

        return t;
    }
}
