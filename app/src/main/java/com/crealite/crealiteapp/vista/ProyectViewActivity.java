package com.crealite.crealiteapp.vista;


import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento1;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento2;
import com.crealite.crealiteapp.vista.Fragmentos.Fragmento3;
import com.crealite.crealiteapp.vista.Fragmentos.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ProyectViewActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private Fragmento1 fragmento1;
    private ArrayList<Servicio> serviciosContratados;
    private TextView tvNombreProyecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.proyect_view_activitys);

        //INICIAR TAB LAYOUT.
        initComponents();
        asignarFragmentosATabLayout();

        //RECOGER DATOS DEL ACTIVITY ANTERIOR
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        serviciosContratados = (ArrayList<Servicio>) extras.getSerializable("LISTA_SERVICIOS_CONTRATADOS");
        if (serviciosContratados != null){
            anadirServiciosContratados(serviciosContratados);
        }
        //MODIFICAR NOMBRE PROYECTO
        tvNombreProyecto.setText(extras.getCharSequence("NOMBRE_PROYECTO"));


    }

    private void anadirServiciosContratados(ArrayList<Servicio> serviciosContratados) {
        fragmento1.setServicios(serviciosContratados);
    }

    private void asignarFragmentosATabLayout() {
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFrament( fragmento1,"Servicios");
        vpAdapter.addFrament( new Fragmento2(), "Proceso");
        vpAdapter.addFrament( new Fragmento3(),"Presupuesto");

        viewPager.setAdapter(vpAdapter);
    }

    private void initComponents() {
        tableLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPage);
        tableLayout.setupWithViewPager(viewPager);
        fragmento1 = new Fragmento1();
        serviciosContratados = new ArrayList<>();
        tvNombreProyecto = findViewById(R.id.txtNombreProyecto);
    }
}
