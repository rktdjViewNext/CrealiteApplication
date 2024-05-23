package com.crealite.crealiteapp.vista;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.CRUD_EstadoProyecto;
import com.crealite.crealiteapp.controlador.CRUD_Servicios;
import com.crealite.crealiteapp.controlador.Constantes;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento1;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento2;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento3;
import com.crealite.crealiteapp.vista.Fragmentos.VPAdapter;
import com.google.android.material.tabs.TabLayout;


public class ProyectViewActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private Fragmento1 fragmento1;
    private Fragmento2 fragmento2;
    private TextView tvNombreProyecto;
    private Proyecto proyecto;
    private CRUD_Servicios crudServicios;
    private CRUD_EstadoProyecto crudEstadoProyecto;
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.proyect_view_activitys);

        //INICIAR TAB LAYOUT.
        initComponents();


        //RECOGER DATOS DEL ACTIVITY ANTERIOR
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            System.out.println("SIII HAY EXTRAS");
            proyecto = (Proyecto) extras.get(Constantes.EXTRA_PRYECTO);

        }

        crudServicios.obtenerTodosServicios((success, servicios) -> {
            fragmento1.setServicios(crudServicios.listarServiciosProyecto(proyecto));
            crudEstadoProyecto.obtenerEstadosProyecto(estadosProyectos -> {
                fragmento2.setProcesosProyectos(crudEstadoProyecto.searchEstadoProyecto(proyecto));
                asignarFragmentosATabLayout();
            });

        });

        //MODIFICAR NOMBRE PROYECTO
        tvNombreProyecto.setText(proyecto.getNombre());

        btnBack.setOnClickListener(v -> iniciarHomeActivity());

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                iniciarHomeActivity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);
        onBackPressedCallback.setEnabled(true);
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
        vpAdapter.addFrament( new Fragmento3(),"Presupuesto");

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
        crudEstadoProyecto = new CRUD_EstadoProyecto();
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);

    }

    private String sacarDetallesServicios(){
        StringBuilder detallesServicios = new StringBuilder();
        return null;

    }


}
