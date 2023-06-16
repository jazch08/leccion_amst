package com.example.leccion_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Busqueda_heroes extends AppCompatActivity {

    private String text_nombre;
    private String token = "925571252058726";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_heroes);
        mQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        this.text_nombre = (String) intent.getExtras().get("nombreHeroe");
        busqueda();
    }

    public void busqueda(){
        String url_temp = "https://superheroapi.com/api/925571252058726/search/"+this.text_nombre;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray array = response.getJSONArray("results");
                            System.out.println(array);
                            int cantidad = array.length();

                            TextView txtCantidad = findViewById(R.id.txtCantidad);
                            txtCantidad.setText(String.valueOf(cantidad));


                            for(int i=0 ; i<cantidad ; i++){
                                JSONObject hero = (JSONObject) array.get(i);
                                LinearLayout linearHeros = findViewById(R.id.linearHeros);
                                linearHeros.setOrientation(LinearLayout.VERTICAL);
                                TextView hero_name = new TextView(getApplicationContext());
                                hero_name.setText(hero.get("name").toString());
                                String id_hero = hero.get("id").toString();
                                hero_name.setClickable(true);
                                hero_name.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        detalle_hero(v,id_hero);
                                    }
                                });
                                linearHeros.addView(hero_name);


                            }


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

    public void detalle_hero(View view, String id){
        Intent intent = new Intent(this, DetalleHero.class);
        intent.putExtra("id_hero",id);
        startActivity(intent);
    }


}