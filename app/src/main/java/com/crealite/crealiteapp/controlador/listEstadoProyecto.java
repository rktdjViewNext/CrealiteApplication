package com.crealite.crealiteapp.controlador;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.ProcesosProyecto;
import com.crealite.crealiteapp.modelo.Servicio;

import java.util.ArrayList;

public class listEstadoProyecto extends ArrayAdapter<ProcesosProyecto> {
    public listEstadoProyecto(@NonNull Context context, ArrayList<ProcesosProyecto > procesosProyectos) {
        super(context, R.layout.list_item_estado_pryecto, procesosProyectos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ProcesosProyecto estado = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_estado_pryecto,parent,false);

        }

        TextView estadotxt = convertView.findViewById(R.id.txtEstadoProyecto);
        ImageView check = convertView.findViewById(R.id.imgCheck);


        estadotxt.setText(estado.getNombre());

        if (estado.getEstado().equals("TERMINADO")){
            check.setImageResource(R.drawable.baseline_check_circle_green_24);
        }else if(estado.getEstado().equals("EN PROCESO")){
            check.setImageResource(R.drawable.baseline_alarm_24);
        }else {
            check.setImageResource(R.drawable.baseline_cancel_red_24);
        }



        return convertView;
    }
}
