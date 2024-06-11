package com.crealite.crealiteapp.controlador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.vista.ProyectViewActivity;

import java.util.ArrayList;

public class listUltimosProyectos extends ArrayAdapter<Proyecto> {
    private Context context;
    public listUltimosProyectos(@NonNull Context context, ArrayList<Proyecto> proyectos) {
        super(context, R.layout.list_item_ultimos_proyectos, proyectos);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       Proyecto p = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_ultimos_proyectos,parent,false);
        }

        TextView tipoServicio = convertView.findViewById(R.id.tipoServicioItem);
        TextView fechaServicio = convertView.findViewById(R.id.txtNumServicios);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProyectViewActivity.class);
                intent.putExtra(Constantes.EXTRA_PRYECTO,p);
                intent.putExtra("VERDADERO",true);
                context.startActivity(intent);
            }
        });

        if (p != null) {
            tipoServicio.setText(p.getNombre());
        }
        if (p != null) {
            fechaServicio.setText(p.getCliente().getNombre());
        }

        return convertView;
    }
}
