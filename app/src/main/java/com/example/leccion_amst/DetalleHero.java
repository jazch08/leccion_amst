package com.example.leccion_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetalleHero extends AppCompatActivity {
    private String id_hero;

    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_hero);
        mQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        this.id_hero = intent.getStringExtra("id_hero");
        System.out.println(id_hero);

        busquedaId();

    }

    public void busquedaId(){
        String url_temp = "https://www.superheroapi.com/api.php/925571252058726/"+this.id_hero;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            TextView txt_nombreHero = findViewById(R.id.txt_nombreHero);
                            TextView txt_nombreRealHero = findViewById(R.id.txt_nombreRealHero);
                            ImageView imageHero = findViewById(R.id.img_hero);

                            txt_nombreHero.setText(response.get("name").toString());

                            JSONObject bloque_powerstats = response.getJSONObject("powerstats");
                            JSONObject bloque_biography = response.getJSONObject("biography");
                            JSONObject bloque_image = response.getJSONObject("image");

                            String url_image = bloque_image.getString("url");

                            txt_nombreRealHero.setText(bloque_biography.getString("full-name"));
                            Picasso.get().load(url_image).into(imageHero);





                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);
    }
}