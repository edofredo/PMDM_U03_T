package com.pmdm.pmdm_u04_t;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import androidx.annotation.NonNull;

public class BotonReproducir extends AppCompatButton{

    //declaración de variables
    private boolean reproduciendo = false;
    private String texto;
    MainActivity activity;

    /**
     * Constructor de la clase
     * Crea un botón con un texto predeterminado, asigna un listener de evento e instancia
     * una activity que es el contexto
     * @param context Recibe por parámetro el contexto
     */
    public BotonReproducir(@NonNull Context context) {
        super(context);
        setText("Reproducir");
        setOnClickListener(clicker);
        activity = (MainActivity) context;
    }

    /**
     * Método que alterna el botón entre grabar y parar grabación
     */
    View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (reproduciendo){
                texto = "Reproducir";
                setText(texto);
                reproduciendo = false;
                activity.pausarReproduccion();
            } else {
                texto = "Pausar";
                setText(texto);
                reproduciendo = true;
                activity.empezarReproduccion();
            }
        }
    };

    public void setReproduciendo(boolean r){
        this.reproduciendo = r;
    }

    public void setTexto(String t){
        this.texto = t;
        setText(texto);
    }

}
