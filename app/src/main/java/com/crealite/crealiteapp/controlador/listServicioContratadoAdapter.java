package com.crealite.crealiteapp.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Servicio;

import java.util.ArrayList;

public class listServicioContratadoAdapter extends ArrayAdapter<Servicio> {
    public listServicioContratadoAdapter(@NonNull Context context, ArrayList<Servicio> servicios) {
        super(context, R.layout.list_item_servicio_contratado, servicios);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Servicio s = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_servicio_contratado,parent,false);

        }

        TextView tipoServicio = convertView.findViewById(R.id.tipoServicioItem);
        TextView fechaServicio = convertView.findViewById(R.id.fechaServicioItem);
        TextView subTipoServicio = convertView.findViewById(R.id.subTipoServicio);
        TextView horasContratadas = convertView.findViewById(R.id.horasContratadas);
        AppCompatButton btnDescripcion = convertView.findViewById(R.id.btnDescripción);
        TextView localidad = convertView.findViewById(R.id.txtLocalidad);
        tipoServicio.setText(s.getTipo());
        fechaServicio.setText(s.getFecha().toString());

        return convertView;
    }
}
