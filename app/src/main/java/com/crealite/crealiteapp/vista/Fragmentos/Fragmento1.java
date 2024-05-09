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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,2,11));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,1,19));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,10,14));
            servicios.add(s);
            /*s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,4));
            servicios.add(s);
            s = new Servicio("Servicio Fotografia", LocalDate.of(2024,3,24));
            servicios.add(s);
            s = new Servicio("Servicio Diseño", LocalDate.of(2024,4,26));
            servicios.add(s);
            s = new Servicio("Servicio Filmmaking", LocalDate.of(2024,12,12));
            servicios.add(s);
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
            servicios.add(s);*/
        }


        listAdapter = new listServicioContratadoAdapter(getActivity(),servicios);
        lvServiciosContratados.setAdapter(listAdapter);
        lvServiciosContratados.setClickable(true);

        return view;

    }

}