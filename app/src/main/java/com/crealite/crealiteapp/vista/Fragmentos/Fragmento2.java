package com.crealite.crealiteapp.vista.Fragmentos;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.controlador.listEstadoProyecto;
import com.crealite.crealiteapp.modelo.EstadoProyecto;
import java.util.ArrayList;


public class Fragmento2 extends Fragment {
    private ListAdapter listAdapter;
    private ArrayList<EstadoProyecto> procesosProyectos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento2, container, false);
        ListView lvProcesosProyecto = view.findViewById(R.id.lvProcesoServicio);
        listAdapter = new listEstadoProyecto(getActivity(),procesosProyectos);
        lvProcesosProyecto.setAdapter(listAdapter);
        lvProcesosProyecto.setClickable(true);

        return view;
    }

    public ArrayList<EstadoProyecto> getProcesosProyectos() {
        return procesosProyectos;
    }

    public void setProcesosProyectos(ArrayList<EstadoProyecto> procesosProyectos) {
        this.procesosProyectos = procesosProyectos;
    }
}