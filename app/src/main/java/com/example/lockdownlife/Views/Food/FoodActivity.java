package com.example.lockdownlife.Views.Food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Sleep.PopInfoSleep;


public class FoodActivity extends AppCompatActivity {

    private ProgressDialog progress_dialog;
    private Toolbar food_toolbar;
    private Button but_search;
    private EditText ingredients, key_words;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food);

        progress_dialog = new ProgressDialog(this);

        food_toolbar = findViewById(R.id.food_toolbar);
        setSupportActionBar(food_toolbar);
        food_toolbar.setTitle(R.string.title_activity_Food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        but_search = findViewById(R.id.but_search);
        ingredients = findViewById(R.id.et_ingredients);
        key_words = findViewById(R.id.et_term);


        but_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                progress_dialog.setMessage("Cargando Web...");
                progress_dialog.show();

                Intent intent = new Intent(FoodActivity.this, ListRecipe.class);
                intent.putExtra("ingredients", ingredients.getText().toString());
                intent.putExtra("key_words", key_words.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info_food) {
            Intent intent = new Intent(this, PopInfoFood.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
