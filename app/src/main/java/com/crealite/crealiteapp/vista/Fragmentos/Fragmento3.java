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
import com.crealite.crealiteapp.controlador.CRUD_Empleado;
import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Empleado;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

import java.util.ArrayList;
import java.util.List;


public class Fragmento3 extends Fragment {

    private ArrayList<Servicio> servicios;
    private Proyecto proyecto;
    private CRUD_Empleado crudEmpleado = new CRUD_Empleado();
    private String listaEmpleado = "";
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_fragmento3, container, false);


        TextView presupuestoServicios = view.findViewById(R.id.txtPresupuestoServicios);
        TextView presupuestoTrabajadores = view.findViewById(R.id.txtPresupuestoEmpleados);
        TextView precioTotal = view.findViewById(R.id.txtPrecioTotal);
        TextView preciosubtotal = view.findViewById(R.id.txtSubtotal);
        TextView precioiva = view.findViewById(R.id.txtIVA);
        String listaServicio = "";


        for (Servicio s: servicios){
            System.out.println(s);
            if (s instanceof Fotografia){
                if (listaServicio.isEmpty()){
                    listaServicio = " FOTOGRAFIA ----------- " + s.getPrecioServicio();
                }else{
                    listaServicio = listaServicio + Html.fromHtml("<br />" ) + " FOTOGRAFIA ----------- " + s.getPrecioServicio();
                }

            }else if(s instanceof Video){
                if (listaServicio.isEmpty()){
                    listaServicio = " VIDEO ----------- " + s.getPrecioServicio();
                }else{
                    listaServicio = listaServicio + Html.fromHtml("<br />" ) + " FOTOGRAFIA ----------- " + s.getPrecioServicio();
                }
            }else if(s instanceof Diseno){
                if (listaServicio.isEmpty()){
                    listaServicio = " DISENO ----------- " + s.getPrecioServicio();
                }else{
                    listaServicio = listaServicio + Html.fromHtml("<br />" ) + " FOTOGRAFIA ----------- " + s.getPrecioServicio();
                }
            }

        }
        if (proyecto.getPresupuesto() != null){
            listaEmpleado = "";
            precioTotal.setText(proyecto.getPresupuesto().getTotal()+ "");
            preciosubtotal.setText(proyecto.getPresupuesto().getSubtotal()+ "");
            precioiva.setText(proyecto.getPresupuesto().getIva()+ "");

            presupuestoServicios.setText(listaServicio);
            presupuestoTrabajadores.setText("");
            for (Servicio se: servicios){

                crudEmpleado.obtenerEmpleadosPorServicio(se.getId(), new CRUD_Empleado.ResponseCallback() {
                    @Override
                    public void onComplete(List<Empleado> empleados) {

                        for (Empleado e: empleados){
                            if (listaEmpleado.isEmpty()){
                                double precioEmpleado = se.getDuracion() * e.getPrecioHora();
                                listaEmpleado = e.getNombre() + " " + e.getApellidos() + " ----------- " + se.getDuracion() + " x " + e.getPrecioHora() + " = " + precioEmpleado;
                                presupuestoTrabajadores.setText(listaEmpleado);
                            }else{
                                double precioEmpleado = se.getDuracion() * e.getPrecioHora();
                                listaEmpleado = listaEmpleado + Html.fromHtml("<br />" ) + e.getNombre() + " " + e.getApellidos() + " ----------- " + se.getDuracion() + " x " + e.getPrecioHora() + " = " + precioEmpleado;
                                presupuestoTrabajadores.setText(listaEmpleado);
                            }
                        }

                    }
                });
            }

        }else{
            presupuestoServicios.setText("PRESUPUESTO NO DISPONIBLE");
            presupuestoTrabajadores.setText("PRESUPUESTO NO DISPONIBLE");
        }
        return view;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public void setProyecto(Proyecto proyecto){
        this.proyecto = proyecto;
    }

}