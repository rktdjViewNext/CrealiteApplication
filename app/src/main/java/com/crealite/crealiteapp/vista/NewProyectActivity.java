package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listServicioAdapter;
import com.crealite.crealiteapp.databinding.NewProyectActivityBinding;
import com.crealite.crealiteapp.modelo.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;

public class NewProyectActivity extends AppCompatActivity {

    ListView listaServiciosAcontratar;
    ListAdapter listAdapter;
    Servicio s;
    ArrayList<Servicio> servicios = new ArrayList<>();
    ImageButton btnBack, btnAddService, btnSolcitarPresupuesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_proyect_activity);

        initComponents();
        cargarServiciosAcontratar();
        listeners();




    }

    private void listeners() {
        //PULSAR AÑADIR SERVICIO
        btnAddService.setOnClickListener(v -> {
            System.out.println("hola");
            Intent intent = new Intent(NewProyectActivity.this, AnadirServicioProyecto.class);
            startActivity(intent);
        });

        //PULSAR CONFIRMAR PROYECTO
        btnSolcitarPresupuesto.setOnClickListener(v -> {
            System.out.println("hulaa");
            Intent intent = new Intent(NewProyectActivity.this, ProyectViewActivity.class);
            startActivity(intent);
        });
    }


    public void IniciarSesion(View view){
        System.out.println("adios");
        Intent intent = new Intent(NewProyectActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
    private void initComponents() {
        btnBack = findViewById(R.id.btnBack);
        btnAddService = findViewById(R.id.btnAddServiceUnico);
        btnSolcitarPresupuesto = findViewById(R.id.btnSolicitarPresupuestoUnico);
        listaServiciosAcontratar = findViewById(R.id.listaSeviciosAContratar);


    }

    private void cargarServiciosAcontratar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,2,11));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,1,19));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,10,14));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,4));
            servicios.add(s);
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,3,24));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,4,26));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,12));
            servicios.add(s);
        }


        listAdapter = new listServicioAdapter(NewProyectActivity.this,servicios);
        listaServiciosAcontratar.setAdapter(listAdapter);
        listaServiciosAcontratar.setClickable(true);
    }
}
