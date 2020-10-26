package com.example.lockdownlife.Views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lockdownlife.R;


public class AboutActivity extends AppCompatActivity {

    TextView text_auth, text_description;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("aquiii", "dentro about");
        setContentView(R.layout.about);

        text_auth = findViewById(R.id.et_auth_name);
        text_description = findViewById(R.id.et_auth);
        iv = findViewById(R.id.iv_about);

    }
}
