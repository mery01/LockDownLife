package com.example.lockdownlife.Views;

/***************************************************************************************************
 * NOMBRE DE LA CLASE: HomeActivity
 * FUNCIONALIDAD DE DONDE VIENE: MainActivity
 * FUNCIONALIDAD HACIA DONDE VA: ui (close, dayreg, home, rules, share)
 * DESCRIPCIÓN: Se trata del menú principal de la aplicación. Incluye un menu vertical desplegable.
 * REFERENCIAS:
 *  *  https://developer.android.com/guide/index.html
 *  *  Implementado de forma automática con el Navigation Drawer Activity de Android Studio y modificado para
 *  *  adaptarse a la función requerida.
 *
 * ************************************************************************************************/


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.Menu;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lockdownlife.R;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Para cambiar el texto a blanco y poner los iconos en su color natural.
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navigationView.setItemIconTintList(null);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona la seleccion de opciones en el menú
        int id = item.getItemId();
        if (id == R.id.action_About) {
            about();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void about(){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
