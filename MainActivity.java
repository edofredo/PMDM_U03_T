package com.pmdm.pmdm_u04_t;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bAdelantar, bRebobinar, bParar;
    BotonGrabar bGrabar;
    BotonReproducir bReproducir;

    //declaracion de objeto MediaRecorder
    MediaRecorder mr = null;

    MediaPlayer mp = null;

    //ID del permiso
    //private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    //solicitud de permisos para grabar
    private boolean permissionToRecordAccepted = false;
    //declaración e inicialización de array de String con constante de clase
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};



    /**
     * Metodo que crea la interfaz grafica
     * @param savedInstanceState Parámetro que representa un estado guardado de la app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layReproduccion = findViewById(R.id.layReproducion);
        LinearLayout layGrabacion = findViewById(R.id.layGrabacion);
        bGrabar = new BotonGrabar(this);
        bReproducir = new BotonReproducir(this);

        layReproduccion.addView(bReproducir);
        layGrabacion.addView(bGrabar);
        layReproduccion.setGravity(1);
        layGrabacion.setGravity(1);

        mp = new MediaPlayer().create(this, R.raw.thunderstruck);
        bAdelantar = findViewById(R.id.butAdelantar);
        bRebobinar = findViewById(R.id.butRebobinar);
        bParar = findViewById(R.id.butParar);


    }

    private void enableButton(boolean reprod, boolean rebob, boolean adelan){
        bReproducir.setEnabled(reprod);
        bRebobinar.setEnabled(rebob);
        bAdelantar.setEnabled(adelan);

    }


    public void empezarGrabacion(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED){

            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setOutputFile(getFilesDir().getAbsolutePath()+ File.separator+"grabacion.mp3");
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try{
                mr.prepare();
            } catch(IOException e){
                mr.reset();
                mr.release();
                mr = null;
                e.printStackTrace();
            }
            mr.start();


        }else{
            ActivityCompat.requestPermissions(this,permissions,1112);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1112:
                permissionToRecordAccepted  = grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
        else{
            empezarGrabacion();
        }
    }

    public void pararGrabacion(){
        mr.stop();
        mr.reset();
        mr.release();
        mr = null;
    }

    public void empezarReproduccion(){
        mp.start();

    }

    public void pausarReproduccion(){
        mp.pause();
    }

    public void pararReproduccion() throws IOException {
        mp.stop();
        bReproducir.setReproduciendo(false);
        bReproducir.setTexto("Reproducir");
        mp.prepare();

    }

    public void adelantar(){
        mp.seekTo(500);
    }

    public void rebobinar(){
        mp.seekTo(-500);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.butAdelantar:
                adelantar();
                break;
            case R.id.butRebobinar:
                rebobinar();
                break;
            case R.id.butParar:
                try {
                    pararReproduccion();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}