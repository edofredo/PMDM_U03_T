package com.pmdm.pmdm_u04_t;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class BotonReproducir extends AppCompatButton{

    //declaración de variables
    private boolean reproduciendo = false;
    private String texto;
    MainActivity activity;

    /**
     * Constructor de la clase
     * Crea un botón con un texto y color de fondo, asigna un listener de evento e instancia
     * una activity que es el contexto
     * @param context contexto de la activity
     */
    public BotonReproducir(@NonNull Context context) {
        super(context);
        setText("Reproducir");
        setBackgroundColor(Color.parseColor("#2196F3"));
        setOnClickListener(clicker);
        activity = (MainActivity) context;

    }

    /**
     * Método que alterna el botón entre Reproducir y Pause
     * Llamada a métodos de la clase activity para controlar la reproducción y la disponibilidad
     * de los botones del reproductor en función del estado del mismo
     */
    View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (reproduciendo){
                texto = "Reproducir";
                setText(texto);
                reproduciendo = false;
                activity.enableButton(true,true,true,true, false);
                activity.pausarReproduccion();

            } else {
                texto = "Pausar";
                setText(texto);
                reproduciendo = true;
                activity.enableButton(true,true,true,true, false);
                activity.empezarReproduccion();


            }
        }
    };

    /**
     * Método setter para la variable reproduciendo
     */
    public void setReproduciendo(boolean r){
        this.reproduciendo = r;
    }

    /**
     * Método getter para la variable reproduciendo
     */
    public boolean getEstadoReproduccion(){
        return reproduciendo;
    }

    /**
     * Método setter para el texto del botón
     * @param t texto a mostrar en el botón
     */
    public void setTexto(String t){
        this.texto = t;
        setText(texto);
    }
}

