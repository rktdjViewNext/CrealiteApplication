package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.crealite.crealiteapp.modelo.Proyecto;
import com.crealite.crealiteapp.modelo.Servicio;
import com.crealite.crealiteapp.modelo.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Servicios {

    private ArrayList<Servicio>  servicios;
    private CRUD_Proyecto crudProyecto;

    public CRUD_Servicios() {
        servicios = new ArrayList<>();
        crudProyecto = new CRUD_Proyecto();
        obtenerTodosServicios(new ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Servicio> servicios) {
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void insertarServicio(JSONObject jsonParam, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/insertarServicio.php");
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
                        Log.e(Constantes.TAG, "Server returned non-OK status: " + responseCode);
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
                    callback.onComplete(result, servicios);
                }
            }
        }.execute();
    }

    public boolean addServicio(Servicio servicio, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("precioServicio", servicio.getPrecioServicio());
            jsonParam.put("descripcion", servicio.getDescripcion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaRealizar", servicio.getFechaRealizar().toString());
            }
            jsonParam.put("duracion", servicio.getDuracion());
            jsonParam.put("localidad", servicio.getLocalidad());
            jsonParam.put("provincia", servicio.getProvincia());
            jsonParam.put("empleadosNecesarios", servicio.getEmpleadosNecesarios());
            jsonParam.put("proyecto_id", servicio.getProyecto().getId());
            insertarServicio(jsonParam, callback);
            return true;
        } catch (Exception e) {
            Log.e("CRUD_SERVICIOS", "Error adding service", e);
            return false;
        }
    }



    @SuppressLint("StaticFieldLeak")
    public void obtenerTodosServicios(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerServiciosAll.php");
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
                        Log.d("CRUD_Servicios", "Server response: " + responseString);

                        JSONObject jsonResponse = new JSONObject(responseString);
                        if (jsonResponse.getString("status").equals("success")) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            servicios.clear(); // Limpiar la lista actual
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject clienteJson = data.getJSONObject(i);
                                int id = clienteJson.getInt("id");
                                double precio = clienteJson.getDouble("precioServicio");
                                String descripcion = clienteJson.getString("descripcion");
                                LocalDate fecharArealizar = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                     fecharArealizar = LocalDate.parse(clienteJson.getString("fechaRealizar"));
                                }
                                int duracion = clienteJson.getInt("duracion");
                                String localidad = clienteJson.getString("localidad");
                                String provincia = clienteJson.getString("provincia");
                                int empleadosNecesarios = clienteJson.getInt("empleadosNecesarios");
                                Proyecto proyecto= crudProyecto.search(clienteJson.getInt("proyecto_id"));
                                Servicio s = new Servicio(id,precio,descripcion,fecharArealizar,duracion,localidad,provincia,empleadosNecesarios,proyecto);
                                servicios.add(s);
                            }
                            Log.d("CRUD_CLIENTES", "Clientes obtenidos correctamente");
                            return true;
                        } else {
                            String errorMessage = jsonResponse.getString("message");
                            Log.e("CRUD_CLIENTES", "Error al obtener clientes: " + errorMessage);
                            return false;
                        }
                    } else {
                        Log.e("CRUD_CLIENTES", "Server returned non-OK status: " + responseCode);
                        return false;
                    }
                } catch (Exception e) {
                    Log.e("CRUD_CLIENTES", "Error al obtener clientes", e);
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
                    callback.onComplete(success, servicios);
                }
            }
        }.execute();
    }


    public ArrayList<Servicio> listarServiciosProyecto(Proyecto proyecto) {
        ArrayList<Servicio> serviciosProyecto = new ArrayList<>();
        for (Servicio servicio: servicios) {
            if (servicio.getProyecto().getId() == proyecto.getId()){
                serviciosProyecto.add(servicio);
            }
        }

        for (Servicio s: serviciosProyecto) {
            System.out.println("PROYECTOS SELECIONADOS:" + s);
        }
        return serviciosProyecto;
    }

    public void add() {
    }


    public interface ResponseCallback {
        void onComplete(boolean success, List<Servicio> servicios);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertarVideo(JSONObject jsonParam, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/insertarVideo.php");
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
                        Log.e(Constantes.TAG, "Server returned non-OK status: " + responseCode);
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
                    callback.onComplete(result, null);
                }
            }
        }.execute();
    }

    public boolean addVideo(Video video, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("precioServicio", video.getPrecioServicio());
            jsonParam.put("descripcion", video.getDescripcion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaRealizar", video.getFechaRealizar().toString());
            }
            jsonParam.put("duracion", video.getDuracion());
            jsonParam.put("localidad", video.getLocalidad());
            jsonParam.put("provincia", video.getProvincia());
            jsonParam.put("empleadosNecesarios", video.getEmpleadosNecesarios());
            jsonParam.put("proyecto_id", video.getProyecto().getId());
            jsonParam.put("duracionVideo", video.getDuracionVideo());
            jsonParam.put("makingOff", video.isMakingOff());
            jsonParam.put("tipoServicioVideos", video.getTipo());
            insertarVideo(jsonParam, callback);
            return true;
        } catch (Exception e) {
            Log.e("CRUD_VIDEOS", "Error adding video", e);
            return false;
        }
    }

}
