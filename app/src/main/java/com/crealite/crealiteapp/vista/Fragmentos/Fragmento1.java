package com.crealite.crealiteapp.vista.Fragmentos;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listServicioContratadoAdapter;
import com.crealite.crealiteapp.modelo.Servicio;
import java.time.LocalDate;
import java.util.ArrayList;

public class Fragmento1 extends Fragment {

    ListAdapter listAdapter;
    Servicio s;
    ArrayList<Servicio> servicios = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_fragmento1, container, false);

        ListView lvServiciosContratados = view.findViewById(R.id.lvSeviciosContratados);



        listAdapter = new listServicioContratadoAdapter(getActivity(),servicios);
        lvServiciosContratados.setAdapter(listAdapter);
        lvServiciosContratados.setClickable(true);

        return view;

    }



    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }
}