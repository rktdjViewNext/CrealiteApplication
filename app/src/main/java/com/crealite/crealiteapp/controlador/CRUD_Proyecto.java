package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Presupuesto;
import com.crealite.crealiteapp.modelo.Proyecto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Proyecto {

    private ArrayList<Proyecto> proyectos;
    private CRUD_Clientes crud_clientes;


    public CRUD_Proyecto() {
        proyectos = new ArrayList<>();
        this.crud_clientes = new CRUD_Clientes();
        obtenerTodosProyectos(new ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Proyecto> proyectos) {
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public void insertarProyecto(JSONObject jsonParam, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/insertarProyecto.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8")));
                    out.print(jsonParam.toString());
                    out.flush();
                    out.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        return true;
                    } else {
                        Log.e(Constantes.TAG, "Server returned non-OK status: insertar" + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e(Constantes.TAG, "Error sending request", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (callback != null) {
                    callback.onComplete(result, proyectos);
                }
            }
        }.execute();
    }

    public boolean addProyecto(Proyecto proyecto, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("nombre", proyecto.getNombre());
            jsonParam.put("pagado", proyecto.isPagado());
            jsonParam.put("cliente_id", proyecto.getCliente().getId());
            insertarProyecto(jsonParam, callback);
            return true;
        } catch (Exception e) {
            Log.e("CRUD_PROYECTOS", "Error adding project", e);
            return false;
        }
    }


    @SuppressLint("StaticFieldLeak")
    public void obtenerTodosProyectos(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerProyectosAll.php");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        String responseString = response.toString();
                        Log.d("CRUD_Proyecto", "Server response: " + responseString);
                        JSONObject jsonResponse = new JSONObject(responseString);
                        if (jsonResponse.getString("status").equals("success")) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            proyectos.clear(); // Limpiar la lista actual
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject proyectoJson = data.getJSONObject(i);
                                int id = proyectoJson.getInt("id");
                                String nombre = proyectoJson.getString("nombre");

                                boolean pagado = proyectoJson.getInt("pagado") == 1;
                                int clienteId = 0 ;
                               try {
                                   clienteId = proyectoJson.getInt("cliente_id");
                               }catch (Exception e){

                               }



                                boolean finalizado = proyectoJson.getInt("finalizado") == 1;

                                JSONObject presupuestoJson = proyectoJson.getJSONObject("presupuesto");
                                Presupuesto presupuesto = null;
                                try{
                                   presupuesto = new Presupuesto(

                                            presupuestoJson.getInt("id"),
                                            presupuestoJson.getDouble("subtotal"),
                                            presupuestoJson.getDouble("iva"),
                                            presupuestoJson.getDouble("total"),
                                            presupuestoJson.getInt("pagado") == 1
                                    );
                                }catch (Exception e){

                                }


                                Proyecto proyecto = new Proyecto(id, nombre, pagado, presupuesto, crud_clientes.searchById(clienteId), finalizado);
                                proyectos.add(proyecto);
                            }
                            Log.d("CRUD_Proyecto", "Proyectos con presupuestos obtenidos correctamente");
                            return true;
                        } else {
                            String errorMessage = jsonResponse.getString("message");
                            Log.e("CRUD_Proyecto", "Error al obtener proyectos: " + errorMessage);
                            return false;
                        }
                    } else {
                        Log.e("CRUD_Proyecto", "Server returned non-OK status: " + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e("CRUD_Proyecto", "Error al obtener proyectos", e);
                    return false;
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    callback.onComplete(success, proyectos);
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void actualizarProyectoPagado(int proyectoId, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/modificarProyectoPagado.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("proyecto_id", proyectoId);

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8")));
                    out.print(jsonParam.toString());
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

                        String responseString = response.toString();
                        Log.d("CRUD_Proyecto", "Server response: " + responseString);
                        JSONObject jsonResponse = new JSONObject(responseString);
                        if (jsonResponse.getString("status").equals("success")) {
                            Proyecto proyecto = searchById(proyectoId);
                            if (proyecto != null) {
                                proyecto.setPagado(true);
                            }
                            return true;
                        } else {
                            String errorMessage = jsonResponse.getString("message");
                            Log.e("CRUD_Proyecto", "Error al actualizar proyecto: " + errorMessage);
                            return false;
                        }
                    } else {
                        Log.e("CRUD_Proyecto", "Server returned non-OK status: " + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e("CRUD_Proyecto", "Error al actualizar proyecto", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    callback.onComplete(success, proyectos);
                }
            }
        }.execute();
    }

    public Proyecto search(int proyectoId) {
        for (Proyecto p:proyectos
             ) {
            if (p.getId() == proyectoId) return p;
        }
        return null;
    }

    public Proyecto searchByName(Proyecto proyecto){
        for (Proyecto p:proyectos
             ) {
            if (proyecto.getNombre().equals(p.getNombre()) && p.getCliente().getId() == proyecto.getCliente().getId()){
                return p;
            }
        }
        return null;
    }

    public ArrayList<Proyecto> obtenerProyectosPagadosCliente(Cliente cliente) {
        ArrayList<Proyecto>proyectosPagados = new ArrayList<>();
        for (Proyecto p:proyectos
             ) {
            if (p.getCliente() != null && p.getCliente().getId() == cliente.getId()){
                if (p.isPagado()){
                    proyectosPagados.add(p);
                }
            }
        }
        return proyectosPagados;
    }

    public ArrayList<Proyecto> obtenerProyectosFinalizadosCliente(Cliente cliente) {

        ArrayList<Proyecto>proyectosPagados = new ArrayList<>();
        for (Proyecto p:proyectos
        ) {
            if (p.getCliente() != null && p.getCliente().getId() == cliente.getId()){
                if (p.getFinalizado()){
                    proyectosPagados.add(p);
                }
            }
        }
        return proyectosPagados;
    }

    public interface ResponseCallback {
        void onComplete(boolean success, List<Proyecto> proyectos);
    }


    public Proyecto searchById(int id){
        for (Proyecto p:proyectos
             ) {
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public void listarProyectos(){
        for (Proyecto p:proyectos
             ) {
            System.out.println(p);
        }
    }

    public ArrayList<Proyecto> searchByCliente( Cliente c){
        ArrayList<Proyecto>proyectosClientes = new ArrayList<>();

        for (Proyecto p: proyectos){

            if (p.getCliente() != null && p.getCliente().getId() == c.getId()){
                proyectosClientes.add(p);
            }
        }

        return proyectosClientes;
    }

    public ArrayList<Proyecto> ultimosCinco(Cliente c){
        ArrayList<Proyecto>proyectosClientes = new ArrayList<>();
        int contador = 0;
        for (int i = proyectos.size()-1; i >= 0 ; i--) {

            if (proyectos.get(i).getCliente() != null && proyectos.get(i).getCliente().getId() == c.getId()){
                proyectosClientes.add(proyectos.get(i));
                contador++;
                if (contador == 6){
                    break;
                }
            }
        }
        return proyectosClientes;
    }
}
