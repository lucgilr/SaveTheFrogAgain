package com.uva.moviles.savethefrog;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mario on 30/10/2015.
 */
public class RanaModelo {



    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    private float posiciones[];
    private float texels[];
    private float normales[];
    private int caras[];

    private short drawOrder[];


    private void getDatos(){
        File f = new File("rana/frog_model.obj");
        try{
            int vertices = 0;
            int posiciones = 0;
            int texels = 0;
            int normales = 0;
            int caras = 0;
            Scanner fichero = new Scanner(f);
            String linea;
            while(fichero.hasNext()){
                linea = fichero.next().substring(0,2);

                if(linea.equals("v ")){
                    posiciones++;
                }else if(linea.equals("vt")){
                    texels++;
                }else if(linea.equals("vn")){
                    normales++;
                }else if(linea.equals("f ")){
                    caras++;
                }
            }
            fichero.close();

            vertices = caras * 3;
        }catch (IOException e){

        }
    }
}
