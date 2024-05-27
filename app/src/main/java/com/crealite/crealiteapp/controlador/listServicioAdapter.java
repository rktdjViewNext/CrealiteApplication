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

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

import java.util.ArrayList;

public class listServicioAdapter extends ArrayAdapter<Servicio> {
    public listServicioAdapter(@NonNull Context context, ArrayList<Servicio> servicios) {
        super(context, R.layout.list_item, servicios);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Servicio s = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        ImageButton btnRemoveItem = convertView.findViewById(R.id.btnRemove);
        TextView tipoServicio = convertView.findViewById(R.id.tipoServicioItem);
        TextView fechaServicio = convertView.findViewById(R.id.txtNumServicios);


        fechaServicio.setText(s.getFechaRealizar().toString());

        if (s instanceof Fotografia){
            tipoServicio.setText("FOTOGRAFIA");
        }else if (s instanceof Video){
            tipoServicio.setText("VIDEO");
        }else{
            tipoServicio.setText("DISEÑO");
        }

        btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(s);
            }
        });

        return convertView;
    }
}
