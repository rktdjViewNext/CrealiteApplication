package com.crealite.crealiteapp.vista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.crealite.crealiteapp.R;
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

        btnFotografia.setOnClickListener(v -> {

            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFotografia,btnFilmmaking,btnDiseno);
        });



        btnFilmmaking.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFilmmaking,btnFotografia,btnDiseno);
        });



        btnDiseno.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnDiseno,btnFotografia,btnFilmmaking);
        });
    }

    private void cambiarColorBotones(Button btnSelecionado, Button btnDeselecionado, Button btnDeselecionado2) {

        //CAMBIAR COLOR DE FONDO
        btnSelecionado.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.orange2_button_bg,null));
        btnDeselecionado.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.orange_button_bg,null));
        btnDeselecionado2.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.orange_button_bg,null));
        //CAMBIAR COLOR DEL TEXTO
        btnSelecionado.setTextColor(ResourcesCompat.getColor(getResources(),R.color.orange,null));
        btnDeselecionado.setTextColor(ResourcesCompat.getColor(getResources(),R.color.black,null));
        btnDeselecionado2.setTextColor(ResourcesCompat.getColor(getResources(),R.color.black,null));

    }
}
