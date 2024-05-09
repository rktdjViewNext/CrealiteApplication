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
import com.crealite.crealiteapp.controlador.listEstadoProyecto;
import com.crealite.crealiteapp.controlador.listServicioContratadoAdapter;
import com.crealite.crealiteapp.modelo.ProcesosProyecto;
import com.crealite.crealiteapp.modelo.Servicio;

import java.time.LocalDate;
import java.util.ArrayList;


public class Fragmento2 extends Fragment {
    ListAdapter listAdapter;
    ArrayList<ProcesosProyecto> procesosProyectos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento2, container, false);

        ListView lvProcesosProyecto = view.findViewById(R.id.lvProcesoServicio);

        procesosProyectos.add(new ProcesosProyecto("ADJUDICACION DE PROFESIONALES", "TERMINADO"));
        procesosProyectos.add(new ProcesosProyecto("GENERANDO PRESUPUESTO", "TERMINADO"));
        procesosProyectos.add(new ProcesosProyecto("PRIMER PAGO (70%)", "TERMINADO"));
        procesosProyectos.add(new ProcesosProyecto("EVENTO REALIZADO", "TERMINADO"));
        procesosProyectos.add(new ProcesosProyecto("SERVICIO LISTO PARA ENTREGA", "EN PROCESO"));
        procesosProyectos.add(new ProcesosProyecto("SEGUNDO PAGO PAGO (30%)", "CANCELADO"));
        procesosProyectos.add(new ProcesosProyecto("FINALIZADO (30%)", "CANCELADO"));

        listAdapter = new listEstadoProyecto(getActivity(),procesosProyectos);
        lvProcesosProyecto.setAdapter(listAdapter);
        lvProcesosProyecto.setClickable(true);

        return view;
    }
}