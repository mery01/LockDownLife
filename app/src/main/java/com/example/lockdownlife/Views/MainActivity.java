package com.example.lockdownlife.Views;

/***************************************************************************************************
 * NOMBRE DE LA CLASE: Main
 * FUNCIONALIDAD DE DONDE VIENE: ninguna
 * FUNCIONALIDAD HACIA DONDE VA: Home
 * DESCRIPCIÓN: Se trata de una actividad de inicio, sólo aparece el logo de la aplicación.
 *
 * La aplicación parte del ejemplo HelloAppMov proporcionado en la asignatura Aplicaciones Móviles
 * a la que se le ha añadido esta pantalla con un contador que muestra el logo de la aplicación
 * al iniciarse.
 * ************************************************************************************************/

import android.content.Intent;

import android.widget.ImageView;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lockdownlife.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Contador para la pantalla principal de inicio con el icono, cuando pasan esos 3000 ms
        // pasamos al menu principal.
        final int contador = 3000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Esto sirve para esconder el actionBar.
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Funcion que muestra el nombre y el logo de la app
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                //Obtenemos referencias a los elementos del interfaz grafico
                ImageView lgtype = findViewById(R.id.Portada);
                lgtype.setImageResource(R.mipmap.ic_launcher);

                // Iniciamos la nueva actividad
                startActivity(intent);
                finish();
            }
        },contador);


    }
}

