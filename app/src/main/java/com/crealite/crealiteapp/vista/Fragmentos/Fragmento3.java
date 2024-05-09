package com.crealite.crealiteapp.vista.Fragmentos;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.crealite.crealiteapp.R;



public class Fragmento3 extends Fragment {


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_fragmento3, container, false);


        TextView presupuestoServicios = view.findViewById(R.id.txtPresupuestoServicios);
        TextView presupuestoTrabajadores = view.findViewById(R.id.txtPresupuestoEmpleados);
        TextView precioTotal = view.findViewById(R.id.txtPrecioTotal);
        presupuestoServicios.setText("SERVICIO  FOTOGRAFIA ---------------  700€" + Html.fromHtml("<br />" ) + "SERVICIO  VIDEO ---------------  700€" + Html.fromHtml("<br />" ) + Html.fromHtml("<br />" ) + " SERVICIOS: 1400€");
        presupuestoTrabajadores.setText("SERVICIO  FOTOGRAFIA ---------------  700€" + Html.fromHtml("<br />" ) + "SERVICIO  VIDEO ---------------  700€" + Html.fromHtml("<br />" ) + Html.fromHtml("<br />" ) + " SERVICIOS: 1400€ " +  Html.fromHtml("<br />" ) + "SERVICIO  FOTOGRAFIA ---------------  700€" + Html.fromHtml("<br />" ) + "SERVICIO  VIDEO ---------------  700€" + Html.fromHtml("<br />" ) + Html.fromHtml("<br />")  + " SERVICIOS: 1400€");




        return view;
    }
}