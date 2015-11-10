package com.uva.moviles.savethefrog;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Jennifer on 03/11/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        mRenderer = new MyGLRenderer();


        setEGLContextClientVersion(2);
        //super.setEGLConfigChooser(8,8,8,8,16,0);

        setRenderer(mRenderer);

    }
}
