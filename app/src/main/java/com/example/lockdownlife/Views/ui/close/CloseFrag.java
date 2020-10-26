package com.example.lockdownlife.Views.ui.close;

/***************************************************************************************************
 * NOMBRE DE LA CLASE: CloseFragment
 * FUNCIONALIDAD DE DONDE VIENE: HomeActivity
 * FUNCIONALIDAD HACIA DONDE VA: Sale de la aplicación y la cierra.
 * DESCRIPCIÓN: Pantalla de Salir. Este fragment de la actividad HomeActivity tiene la
 * función de salir de la aplicación cuando de confirma el AlertDialog.
 * REFERENCIAS:
 * Código extraído del siguiente vídeo. Todos los derechos reservados al autor de este código.
 * El código ha sido adaptado a la actividad para su funcionamiento.
 *  https://www.youtube.com/watch?v=IjNa5LyieaE
 * ************************************************************************************************/

/***************************************************************************************************
 * NOMBRE DE LA CLASE: CloseFragment
 * FUNCIONALIDAD DE DONDE VIENE: HomeActivity
 * FUNCIONALIDAD HACIA DONDE VA: Ningún lado
 * DESCRIPCIÓN: Pantalla con un botón de salir. Cuando el usuario toca el botón la aplicación se
 * cierra.
 * ************************************************************************************************/


import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.Button;

import com.example.lockdownlife.R;


public class CloseFrag extends Fragment {

    private CloseViewModel closeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        closeViewModel =
                ViewModelProviders.of(this).get(CloseViewModel.class);
        View root = inflater.inflate(R.layout.close_frag, container, false);

        Button boton = root.findViewById(R.id.boton_salir);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarDialogo();

            }
        });
        return root;
    }

    private void mostrarDialogo() {

        AlertDialog.Builder show = new AlertDialog.Builder(getContext());
        show.setTitle("¿Desea salir de la aplicación?");
        show.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        show.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Mensaje", "Se canceló la acción");
            }
        });
        AlertDialog alertDialog = show.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#353544")));
        alertDialog.show();
    }
}
