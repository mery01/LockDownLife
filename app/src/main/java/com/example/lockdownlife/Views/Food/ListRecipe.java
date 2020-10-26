package com.example.lockdownlife.Views.Food;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lockdownlife.Adapter.Food.FoodAdapter;
import com.example.lockdownlife.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListRecipe extends AppCompatActivity {

    private ProgressDialog progress_dialog;
    private String LINK = "http://www.recipepuppy.com/api/?i=";
    private String QUERY = "&q=";
    private ArrayList<Recipe> list;
    private RequestQueue request_queue;
    private ListView list_view;
    private FoodAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.food_recipe_list);

        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Carga lenta...");
        progress_dialog.show();

        String url = null;

        String ingredients = getIntent().getStringExtra("ingredients").toLowerCase();
        String key_words = getIntent().getStringExtra("key_words").toLowerCase();


        if (!ingredients.equals("") && !key_words.equals("")) {
            String url_new = LINK + ingredients + QUERY + key_words;
            url = url_new;
        } else {
            url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3";
        }


        list = new ArrayList<Recipe>();
        request_queue = Volley.newRequestQueue(this);
        GetRecipe(url);

    }

    public void GetRecipe(String url){

        JsonObjectRequest object_request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray array = response.getJSONArray("results");
                    for (int i = 0; i <= array.length() - 1; ++i) {

                        JSONObject object = array.getJSONObject(i);

                        String title = object.getString("title");
                        Log.d("Aquiii",title);
                        String ingredients = object.getString("ingredients");
                        String thumbnail = object.getString("thumbnail");
                        String href = object.getString("href");


                        Recipe recipe = new Recipe(title, ingredients, thumbnail, href);

                        recipe.setTitle(title);
                        Log.d("AQUIIII SET",recipe.getTitle());
                        recipe.setIngredients("List of all the ingredients needed: \n\n" + ingredients);
                        recipe.setThumbnail(thumbnail);
                        recipe.setHref(href);

                        list.add(recipe);
                        Log.d("aquiii",String.valueOf(list.size()));

                        adapter = new FoodAdapter(ListRecipe.this,R.layout.food_content, list);
                        list_view = findViewById(R.id.list_view);
                        list_view.setAdapter(adapter);
                    }

                    adapter.notifyDataSetChanged();
                    progress_dialog.cancel();

                } catch (JSONException exception) {
                    Log.d("Aquiii", "dentro catch");

                    exception.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
            }
        });

        request_queue.add(object_request);

    }
}
