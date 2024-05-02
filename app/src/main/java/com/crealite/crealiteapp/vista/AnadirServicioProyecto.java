package com.crealite.crealiteapp.vista;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.google.android.material.button.MaterialButton;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Arrays;

public class AnadirServicioProyecto extends AppCompatActivity {
    Spinner spinner;
    Button btnFotografia,btnFilmmaking,btnDiseno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.anadir_servicio_proyecto);



        initComponents();



        String [] value = {"BODA", "COMUNION", "STREET", "DJ", "FESTIVAL","DISCOTECA","PRODUCTO","BODA", "COMUNION", "STREET", "DJ", "FESTIVAL","DISCOTECA","PRODUCTO"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(value));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner,arrayList);

        spinner.setAdapter(arrayAdapter);
        
        
        
        
        cambiarTipoDeServicioListener();
    }

    private void initComponents() {
        spinner = findViewById(R.id.spinner);
        btnDiseno = findViewById(R.id.btnServicioDisenoAdd);
        btnFilmmaking = findViewById(R.id.btnServicioFilmmakingAdd);
        btnFotografia = findViewById(R.id.btnServicioFotografiaAdd);

    }

    private void cambiarTipoDeServicioListener() {

        btnFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFotografia.setBackground(getDrawable(R.drawable.orange2_button_bg));
                btnFilmmaking.setBackground(getDrawable(R.drawable.orange_button_bg));
                btnDiseno.setBackground(getDrawable(R.drawable.orange_button_bg));

            }
        });



        btnFilmmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnFotografia.setBackground(getDrawable(R.drawable.orange_button_bg));
                btnFilmmaking.setBackground(getDrawable(R.drawable.orange2_button_bg));
                btnDiseno.setBackground(getDrawable(R.drawable.orange_button_bg));

            }
        });



        btnDiseno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btnFotografia.setBackground(getDrawable(R.drawable.orange_button_bg));
                btnFilmmaking.setBackground(getDrawable(R.drawable.orange_button_bg));
                btnDiseno.setBackground(getDrawable(R.drawable.orange2_button_bg));
            }
        });
    }
}
