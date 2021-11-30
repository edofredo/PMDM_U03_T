package com.pmdm.pmdm_u04_t;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class BotonGrabar extends AppCompatButton {

    //declaración de variables
    boolean grabando = false;
    MainActivity activity;

    /**
     * Constructor de la clase
     * Crea un botón con un texto predeterminado, asigna un listener de evento e instancia
     * una activity que es el contexto
     * @param context Recibe por parámetro el contexto
     */
    public BotonGrabar(@NonNull Context context) {
        super(context);
        setText("Grabar");
        setOnClickListener(clicker);
        activity = (MainActivity) context;
    }

    /**
     * Método que alterna el botón entre grabar y parar grabación
     */
    OnClickListener clicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (grabando){
                setText("Grabar");
                grabando = false;
                activity.pararGrabacion();
            } else {
                setText("Parar grabación");
                grabando = true;
                activity.empezarGrabacion();
            }
        }
    };

}
