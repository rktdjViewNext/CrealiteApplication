package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.crealite.crealiteapp.modelo.EstadoProyecto;
import com.crealite.crealiteapp.modelo.Proyecto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CRUD_EstadoProyecto  {

    private ArrayList<EstadoProyecto> estados;
    private CRUD_Proyecto crudProyecto;


    public CRUD_EstadoProyecto(Proyecto proyecto) {
        estados = new ArrayList<>();
        estados.add(new EstadoProyecto("ADJUDICACION DE PROFESIONALES", "EN PROCESO",proyecto));
        estados.add(new EstadoProyecto("GENERANDO PRESUPUESTO", "SIN INICIAR",proyecto));
        estados.add(new EstadoProyecto("PRIMER PAGO (70%)", "SIN INICIAR",proyecto));
        estados.add(new EstadoProyecto("EVENTO REALIZADO", "SIN INICIAR",proyecto));
        estados.add(new EstadoProyecto("SERVICIO LISTO PARA ENTREGA", "SIN INICAR",proyecto));
        estados.add(new EstadoProyecto("SEGUNDO PAGO PAGO (30%)", "SIN INICIAR",proyecto));
        estados.add(new EstadoProyecto("FINALIZADO", "SIN INICIAR",proyecto));
    }

    public CRUD_EstadoProyecto (){
        estados = new ArrayList<>();
        crudProyecto = new CRUD_Proyecto();
        obtenerEstadosProyecto(new ResponseCallback() {
            @Override
            public void onComplete(List<EstadoProyecto> estadosProyectos) {
                estados = (ArrayList<EstadoProyecto>) estadosProyectos;
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void obtenerEstadosProyecto(final ResponseCallback callback) {
        new AsyncTask<Void, Void, List<EstadoProyecto>>() {
            @Override
            protected List<EstadoProyecto> doInBackground(Void... voids) {
                List<EstadoProyecto> estadosProyectoList = new ArrayList<>();
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerEstadosProyectosAll.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            EstadoProyecto estadoProyecto = new EstadoProyecto();
                            estadoProyecto.setId(jsonObject.getInt("id"));
                            estadoProyecto.setProyecto(crudProyecto.search(jsonObject.getInt("proyecto_id")));
                            estadoProyecto.setNombre(jsonObject.getString("fase"));
                            estadoProyecto.setEstado(jsonObject.getString("estado"));
                            estadosProyectoList.add(estadoProyecto);
                        }
                    } else {
                        Log.e("ProyectoService", "Server returned non-OK status: " + responseCode);
                    }
                } catch (Exception e) {
                    Log.e("ProyectoService", "Error fetching project states", e);
                }
                return estadosProyectoList;
            }

            @Override
            protected void onPostExecute(List<EstadoProyecto> result) {
                if (callback != null) {
                    callback.onComplete(result);
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void insertarEstadoProyecto(final EstadoProyecto estadoProyecto, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/insertarEstadoProyecto.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("proyecto_id", estadoProyecto.getProyecto().getId());
                    jsonParam.put("fase", estadoProyecto.getNombre());
                    jsonParam.put("estado", estadoProyecto.getEstado());

                    OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(conn.getOutputStream()), "UTF-8");
                    out.write(jsonParam.toString());
                    out.flush();
                    out.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        JSONObject jsonResponse = new JSONObject(response.toString());
                        if (jsonResponse.getString("status").equals("success")) {
                            return true;
                        } else {
                            Log.e("ProyectoService", "Server returned error: " + jsonResponse.getString("message"));
                            return false;
                        }
                    } else {
                        Log.e("ProyectoService", "Server returned non-OK status: " + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e("ProyectoService", "Error inserting project state", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (callback != null) {
                    callback.onComplete(estados);
                }
            }
        }.execute();
    }


    public interface ResponseCallback {
        void onComplete(List<EstadoProyecto> estadosProyectos);

    }


    public ArrayList<EstadoProyecto> getEstados(){
        return estados;
    }

    public ArrayList<EstadoProyecto> searchEstadoProyecto(Proyecto p){
        ArrayList<EstadoProyecto>estadoProyecto = new ArrayList<>();

        for (EstadoProyecto estado: estados
             ) {
            if (estado.getProyecto().getId() == p.getId()){
                estadoProyecto.add(estado);
            }
        }
        return estadoProyecto;
    }

}
