package com.crealite.crealiteapp.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.CRUD_EstadoProyecto;
import com.crealite.crealiteapp.controlador.CRUD_Proyecto;
import com.crealite.crealiteapp.controlador.CRUD_Servicios;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.controlador.listServicioAdapter;
import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.EstadoProyecto;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

import java.util.ArrayList;
import java.util.List;

public class NewProyectActivity extends AppCompatActivity {
    private ListView listaServiciosAcontratar;
    private ListAdapter listAdapter;
    private ArrayList<Servicio> servicios;
    private ImageButton btnBack, btnAddService, btnSolcitarPresupuesto;
    private EditText etNombreProyecto;
    private Proyecto nuevoProyecto;
    private CRUD_Servicios crudServicios;
    private CRUD_Proyecto crudProyecto;

    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_proyect_activity);
        initComponents();

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            cliente = (Cliente) extras.get(Constantes.EXTRA_CLIENTE);

            nuevoProyecto = (Proyecto) extras.get(Constantes.EXTRA_PRYECTO);
            if (nuevoProyecto == null){
                nuevoProyecto = new Proyecto();
                nuevoProyecto.setCliente(cliente);
            }else{
                cliente = nuevoProyecto.getCliente();
            }

            /// if not null
            servicios= (ArrayList<Servicio>) extras.getSerializable(Constantes.EXTRA_LISTA_SERVICIO);
            System.out.println(servicios);
            if (servicios != null){
                for (Servicio s: servicios) {
                    System.out.println(s);
                }
            }else{
                servicios = new ArrayList<>();
            }

            etNombreProyecto.setText(nuevoProyecto.getNombre());
        }


        cargarServiciosAcontratar();
        listeners();

    }

    private void listeners() {
        //PULSAR AÑADIR SERVICIO
        btnAddService.setOnClickListener(v -> {
            Intent intent = new Intent(NewProyectActivity.this, AnadirServicioProyecto.class);
            nuevoProyecto.setNombre(etNombreProyecto.getText().toString());
            intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
            intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
            startActivity(intent);
        });

        //PULSAR CONFIRMAR PROYECTO
        btnSolcitarPresupuesto.setOnClickListener(v -> {
            nuevoProyecto = new Proyecto(etNombreProyecto.getText().toString(), false, null, nuevoProyecto.getCliente());
            if (!etNombreProyecto.getText().toString().isEmpty()){
                if (crudProyecto.searchByName(new Proyecto(etNombreProyecto.getText().toString(),cliente)) == null){
                    if (!servicios.isEmpty()){


                crudProyecto.addProyecto(nuevoProyecto, new CRUD_Proyecto.ResponseCallback() {
                    @Override
                    public void onComplete(boolean success, List<Proyecto> proyectos) {
                        crudProyecto.obtenerTodosProyectos(new CRUD_Proyecto.ResponseCallback() {
                            @Override
                            public void onComplete(boolean success, List<Proyecto> proyectos) {
                                System.out.println(crudProyecto.searchByName(nuevoProyecto));
                                crudProyecto.listarProyectos();
                                CRUD_EstadoProyecto crudEstadoProyecto = new CRUD_EstadoProyecto(crudProyecto.searchByName(nuevoProyecto));
                                for (EstadoProyecto estado:crudEstadoProyecto.getEstados()
                                ) {
                                    crudEstadoProyecto.insertarEstadoProyecto(estado, new CRUD_EstadoProyecto.ResponseCallback() {
                                        @Override
                                        public void onComplete(List<EstadoProyecto> estadosProyectos) {

                                        }
                                    });
                                }

                                for (Servicio servicio : servicios) {
                                    servicio.setProyecto(crudProyecto.searchByName(nuevoProyecto));
                                    if (servicio instanceof Fotografia) {

                                    } else if (servicio instanceof Video) {
                                        crudServicios.addVideo((Video) servicio, new CRUD_Servicios.ResponseCallback() {
                                            @Override
                                            public void onComplete(boolean success, List<Servicio> servicios) {
                                                System.out.println("VIDEO AÑADIDO");
                                            }
                                        });

                                    } else if (servicio instanceof Diseno) {

                                    }
                                }

                                Intent intent = new Intent(NewProyectActivity.this, ProyectViewActivity.class);
                                nuevoProyecto.setNombre(etNombreProyecto.getText().toString());
                                intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
                                intent.putExtra(Constantes.EXTRA_PRYECTO,crudProyecto.searchByName(nuevoProyecto));
                                startActivity(intent);
                            }
                        });
                    }
                });
                    }else {
                        Toast.makeText(this, "SELECCIONE ALGUN SERVICIO", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "YA TIENES UN PROYECTO CON ESE NOMBRE", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "INSERTE UN NOMBRE AL PROYECTO", Toast.LENGTH_SHORT).show();
            }

        });
    }


    public void backAction(View view){
        nuevoProyecto = null;
        getOnBackPressedDispatcher().onBackPressed();
    }
    private void initComponents() {
        btnBack = findViewById(R.id.btnBack);
        btnAddService = findViewById(R.id.btnAddServiceUnico);
        btnSolcitarPresupuesto = findViewById(R.id.btnSolicitarPresupuestoUnico);
        listaServiciosAcontratar = findViewById(R.id.listaSeviciosAContratar);
        etNombreProyecto = findViewById(R.id.editTextNombreProyecto);
        nuevoProyecto = new Proyecto();
        crudServicios = new CRUD_Servicios();
        crudProyecto = new CRUD_Proyecto();
    }

    private void cargarServiciosAcontratar() {
        if (servicios != null){
            listAdapter = new listServicioAdapter(NewProyectActivity.this, servicios);
            listaServiciosAcontratar.setAdapter(listAdapter);
            listaServiciosAcontratar.setClickable(true);
        }

    }


}
