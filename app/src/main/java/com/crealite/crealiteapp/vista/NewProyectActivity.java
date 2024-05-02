package com.crealite.crealiteapp.vista;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listServicioAdapter;
import com.crealite.crealiteapp.databinding.NewProyectActivityBinding;
import com.crealite.crealiteapp.modelo.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;

public class NewProyectActivity extends AppCompatActivity {

    NewProyectActivityBinding binding;
    ListAdapter listAdapter;
    Servicio s;
    ArrayList<Servicio> servicios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_proyect_activity);

        binding = NewProyectActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        binding.listaSeviciosAContratar.setAdapter(listAdapter);
        binding.listaSeviciosAContratar.setClickable(true);

    }
}
