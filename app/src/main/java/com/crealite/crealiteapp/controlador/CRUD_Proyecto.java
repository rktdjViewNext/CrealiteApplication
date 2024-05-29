package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.crealite.crealiteapp.modelo.Cliente;
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

    ArrayList<Proyecto> proyectos;
    CRUD_Clientes crud_clientes;

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
                        System.out.println(responseString);
                        Log.d("CRUD_Proyectos", "Server response: " + responseString);
                        JSONObject jsonResponse = new JSONObject(responseString);
                        if (jsonResponse.getString("status").equals("success")) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            proyectos.clear(); // Limpiar la lista actual
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject proyectoJson = data.getJSONObject(i);
                                int id = proyectoJson.getInt("id");
                                String nombre = proyectoJson.getString("nombre");

                                int pagadoInt = proyectoJson.getInt("pagado");
                                boolean pagado = false;
                                if (pagadoInt == 1) pagado = true;


                                //int presupuestoId = proyectoJson.getInt("presupuesto_id");
                                int clienteId = proyectoJson.getInt("cliente_id");

                                int finalizadoInt = proyectoJson.getInt("finalizado");
                                boolean finalizado = false;
                                if (finalizadoInt == 1) pagado = true;

                                Proyecto proyecto = new Proyecto(id, nombre, pagado, null, crud_clientes.searchById(clienteId),finalizado);
                                proyectos.add(proyecto);
                            }
                            Log.d("CRUD_Proyectos", "Proyectos obtenidos correctamente");
                            return true;
                        } else {
                            String errorMessage = jsonResponse.getString("message");
                            Log.e("CRUD_Proyectos", "Error al obtener proyectos: " + errorMessage);
                            return false;
                        }
                    } else {
                        Log.e("CRUD_Proyectos", "Server returned non-OK status: listar" + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e("CRUD_Proyectos", "Error al obtener proyectos", e);
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
            if (p.getCliente().getId() == cliente.getId()){
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
            if (p.getCliente().getId() == cliente.getId()){
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
            System.out.println(p);
            if (p.getCliente().getId() == c.getId()){
                proyectosClientes.add(p);
            }
        }

        for (Proyecto p:proyectosClientes
             ) {
            System.out.println("ESTOS SON MIS PROYECTOS" + p);
        }

        return proyectosClientes;
    }

    public ArrayList<Proyecto> ultimosCinco(Cliente c){
        ArrayList<Proyecto>proyectosClientes = new ArrayList<>();
        int contador = 0;
        for (int i = proyectos.size()-1; i >= 0 ; i--) {
            if (proyectos.get(i).getCliente().getId() == c.getId()){
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
