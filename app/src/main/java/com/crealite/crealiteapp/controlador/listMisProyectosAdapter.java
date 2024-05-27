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
import androidx.appcompat.widget.AppCompatButton;

import com.crealite.crealiteapp.R;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.vista.ProyectViewActivity;

import java.util.ArrayList;
import java.util.List;

public class listMisProyectosAdapter extends ArrayAdapter<Proyecto> {

    Context context;
    CRUD_Servicios crudServicios;

    public listMisProyectosAdapter(@NonNull Context context, ArrayList<Proyecto> proyectos) {
        super(context, R.layout.list_item_mis_proyectos, proyectos);
        this.context = context;
        this.crudServicios = new CRUD_Servicios();
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Proyecto p = getItem(position);
        if (p.getNombre().equalsIgnoreCase("Ayuso -Feria Medellin")) p.setFinalizado(true);
        if (p.getNombre().equalsIgnoreCase("Juanma Garcia - Palapa")) p.setPagado(true);


        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mis_proyectos,parent,false);

        }


        TextView nombre = convertView.findViewById(R.id.txtNombrePr);
        TextView numServicios = convertView.findViewById(R.id.txtNumServicios);
        TextView pagado = convertView.findViewById(R.id.txtpagado);
        TextView finalizado = convertView.findViewById(R.id.txtFinalizado);
        AppCompatButton btnDescripcion = convertView.findViewById(R.id.btnDescripción);

        nombre.setText(p.getNombre());


        crudServicios.obtenerTodosServicios(new CRUD_Servicios.ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Servicio> servicios) {

                numServicios.setText(String.valueOf(crudServicios.numServiosProyecto(p)));
                if (!p.isPagado() ){
                    pagado.setText("NO");
                }else {
                    pagado.setText("SI");
                }

                if (p.getFinalizado()){
                    finalizado.setText("SI");
                }else{
                    finalizado.setText("NO");
                }
            }
        });




        btnDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProyectViewActivity.class);
                intent.putExtra(Constantes.EXTRA_PRYECTO,p);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
