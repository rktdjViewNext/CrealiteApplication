package com.crealite.crealiteapp.vista;

import static com.crealite.crealiteapp.controlador.Constantes.TIPO_SERVICIO_DISENO;
import static com.crealite.crealiteapp.controlador.Constantes.TIPO_SERVICIO_FOTOGRAFIA;
import static com.crealite.crealiteapp.controlador.Constantes.TIPO_SERVICIO_VIDEO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

import java.util.ArrayList;

public class AnadirServicioProyecto extends AppCompatActivity {
    private Spinner spinner;
    private Button btnFotografia,btnFilmmaking,btnDiseno,btnAddServicio;
    private String tipoServicio;
    private ImageButton btnBack;
    private Servicio s;
    private Proyecto nuevoProyecto;
    private ArrayList<Servicio> servicios;
    private Cliente cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.anadir_servicio_proyecto);
        initComponents();
        cargarDatosSpinnerServicios();
        cambiarTipoDeServicioListener();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nuevoProyecto = (Proyecto) extras.get(Constantes.EXTRA_PRYECTO);
            servicios = (ArrayList<Servicio>) extras.getSerializable(Constantes.EXTRA_LISTA_SERVICIO);
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
                iniciarConfiguracionServicio();
            }
        });

    }

    private void iniciarConfiguracionServicio() {

        if (tipoServicio.equals("FILLMAKING")){
            Video video = new Video();
            video.setFinalizano(false);
            video.setTipo(spinner.getSelectedItem().toString());
            configurarServicio(video,TIPO_SERVICIO_VIDEO);
            Intent intent = new Intent(this, ConfigurarServicioAnadirVideo.class);
            intent.putExtra(Constantes.EXTRA_SERVICIO, video);
            intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
            intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
            startActivity(intent);
        }else if (tipoServicio.equals("FOTOGRAFIA")){
            Fotografia fotografia = new Fotografia();
            fotografia.setFinalizano(false);
            fotografia.setTipo(spinner.getSelectedItem().toString());
            configurarServicio(fotografia,TIPO_SERVICIO_FOTOGRAFIA);
            Intent intent = new Intent(this, ConfigurarServicioAnadirFotografia.class);
            intent.putExtra(Constantes.EXTRA_SERVICIO, fotografia);
            intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
            intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
            startActivity(intent);
        }else if (tipoServicio.equals("DISENO")){
            Diseno diseno = new Diseno();
            diseno.setFinalizano(false);
            diseno.setTipo(spinner.getSelectedItem().toString());
            configurarServicio(diseno,TIPO_SERVICIO_DISENO);
            Intent intent = new Intent(this, ConfigurarServicioAnadirDiseno.class);
            intent.putExtra(Constantes.EXTRA_SERVICIO, diseno);
            intent.putExtra(Constantes.EXTRA_PRYECTO,nuevoProyecto);
            intent.putExtra(Constantes.EXTRA_LISTA_SERVICIO,servicios);
            startActivity(intent);
        }


    }

    private void configurarServicio(Servicio servicio, String[] tipoServicioVideo) {

        for (int i = 0; i < tipoServicioVideo.length; i++) {
            if (tipoServicioVideo[i].equalsIgnoreCase(spinner.getSelectedItem().toString())){
                if (servicio instanceof Video){
                    System.out.println("SOY UN SERVICIO DE VIDEO");
                    switch (i){
                        case 0:
                            servicio.setPrecioServicio(800);
                            servicio.setEmpleadosNecesarios(3);
                            break;
                        case 1:
                            servicio.setPrecioServicio(600);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 2:
                            servicio.setPrecioServicio(200);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 3:
                            servicio.setPrecioServicio(800);
                            servicio.setEmpleadosNecesarios(5);
                            break;
                        case 4:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 5:
                            servicio.setPrecioServicio(100);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 6:
                            servicio.setPrecioServicio(500);
                            servicio.setEmpleadosNecesarios(4);
                            break;
                        case 7:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 8:
                            servicio.setPrecioServicio(200);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 9:
                            servicio.setPrecioServicio(500);
                            servicio.setEmpleadosNecesarios(4);
                            break;
                    }
                } else if (servicio instanceof Fotografia){
                    System.out.println("SOY UN SERVICIO DE FOTO");
                    switch (i){
                        case 0:
                            servicio.setPrecioServicio(800);
                            servicio.setEmpleadosNecesarios(3);
                            break;
                        case 1:
                            servicio.setPrecioServicio(600);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 2:
                            servicio.setPrecioServicio(200);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 3:
                            servicio.setPrecioServicio(800);
                            servicio.setEmpleadosNecesarios(5);
                            break;
                        case 4:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 5:
                            servicio.setPrecioServicio(100);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 6:
                            servicio.setPrecioServicio(500);
                            servicio.setEmpleadosNecesarios(4);
                            break;
                        case 7:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 8:
                            servicio.setPrecioServicio(200);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 9:
                            servicio.setPrecioServicio(500);
                            servicio.setEmpleadosNecesarios(4);
                            break;
                        case 10:
                            servicio.setPrecioServicio(100);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 11:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                    }
                } else if (servicio instanceof Diseno){
                    System.out.println("SOY UN SERVICIO DE DISEÑO");
                    switch (i){
                        case 0:
                            servicio.setPrecioServicio(50);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 1:
                            servicio.setPrecioServicio(30);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 2:
                            servicio.setPrecioServicio(60);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 3:
                            servicio.setPrecioServicio(70);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 4:
                            servicio.setPrecioServicio(150);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 5:
                            servicio.setPrecioServicio(200);
                            servicio.setEmpleadosNecesarios(2);
                            break;
                        case 6:
                            servicio.setPrecioServicio(300);
                            servicio.setEmpleadosNecesarios(3);
                            break;
                        case 7:
                            servicio.setPrecioServicio(70);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 8:
                            servicio.setPrecioServicio(30);
                            servicio.setEmpleadosNecesarios(1);
                            break;
                        case 9:
                            servicio.setPrecioServicio(25);
                            servicio.setEmpleadosNecesarios(1);
                            break;

                    }
                }
            }
        }

        System.out.println("EMPLEADOS NECESARIOS: " + servicio.getEmpleadosNecesarios());


    }


    private void initComponents() {
        spinner = findViewById(R.id.spinner);
        btnDiseno = findViewById(R.id.btnServicioDisenoAdd);
        btnFilmmaking = findViewById(R.id.btnServicioFilmmakingAdd);
        btnFotografia = findViewById(R.id.btnServicioFotografiaAdd);
        btnBack = findViewById(R.id.btnBack);
        btnAddServicio = findViewById(R.id.btnAddServicio);
        tipoServicio = "FOTOGRAFIA";

    }

    private void cargarDatosSpinnerServicios(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_spinner, TIPO_SERVICIO_FOTOGRAFIA);
        spinner.setAdapter(arrayAdapter);
    }


    private void cambiarTipoDeServicioListener() {

        btnFotografia.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFotografia,btnFilmmaking,btnDiseno);
            spinner.setAdapter(new ArrayAdapter<>(this,R.layout.style_spinner, TIPO_SERVICIO_FOTOGRAFIA));
            tipoServicio = "FOTOGRAFIA";
        });



        btnFilmmaking.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnFilmmaking,btnFotografia,btnDiseno);
            spinner.setAdapter(new ArrayAdapter<>(this,R.layout.style_spinner, TIPO_SERVICIO_VIDEO));
            tipoServicio = "FILLMAKING";
        });



        btnDiseno.setOnClickListener(v -> {
            //CAMBIAR COLOR FONDO BOTONES
            cambiarColorBotones(btnDiseno,btnFotografia,btnFilmmaking);
            spinner.setAdapter(new ArrayAdapter<>(this,R.layout.style_spinner, TIPO_SERVICIO_DISENO));
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
