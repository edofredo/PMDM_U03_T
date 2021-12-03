package com.pmdm.pmdm_u04_t;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class BotonGrabar extends AppCompatButton {

    //declaración de variables
    private boolean grabando = false;
    private MainActivity activity;

    /**
     * Constructor de la clase
     * Crea un botón con un texto y color de fondo, asigna un listener de evento e instancia
     * una activity que es el contexto
     * @param context contexto de la activity
     */
    public BotonGrabar(@NonNull Context context) {
        super(context);
        setText("Grabar");
        setBackgroundColor(Color.parseColor("#2196F3"));
        setOnClickListener(clicker);
        activity = (MainActivity) context;
    }

    /**
     * Método que alterna el botón entre grabar y parar grabación
     * Llamada a métodos de la clase activity para controlar la grabación y la disponibilidad
     * de los botones del reproductor en función del estado del mismo
     */
    OnClickListener clicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (grabando){
                setText("Grabar");
                activity.enableButton(true,false,false,false, true);
                grabando = false;
                activity.pararGrabacion();


            } else {
                setText("Parar grabación");
                activity.enableButton(false,false,false,false, true);
                grabando = true;
                activity.empezarGrabacion();


            }
        }
    };

    public boolean isGrabando() {
        return grabando;
    }

    public void setGrabando(boolean grabando) {
        this.grabando = grabando;
    }

}
