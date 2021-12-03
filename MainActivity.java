package com.pmdm.pmdm_u04_t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //declaración de variables/objetos
    private Button bAdelantar, bRebobinar, bParar;
    private BotonGrabar bGrabar;
    private BotonReproducir bReproducir;
    private int position;
    private MediaRecorder mr = null;
    private MediaPlayer mp = null;

    //variable de permisos para grabar
    private boolean permissionToRecordAccepted = false;

    //declaración e inicialización de array de String con constante de clase
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    /**
     * Metodo invocado cuando se crea la activity por primera vez
     * @param savedInstanceState Parámetro que representa un estado guardado de la app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* declaración/instanciación y asignacion de layouts y botones correspondientes a aquellos
        creados mediante programación Java
         */
        LinearLayout layReproduccion = findViewById(R.id.layReproducion);
        LinearLayout layGrabacion = findViewById(R.id.layGrabacion);
        bGrabar = new BotonGrabar(this);
        bReproducir = new BotonReproducir(this);

        layReproduccion.addView(bReproducir);
        layGrabacion.addView(bGrabar);
        layReproduccion.setGravity(1);
        layGrabacion.setGravity(1);

        //Instanciación de objeto Mediaplayer y cargado fichero para reproducción
        mp = new MediaPlayer().create(this, R.raw.thunderstruck);

        //Asignación de botones de la vista a variables del controlador
        bAdelantar = findViewById(R.id.butAdelantar);
        bRebobinar = findViewById(R.id.butRebobinar);
        bParar = findViewById(R.id.butParar);

    }

    /*
    Metodo que se ejecuta al ganar el foco la activity
    Retoma el estado de la reproducción
     */
    @Override
    protected void onResume(){
        super.onResume();
        mp.seekTo(position);
        if(bReproducir.getEstadoReproduccion()==true) {
            enableButton(true, true, true, true, false);
            mp.start();
        }else
            if(bGrabar.isGrabando()==true) {
                enableButton(false, false, false, false, true);
            }else{
                enableButton(true, false, false, false, true);
            }
    }

    /*
   Método que se ejecuta la pausarse la activity
   Pausa la reproducción
    */
    @Override
    protected void onPause(){
        super.onPause();
        mp.pause();
        position = mp.getCurrentPosition();
    }

    /*
    Método que controla la disponibilidad de los botones del reproductor
     */
    public void enableButton(boolean reprod, boolean rebob, boolean parar, boolean adelan, boolean grabar){
        bReproducir.setEnabled(reprod);
        bRebobinar.setEnabled(rebob);
        bParar.setEnabled(parar);
        bAdelantar.setEnabled(adelan);
        bGrabar.setEnabled(grabar);

    }

    /*
    Método que comienza la grabación si la activity tiene los permisos de grabación concedidos
    Si no los tiene concedidos, los solicita
     */
    public void empezarGrabacion(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)==
                PackageManager.PERMISSION_GRANTED){
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
            ActivityCompat.requestPermissions(this,permissions,1111);
        }
    }

    /*
    Método que gestiona el permiso de grabación en caso de que no estén concedidos todavía
    Si no se conceden, reestablece el reproductor al estado anterior al de grabar
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1111:
                permissionToRecordAccepted = grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            Toast.makeText(this, "Permisos para grabar no concedidos", Toast.LENGTH_LONG).show();
            bGrabar.setGrabando(false);
            bGrabar.setText("Grabar");
            mp.release();
            mp = new MediaPlayer().create(this, R.raw.thunderstruck);

        } else {
            empezarGrabacion();
        }
    }

    /*
    Metodo que para la grabacion e invoca el método cargarGrabación()
     */
    public void pararGrabacion(){
        mr.stop();
        mr.reset();
        mr.release();
        mr = null;
        cargarGrabacion();
    }

    /*
    Método que carga el fichero grabado en el reproductor
     */
    public void cargarGrabacion() {
        mp.reset();
        try {
            mp.setDataSource(getFilesDir().getAbsolutePath() + File.separator + "grabacion.mp3");
            mp.prepare();
        } catch (IOException ex){
            Toast t = Toast.makeText(this,"No se pudo guardar la grabacion",Toast.LENGTH_LONG);
        }
        Toast t = Toast.makeText(this,"Grabación disponible para reproducir",Toast.LENGTH_LONG);
        t.show();
    }

    /*
    Método que comienza la reproducción
     */
    public void empezarReproduccion(){
        mp.start();
    }

    /*
    Método que pausa la reproducción
     */
    public void pausarReproduccion(){
        mp.pause();
    }

    /*
    Método que para la reproducción
     */
    public void pararReproduccion() throws IOException {
        mp.stop();
        bReproducir.setReproduciendo(false);
        bReproducir.setTexto("Reproducir");
        mp.prepare();
        enableButton(true,false,false,false, true);
    }

    /*
    Método que adelanta la reproducción 5seg
     */
    public void adelantar(){
        mp.seekTo(mp.getCurrentPosition()+5000);
    }

    /*
    Método que rebobina la reproducción 5seg
     */
    public void rebobinar(){
        mp.seekTo(mp.getCurrentPosition()-5000);
    }

    /*
    Método que maneja los eventos de click de los botones de la vista en XML
     */
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

