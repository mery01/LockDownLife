package com.example.lockdownlife.Views.ui.home;

/***************************************************************************************************
 * NOMBRE DE LA CLASE: HomeFragment
 * FUNCIONALIDAD DE DONDE VIENE: HomeActivity
 * FUNCIONALIDAD HACIA DONDE VA: AddNote, Exercise, Water, Transport, Recycle, Weather
 * DESCRIPCIÓN: Pantalla Inicio. Este fragment de la actividad HomeActivity tiene la función de pasar a una de las
 * actividades seleccionadas con las que el usuario puede comenzar a guardar los datos de las
 * acciones que éste considere que ha realizadp. Es básicamente la pantalla principal que da acceso
 * a todas las actividades que proporcionan los puntos al usuario.
 * REFERENCIAS:
 *  https://developer.android.com/guide/index.html
 *  Implementado de forma automática con el Navigation Drawer Activity de Android Studio y modificado para
 *  adaptarse a la función requerida.
 * ************************************************************************************************/
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lockdownlife.Views.Calendar.CalendarActivity;
import com.example.lockdownlife.Views.Exercise.ExerciseActivity;
import com.example.lockdownlife.Views.Food.FoodActivity;
import com.example.lockdownlife.Views.List.ListActivity;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Relax.RelaxActivity;
import com.example.lockdownlife.Views.Sleep.SleepActivity;

public class HomeFrag extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_frag, container, false);

        final CardView button_sleep = root.findViewById(R.id.button_sleep);

        button_sleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent add_notes = new Intent(getActivity(), SleepActivity.class);
                startActivity(add_notes);
            }
        });

        final CardView button_list = root.findViewById(R.id.button_list);

        button_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent water = new Intent(getActivity(), ListActivity.class);
                startActivity(water);
            }
        });

        final CardView button_calendar = root.findViewById(R.id.button_calendar);

        button_calendar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent transport = new Intent(getActivity(), CalendarActivity.class);
                startActivity(transport);
            }
        });

        final CardView button_food = root.findViewById(R.id.button_food);

        button_food.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent recycle = new Intent(getActivity(), FoodActivity.class);
                startActivity(recycle);
            }
        });

        final CardView button_exercise = root.findViewById(R.id.button_exercise);

        button_exercise.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent exercise = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(exercise);
            }
        });

        final CardView button_relax = root.findViewById(R.id.button_relax);

        button_relax.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent weather = new Intent(getActivity(), RelaxActivity.class);
                startActivity(weather);
            }
        });
        return root;
    }
}