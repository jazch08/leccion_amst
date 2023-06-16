package com.example.leccion_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void buscarHeroe(View view){
        EditText txtBusqueda = findViewById(R.id.txtBusqueda);
        String textoBusqueda = txtBusqueda.getText().toString();

        Intent intent = new Intent(this,Busqueda_heroes.class);
        intent.putExtra("nombreHeroe", textoBusqueda);

        startActivity(intent);
    }
}