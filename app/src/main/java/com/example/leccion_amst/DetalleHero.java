package com.example.leccion_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DetalleHero extends AppCompatActivity {
    private String id_hero;
    private ArrayList barArrayList;

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

                            BarChart chartPowerstats = findViewById(R.id.chartPowerstats);

                            Iterator<String> iter = bloque_powerstats.keys();

                            barArrayList = new ArrayList();
                            ArrayList<String> arrayXlabels = new ArrayList<>();
                            float xvalue = 1f;

                            while (iter.hasNext()){
                                String iterString = iter.next().toString();
                                String valueS = bloque_powerstats.getString(iterString);
                                if(!valueS.equals("null")){
                                    arrayXlabels.add(iterString);
                                    System.out.println(iterString);
                                    float value = Float.parseFloat(valueS);
                                    System.out.println(value);
                                    barArrayList.add(new BarEntry(xvalue,value));
                                    xvalue+=1f;
                                }
                            }

                            System.out.println(barArrayList.toString());
                            System.out.println(arrayXlabels.toString());

                            BarDataSet barDataSet = new BarDataSet(barArrayList,"");
                            BarData barData = new BarData(barDataSet);
                            chartPowerstats.setData(barData);
                            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            barDataSet.setValueTextColor(Color.BLACK);
                            barDataSet.setValueTextSize(16f);
                            chartPowerstats.getDescription().setEnabled(false);
                            chartPowerstats.invalidate();

                            chartPowerstats.getXAxis().setValueFormatter(new IndexAxisValueFormatter(arrayXlabels));
                            chartPowerstats.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            chartPowerstats.getXAxis().setGranularity(1f);
                            chartPowerstats.getXAxis().setGranularityEnabled(true);





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