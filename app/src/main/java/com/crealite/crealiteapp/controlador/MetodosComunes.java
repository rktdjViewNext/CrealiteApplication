package com.crealite.crealiteapp.controlador;

import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Fotografia;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

public class MetodosComunes {
    public static String getTipoServicio(Servicio s){
        if (s instanceof Fotografia){
            return "FOTOGRAFIA";
        }else if (s instanceof Video){
           return "VIDEO";
        }else{
            return "DISEÑO";
        }
    }

    public static String getSubTipoServicio(Servicio s){
        if (s instanceof Fotografia){
            return ((Fotografia) s).getTipo();
        }else if (s instanceof Video){
            return ((Video) s).getTipo();
        }else if (s instanceof Video){
            return ((Diseno) s).getTipo();
        }else{
            return "SERVICIO";
        }
    }
}
