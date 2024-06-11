package com.crealite.crealiteapp.vista;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.CRUD_Empleado;
import com.crealite.crealiteapp.controlador.CRUD_EstadoProyecto;
import com.crealite.crealiteapp.controlador.CRUD_Proyecto;
import com.crealite.crealiteapp.controlador.CRUD_Servicios;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Empleado;
import com.crealite.crealiteapp.modelo.EstadoProyecto;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento1;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento2;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento3;
import com.crealite.crealiteapp.vista.Fragmentos.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import java.net.ServerSocket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class ProyectViewActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private Fragmento1 fragmento1;
    private Fragmento2 fragmento2;
    private  Fragmento3 fragmento3;
    private TextView tvNombreProyecto;
    private Proyecto proyecto;
    private CRUD_Servicios crudServicios;
    private CRUD_EstadoProyecto crudEstadoProyecto;
    private CRUD_Empleado crudEmpleado;
    private CRUD_Proyecto crudProyecto;
    private ImageButton btnBack;
    private Button btnPagar;
    private boolean bandera;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.proyect_view_activitys);

        initComponents();


        //RECOGER DATOS DEL ACTIVITY ANTERIOR
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            proyecto = (Proyecto) extras.get(Constantes.EXTRA_PRYECTO);
            bandera = extras.getBoolean("VERDADERO");
        }

        //configurarBotonPagar();

        crudServicios.obtenerTodosServicios((success, servicios) -> {
            fragmento1.setServicios(crudServicios.listarServiciosProyecto(proyecto));
            fragmento3.setServicios(crudServicios.listarServiciosProyecto(proyecto));
            fragmento3.setProyecto(proyecto);
            configurarBotonPagar(proyecto);


            crudEstadoProyecto.obtenerEstadosProyecto(estadosProyectos -> {
                fragmento2.setProcesosProyectos(crudEstadoProyecto.searchEstadoProyecto(proyecto));
                asignarFragmentosATabLayout();

            });
        });

        //MODIFICAR NOMBRE PROYECTO
        tvNombreProyecto.setText(proyecto.getNombre());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bandera){
                    Intent intent = new Intent(ProyectViewActivity.this, HomePageActivity.class);
                    intent.putExtra(Constantes.EXTRA_CLIENTE,proyecto.getCliente());
                    intent.putExtra("VERDADERO",true);
                    startActivity(intent);
                }else{
                    getOnBackPressedDispatcher().onBackPressed();

                }
            }
        });


    }

    private void configurarBotonPagar(Proyecto proyecto) {
        //CONFIGURAR CRUD PRESUPUESTO
        System.out.println("ESTE ES EL PROYECTO" + proyecto.getPresupuesto());


        if (proyecto != null && proyecto.getPresupuesto() != null && !proyecto.isPagado()){
            btnPagar.setEnabled(true);
            btnPagar.setTextColor(this.getResources().getColor(R.color.white));
            btnPagar.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.orange2_button_bg,null));

            btnPagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnPagar.setText("PAGADO");
                    proyecto.setPagado(true);
                    btnPagar.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edittext_bg,null));
                    crudProyecto.actualizarProyectoPagado(proyecto.getId(), new CRUD_Proyecto.ResponseCallback() {
                        @Override
                        public void onComplete(boolean success, List<Proyecto> proyectos) {
                            crudEstadoProyecto.cambiarEstadoPagado(proyecto, new CRUD_EstadoProyecto.ResponseCallback() {
                                @Override
                                public void onComplete(List<EstadoProyecto> estadosProyectos) {
                                    Intent intent = new Intent(ProyectViewActivity.this, ProyectViewActivity.class);
                                    intent.putExtra(Constantes.EXTRA_PRYECTO,proyecto);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                    btnPagar.setEnabled(false);
                    Toast.makeText(ProyectViewActivity.this, "PAGO REALIZADO", Toast.LENGTH_SHORT).show();
                }
            });

        }
        if (proyecto != null && proyecto.getPresupuesto() != null && proyecto.isPagado()){
            btnPagar.setText("PAGADO");
            btnPagar.setEnabled(false);
            btnPagar.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edittext_bg,null));
        }



    }

    private void iniciarHomeActivity() {
        Intent intent = new Intent(ProyectViewActivity.this, HomePageActivity.class);
        intent.putExtra(Constantes.EXTRA_CLIENTE,proyecto.getCliente());
        startActivity(intent);
    }

    private void asignarFragmentosATabLayout() {
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFrament( fragmento1,"Servicios");
        vpAdapter.addFrament( fragmento2, "Proceso");
        vpAdapter.addFrament( fragmento3,"Presupuesto");

        viewPager.setAdapter(vpAdapter);
    }

    private void initComponents() {
        tableLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPage);
        tableLayout.setupWithViewPager(viewPager);
        fragmento1 = new Fragmento1();
        tvNombreProyecto = findViewById(R.id.txtNombreProyecto);
        crudServicios = new CRUD_Servicios();
        fragmento2 = new Fragmento2();
        fragmento3 = new Fragmento3();
        crudEstadoProyecto = new CRUD_EstadoProyecto();
        crudProyecto = new CRUD_Proyecto();
        crudEmpleado = new CRUD_Empleado();
        btnBack = findViewById(R.id.btnBack);
        btnPagar= findViewById(R.id.btnPagar);
        bandera = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);

    }


}
