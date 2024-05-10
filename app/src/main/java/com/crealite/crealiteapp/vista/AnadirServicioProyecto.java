package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class AnadirServicioProyecto extends AppCompatActivity {
    private Spinner spinner;
    private Button btnFotografia,btnFilmmaking,btnDiseno,btnAddServicio;
    private ArrayList<String> tiposFotografia;
    private ArrayList<String> tiposFilmmaking;
    private ArrayList<String> tiposDiseno;
    private String tipoServicio;
    private ImageButton btnBack;
    private Servicio s;

    private String nombre_proyecto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.anadir_servicio_proyecto);
        initComponents();
        cargarDatosSpinnerServicios(tiposFotografia);
        cambiarTipoDeServicioListener();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nombre_proyecto = extras.getString("NOMBRE_PROYECTO");
            System.out.println(nombre_proyecto);
        }

        //PULSAR BOTON IR HACIA ATRAS.
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getOnBackPressedDispatcher().onBackPressed();

            }
        });

        //PULSA AÑADIR SERVICIO
        btnAddServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                if (item.equalsIgnoreCase("SELECCIONE TIPO FOTOGRAFIA") || item.equalsIgnoreCase("SELECCIONE EL TIPO DE FILMACIÓN") || item.equalsIgnoreCase("SELECCIONE TIPO DISEÑO")){
                    Toast.makeText(AnadirServicioProyecto.this, "SELECCIONTE UN TIPO", Toast.LENGTH_SHORT).show();
                }else {
                    s = new Servicio(tipoServicio, null);
                    iniciarConfiguracionServicio(s);
                }
            }
        });

    }

    private void iniciarConfiguracionServicio(Servicio s) {
        Intent intent = new Intent(this,ConfigurarServicioAnadir.class);
        intent.putExtra("SERVICIO",s);
        System.out.println(nombre_proyecto);
        intent.putExtra("NOMBRE_PROYECTO",nombre_proyecto);
        startActivity(intent);
    }


    private void initComponents() {
        spinner = findViewById(R.id.spinner);
        btnDiseno = findViewById(R.id.btnServicioDisenoAdd);
        btnFilmmaking = findViewById(R.id.btnServicioFilmmakingAdd);
        btnFotografia = findViewById(R.id.btnServicioFotografiaAdd);
        btnBack = findViewById(R.id.btnBack);
        tiposFilmmaking = new ArrayList<>();
        tiposFotografia = new ArrayList<>();
        tiposDiseno = new ArrayList<>();
        btnAddServicio = findViewById(R.id.btnAddServicio);
        iniciarArrayTiposServicos();
    }

    private void cargarDatosSpinnerServicios(ArrayList<String> tiposServicios){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner, tiposServicios);
        spinner.setAdapter(arrayAdapter);
    }



    private void iniciarArrayTiposServicos() {
        tipoServicio = "FOTOGRAFIA";

        tiposFotografia.add("SELECCIONE TIPO FOTOGRAFIA");
        tiposFotografia.add("BODA");
        tiposFotografia.add("COMUNION");
        tiposFotografia.add("EVENTO");
        tiposFotografia.add("DJ");
        tiposFotografia.add("DISCOTECA");
        tiposFotografia.add("URBANA");
        tiposFotografia.add("ESTUDIO");
        tiposFotografia.add("PRODUCTO");

        tiposDiseno.add("SELECCIONE TIPO DISEÑO");
        tiposDiseno.add("BANNER");
        tiposDiseno.add("PORTADA");
        tiposDiseno.add("FLYER");
        tiposDiseno.add("LOGO");
        tiposDiseno.add("OVERLAY");
        tiposDiseno.add("ANIMATION");

        tiposFilmmaking.add("SELECCIONE EL TIPO DE FILMACIÓN");
        tiposFilmmaking.add("BODA");
        tiposFilmmaking.add("COMUNION");
        tiposFilmmaking.add("DJ");
        tiposFilmmaking.add("EVENTO");
        tiposFilmmaking.add("DISCOTECA");
        tiposFilmmaking.add("VIDEOCLIP");
        tiposFilmmaking.add("SPOT PUBLICITARIO");
        tiposFilmmaking.add("NEGOCIO");
        tiposFilmmaking.add("MARCA PERSONAL");


    }

    private void cambiarTipoDeServicioListener() {

        btnFotografia.setOnClickListener(v -> {

            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFotografia,btnFilmmaking,btnDiseno);
            cargarDatosSpinnerServicios(tiposFotografia);
            tipoServicio = "FOTOGRAFIA";
        });



        btnFilmmaking.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFilmmaking,btnFotografia,btnDiseno);
            cargarDatosSpinnerServicios(tiposFilmmaking);
            tipoServicio = "FILLMAKING";
        });



        btnDiseno.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnDiseno,btnFotografia,btnFilmmaking);
            cargarDatosSpinnerServicios(tiposDiseno);
            tipoServicio = "DISENO";
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
