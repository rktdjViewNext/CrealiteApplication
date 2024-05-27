package com.crealite.crealiteapp.controlador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;
import com.crealite.crealiteapp.vista.ConfigurarServicioAnadirDiseno;
import com.crealite.crealiteapp.vista.ConfigurarServicioAnadirFotografia;
import com.crealite.crealiteapp.vista.ConfigurarServicioAnadirVideo;

import java.util.ArrayList;

public class listServicioContratadoAdapter extends ArrayAdapter<Servicio> {

    private Context context;
    private Servicio s;
    public listServicioContratadoAdapter(@NonNull Context context, ArrayList<Servicio> servicios) {
        super(context, R.layout.list_item_servicio_contratado, servicios);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

         s = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_servicio_contratado,parent,false);

        }



        TextView tipoServicio = convertView.findViewById(R.id.tipoServicioItem);
        TextView fechaServicio = convertView.findViewById(R.id.txtNumServicios);
        TextView subTipoServicio = convertView.findViewById(R.id.subTipoServicio);
        TextView horasContratadas = convertView.findViewById(R.id.horasContratadasServicio);
        AppCompatButton btnDescripcion = convertView.findViewById(R.id.btnDescripción);
        TextView localidad = convertView.findViewById(R.id.txtLocalidad);

        System.out.println("ESTA ES LA DURACION QUE SE ESCRIBE: " + s.getDuracion());
        fechaServicio.setText(s.getFechaRealizar().toString());
        tipoServicio.setText(MetodosComunes.getTipoServicio(s));
        subTipoServicio.setText(MetodosComunes.getSubTipoServicio(s));
        horasContratadas.setText(String.valueOf(s.getDuracion()));
        localidad.setText(s.getLocalidad());

        btnDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s instanceof Fotografia){
                    iniciarConfigrarServicio(ConfigurarServicioAnadirFotografia.class,position);
                }else if (s instanceof Video){
                    iniciarConfigrarServicio(ConfigurarServicioAnadirVideo.class,position);
                }else if (s instanceof Diseno){
                    iniciarConfigrarServicio(ConfigurarServicioAnadirDiseno.class,position);
                }else {
                    Toast.makeText(context, "NO ES NINGUNO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private void iniciarConfigrarServicio(Class<?> configurarServicioAnadir, int position){
        Intent intent = new Intent(context, configurarServicioAnadir);
        System.out.println("ESTA ES LA HORA CONTRATADA QUE SE LA PASA: " + s.getDuracion());
        intent.putExtra(Constantes.EXTRA_SERVICIO,getItem(position));
        context.startActivity(intent);
    }
}
