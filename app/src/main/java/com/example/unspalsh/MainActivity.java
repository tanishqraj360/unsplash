package com.example.unspalsh;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import android.widget.Button;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    ImageView mDogImageView;
    Button nextDogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDogImageView = findViewById(R.id.dogImageView);
        nextDogButton = findViewById(R.id.nextDogButton);

        nextDogButton.setOnClickListener(view -> loadDogImage());

        loadDogImage();
    }

    private void loadDogImage()
    {
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);

        String url = "https://dog.ceo/api/breeds/image/random";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    String dogImageUrl;
                    try{
                        dogImageUrl = response.getString("message");
                        Glide.with(MainActivity.this).load(dogImageUrl).into(mDogImageView);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                },

                error -> {
                    Toast.makeText(MainActivity.this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show();
                    Log.e("MainActivity", "loadDogImage error: "+error.getLocalizedMessage());
                }
        );
        volleyQueue.add(jsonObjectRequest);
    }
}