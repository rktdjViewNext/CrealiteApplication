package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.crealite.crealiteapp.modelo.Diseno;
import com.crealite.crealiteapp.modelo.Fotografia;
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

    private ArrayList<Servicio> servicios;
    private CRUD_Proyecto crudProyecto;
    private ArrayList<Fotografia> fotografias;
    private ArrayList<Video> videos;

    public CRUD_Servicios() {
        servicios = new ArrayList<>();
        crudProyecto = new CRUD_Proyecto();
    }


    @SuppressLint("StaticFieldLeak")
    public void obtenerDisenosAll(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerDisenosAll.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    JSONObject jsonResponse = new JSONObject(content.toString());
                    if (jsonResponse.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("disenos");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Diseno diseno = new Diseno();
                            diseno.setId(jsonObject.getInt("id"));
                            diseno.setDimensiones(jsonObject.getString("dimensiones"));
                            Boolean animado = false;
                            if (jsonObject.getInt("animado") == 1) animado = true;
                            diseno.setAnimado(animado);
                            diseno.setTipo(jsonObject.getString("tipoServicioDiseno"));

                            // Atributos de la clase padre Servicio
                            diseno.setPrecioServicio((float) jsonObject.getDouble("precioServicio"));
                            diseno.setDescripcion(jsonObject.getString("descripcion"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                diseno.setFechaRealizar(LocalDate.parse(jsonObject.getString("fechaRealizar")));
                            }
                            diseno.setDuracion(jsonObject.getInt("duracion"));
                            diseno.setLocalidad(jsonObject.getString("localidad"));
                            diseno.setProvincia(jsonObject.getString("provincia"));
                            diseno.setEmpleadosNecesarios(jsonObject.getInt("empleadosNecesarios"));
                            Proyecto proyecto = crudProyecto.search(jsonObject.getInt("proyecto_id"));
                            diseno.setProyecto(proyecto);

                            servicios.add(diseno);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    callback.onComplete(success, servicios);
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void obtenerFotografiasAll(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerFotografiasAll.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();
                    System.out.println(content.toString());
                    JSONObject jsonResponse = new JSONObject(content.toString());
                    if (jsonResponse.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonResponse.getJSONArray("fotografias");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Fotografia fotografia = new Fotografia();
                            fotografia.setId(jsonObject.getInt("id"));
                            fotografia.setCantidadFotos(jsonObject.getInt("catidadFotos"));
                            fotografia.setTipo(jsonObject.getString("tipoServicioFotografia"));
                            fotografia.setPrecioServicio((float) jsonObject.getDouble("precioServicio"));
                            fotografia.setDescripcion(jsonObject.getString("descripcion"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                fotografia.setFechaRealizar(LocalDate.parse(jsonObject.getString("fechaRealizar")));
                            }
                            fotografia.setDuracion(jsonObject.getInt("duracion"));
                            fotografia.setLocalidad(jsonObject.getString("localidad"));
                            fotografia.setProvincia(jsonObject.getString("provincia"));
                            fotografia.setEmpleadosNecesarios(jsonObject.getInt("empleadosNecesarios"));
                            Proyecto proyecto = crudProyecto.search(jsonObject.getInt("proyecto_id"));
                            boolean finalizado = false;
                            if (jsonObject.getInt("finalizado") == 1) finalizado = true;
                            fotografia.setFinalizano(finalizado);
                            fotografia.setProyecto(proyecto);
                            servicios.add(fotografia);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    obtenerDisenosAll(callback);
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void obtenerVideosAll(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerVideosAll.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);

                    }
                    in.close();
                    conn.disconnect();
                    servicios.clear();
                    JSONArray jsonArray = new JSONArray(content.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Video video = new Video();
                        video.setId(jsonObject.getInt("id"));
                        video.setDuracionVideo((float) jsonObject.getDouble("duracionVideo"));
                        boolean makingOff = false;
                        if (jsonObject.getInt("makingOff") == 1) makingOff = true;
                        video.setMakingOff(makingOff);
                        video.setTipo(jsonObject.getString("tipoServicioVideos"));
                        video.setPrecioServicio((float) jsonObject.getDouble("precioServicio"));
                        video.setDescripcion(jsonObject.getString("descripcion"));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            video.setFechaRealizar(LocalDate.parse(jsonObject.getString("fechaRealizar")));
                        }
                        video.setDuracion(jsonObject.getInt("duracion"));
                        video.setLocalidad(jsonObject.getString("localidad"));
                        video.setProvincia(jsonObject.getString("provincia"));
                        video.setEmpleadosNecesarios(jsonObject.getInt("empleadosNecesarios"));
                        Proyecto proyecto = crudProyecto.search(jsonObject.getInt("proyecto_id"));
                        video.setProyecto(proyecto);

                        servicios.add(video);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean sucess) {
                obtenerFotografiasAll(callback);
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void obtenerTodosServicios(final ResponseCallback callback) {
        servicios.clear();
       obtenerVideosAll(callback);
    }


    public ArrayList<Servicio> listarServiciosProyecto(Proyecto proyecto) {

        ArrayList<Servicio> serviciosProyecto = new ArrayList<>();

        for (Servicio servicio : servicios) {
            if (servicio.getProyecto().getId() == proyecto.getId()) {
                serviciosProyecto.add(servicio);
            }
        }

        for (Servicio s : serviciosProyecto) {
            System.out.println("PROYECTOS SELECIONADOS:" + s);
        }
        return serviciosProyecto;
    }


    public interface ResponseCallback {
        void onComplete(boolean success, List<Servicio> servicios);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertarService(JSONObject jsonParam, final ResponseCallback callback, String php) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + php);
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
            System.out.println("IMPORTADO: " + video.getProvincia());
            jsonParam.put("empleadosNecesarios", video.getEmpleadosNecesarios());
            jsonParam.put("proyecto_id", video.getProyecto().getId());
            jsonParam.put("duracionVideo", video.getDuracionVideo());
            jsonParam.put("makingOff", video.isMakingOff());
            jsonParam.put("tipoServicioVideos", video.getTipo());
            System.out.println(jsonParam.toString());
            insertarService(jsonParam, callback, "/insertarVideo.php");
            return true;
        } catch (Exception e) {
            Log.e("CRUD_VIDEOS", "Error adding video", e);
            return false;
        }
    }

    public boolean addDiseno(Diseno diseno, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("precioServicio", diseno.getPrecioServicio());
            jsonParam.put("descripcion", diseno.getDescripcion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaRealizar", diseno.getFechaRealizar().toString());
            }
            jsonParam.put("duracion", diseno.getDuracion());
            jsonParam.put("localidad", diseno.getLocalidad());
            jsonParam.put("provincia", diseno.getProvincia());
            jsonParam.put("empleadosNecesarios", diseno.getEmpleadosNecesarios());
            jsonParam.put("proyecto_id", diseno.getProyecto().getId());
            jsonParam.put("dimensiones", diseno.getDimensiones());
            jsonParam.put("animado", diseno.isAnimado());
            jsonParam.put("tipoServicioDiseno", diseno.getTipo());
            System.out.println(jsonParam.toString());
            insertarService(jsonParam, callback, "/insertarDiseno.php");
            return true;
        } catch (Exception e) {
            Log.e(Constantes.TAG, "Error adding diseno", e);
            return false;
        }
    }

    public boolean addFotografia(Fotografia fotografia, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("precioServicio", fotografia.getPrecioServicio());
            jsonParam.put("descripcion", fotografia.getDescripcion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaRealizar", fotografia.getFechaRealizar().toString());
            }
            jsonParam.put("duracion", fotografia.getDuracion());
            jsonParam.put("localidad", fotografia.getLocalidad());
            jsonParam.put("provincia", fotografia.getProvincia());
            jsonParam.put("empleadosNecesarios", fotografia.getEmpleadosNecesarios());
            jsonParam.put("proyecto_id", fotografia.getProyecto().getId());
            jsonParam.put("catidadFotos", fotografia.getCantidadFotos());
            jsonParam.put("tipoServicioFotografia", fotografia.getTipo());

            insertarService(jsonParam, callback, "/insertarFotografia.php");
            return true;
        } catch (Exception e) {
            Log.e("CRUD_FOTOGRAFIA", "Error adding fotografia", e);
            return false;
        }
    }

    public int numServiosProyecto(Proyecto p) {
        int numServicios = 0;
        for (Servicio s : servicios) {
            if (s.getProyecto().getId() == p.getId()) {
                numServicios++;
            }
        }

        System.out.println(numServicios);
        return numServicios;
    }

}
