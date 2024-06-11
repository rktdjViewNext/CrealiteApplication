package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.crealite.crealiteapp.modelo.Cliente;
import com.crealite.crealiteapp.modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CRUD_Empleado {

    public CRUD_Empleado(){

    }

    @SuppressLint("StaticFieldLeak")
    public void obtenerEmpleadosPorServicio(final int servicioId, final ResponseCallback callback) {
        new AsyncTask<Void, Void, List<Empleado>>() {
            @Override
            protected List<Empleado> doInBackground(Void... voids) {
                List<Empleado> empleadosList = new ArrayList<>();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerEmpleadoServicio.php?servicio_id=" + servicioId);
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
                        Log.d("CRUD_EMPLEADOS", "Server response: " + responseString);

                        JSONArray jsonArray = new JSONArray(responseString);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String usuario = jsonObject.getString("usuario");
                            String contrasena = jsonObject.getString("contraseña");
                            String nombre = jsonObject.getString("nombre");
                            String apellidos = jsonObject.getString("apellidos");
                            String telefono = jsonObject.getString("telefono");
                            String correo = jsonObject.getString("correo");
                            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
                            boolean admin = jsonObject.getInt("admin") == 1;
                            String dni = jsonObject.getString("dni");
                            double precioHora = jsonObject.getDouble("precioHora");

                            Empleado empleado = new Empleado(id, usuario, contrasena, nombre, apellidos, telefono, correo, fechaNacimiento, admin, dni, precioHora,null);
                            empleadosList.add(empleado);
                        }
                        Log.d("CRUD_EMPLEADOS", "Empleados obtenidos correctamente");
                    } else {
                        Log.e("CRUD_EMPLEADOS", "Server returned non-OK status: " + responseCode);
                    }
                } catch (Exception e) {
                    Log.e("CRUD_EMPLEADOS", "Error al obtener empleados", e);
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
                return empleadosList;
            }

            @Override
            protected void onPostExecute(List<Empleado> empleadosList) {
                if (callback != null) {
                    callback.onComplete(empleadosList);
                }
            }
        }.execute();
    }

    public interface ResponseCallback {
        void onComplete(List<Empleado> empleados);
    }
}
