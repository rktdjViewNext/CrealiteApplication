package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listServicioAdapter;
import com.crealite.crealiteapp.modelo.Servicio;

import java.util.ArrayList;

public class NewProyectActivity extends AppCompatActivity {

    ListView listaServiciosAcontratar;
    ListAdapter listAdapter;
    Servicio s;
    ArrayList<Servicio> servicios = new ArrayList<>();
    ImageButton btnBack, btnAddService, btnSolcitarPresupuesto;
    EditText etNombreProyecto;


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
            Intent intent = new Intent(NewProyectActivity.this, AnadirServicioProyecto.class);
            System.out.println(etNombreProyecto.getText().toString());
            intent.putExtra("NOMBRE_PROYECTO",etNombreProyecto.getText().toString());
            startActivity(intent);
        });

        //PULSAR CONFIRMAR PROYECTO
        btnSolcitarPresupuesto.setOnClickListener(v -> {

            Intent intent = new Intent(NewProyectActivity.this, ProyectViewActivity.class);
            intent.putExtra("NOMBRE_PROYECTO",etNombreProyecto.getText().toString());
            intent.putExtra("LISTA_SERVICIOS_CONTRATADOS",servicios);

            startActivity(intent);
        });
    }


    public void backAction(View view){
        getOnBackPressedDispatcher().onBackPressed();
    }
    private void initComponents() {
        btnBack = findViewById(R.id.btnBack);
        btnAddService = findViewById(R.id.btnAddServiceUnico);
        btnSolcitarPresupuesto = findViewById(R.id.btnSolicitarPresupuestoUnico);
        listaServiciosAcontratar = findViewById(R.id.listaSeviciosAContratar);
        etNombreProyecto = findViewById(R.id.editTextNombreProyecto);


    }

    private void cargarServiciosAcontratar() {

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            s = (Servicio) extras.get("SERVICIO");
            System.out.println(extras.getString("NOMBRE_PROYECTO"));
            etNombreProyecto.setText(extras.getString("NOMBRE_PROYECTO"));
            servicios.add(s);
        }



        listAdapter = new listServicioAdapter(NewProyectActivity.this,servicios);
        listaServiciosAcontratar.setAdapter(listAdapter);
        listaServiciosAcontratar.setClickable(true);
    }


}
