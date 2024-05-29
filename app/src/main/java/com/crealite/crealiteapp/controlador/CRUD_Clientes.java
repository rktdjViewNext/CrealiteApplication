package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.crealite.crealiteapp.modelo.Cliente;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream;


import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.Arrays;
import java.util.List;

public class CRUD_Clientes {
    private List<Cliente> clientesList;

    public CRUD_Clientes() {
        clientesList = new ArrayList<>();
        obtenerTodosClientes(new ResponseCallback() {
            @Override
            public void onComplete(boolean success, List<Cliente> clientes) {
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void obtenerTodosClientes(final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Boolean doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/obtenerClientesAll.php");
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
                        Log.d("CRUD_CLIENTES", "Server response: " + responseString);

                        JSONObject jsonResponse = new JSONObject(responseString);

                        if (jsonResponse.getString("status").equals("success")) {
                            JSONArray data = jsonResponse.getJSONArray("data");

                            clientesList.clear(); // Limpiar la lista actual
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject clienteJson = data.getJSONObject(i);
                                int id = clienteJson.getInt("id");
                                String usuario = clienteJson.getString("usuario");
                                String contrasena = clienteJson.getString("contraseña");
                                String nombre = clienteJson.getString("nombre");
                                String apellidos = clienteJson.getString("apellidos");
                                String telefono = clienteJson.getString("telefono");
                                String correo = clienteJson.getString("correo");
                                LocalDate fechaNacimiento = null;
                                fechaNacimiento = LocalDate.parse(clienteJson.getString("fechaNacimiento"));

                                boolean admin = false;
                                String profesion = clienteJson.getString("profesion");
                                String ciudad = clienteJson.getString("ciudad");

                                byte[] imageBytes = null;
                                try {
                                    String fotoBase64 = clienteJson.getString("foto");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        // Decodificar la cadena Base64
                                        imageBytes = java.util.Base64.getDecoder().decode(fotoBase64);
                                        System.out.println("Image bytes: " + Arrays.toString(imageBytes));
                                    } else {
                                        // Para versiones anteriores a Android O, usa Base64.decode
                                        imageBytes = android.util.Base64.decode(fotoBase64, android.util.Base64.DEFAULT);
                                        System.out.println("Image bytes: " + Arrays.toString(imageBytes));
                                    }


                                } catch (IllegalArgumentException | JSONException e) {
                                    Log.e("CRUD_CLIENTES", "Error decoding photo from Base64", e);
                                }




                                Cliente cliente = new Cliente(id, usuario, contrasena, nombre, apellidos, telefono, correo, fechaNacimiento, admin, imageBytes, profesion, ciudad);
                                clientesList.add(cliente);
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
                    callback.onComplete(success, clientesList);
                }
            }
        }.execute();
    }



    @SuppressLint("StaticFieldLeak")
    public void insertarCliente(JSONObject jsonParam, final ResponseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(Constantes.SERVER_URL + "/insertarCliente.php");
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
                    callback.onComplete(result, clientesList);
                }
            }
        }.execute();
    }

    public boolean add(Cliente element, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("usuario", element.getUsuario());
            jsonParam.put("contraseña", element.getContrasena());
            jsonParam.put("nombre", element.getNombre());
            jsonParam.put("apellidos", element.getApellidos());
            jsonParam.put("telefono", element.getTelefono());
            jsonParam.put("correo", element.getCorreo());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaNacimiento", element.getFechaNacimiento().toString());
            }
            jsonParam.put("admin", false);
            jsonParam.put("profesion", element.getProfesion());
            jsonParam.put("ciudad", element.getCiudad());
            insertarCliente(jsonParam, callback);
            return true;
        } catch (Exception e) {
            Log.e("CRUD_CLIENTES", "Error adding client", e);
            return false;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public boolean update(Cliente element, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", element.getId());
            jsonParam.put("usuario", element.getUsuario());
            jsonParam.put("contraseña", element.getContrasena());
            jsonParam.put("nombre", element.getNombre());
            jsonParam.put("apellidos", element.getApellidos());
            jsonParam.put("telefono", element.getTelefono());
            jsonParam.put("correo", element.getCorreo());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jsonParam.put("fechaNacimiento", element.getFechaNacimiento().toString());
            }
            jsonParam.put("profesion", element.getProfesion());
            jsonParam.put("ciudad", element.getCiudad());

            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        URL url = new URL(Constantes.SERVER_URL + "/modificarCliente.php");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json; utf-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);

                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8")));
                        out.print(jsonParam);
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
                            System.out.println(response);
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
                        callback.onComplete(result, clientesList);
                    }
                }
            }.execute();

            return true;
        } catch (Exception e) {
            Log.e("CRUD_CLIENTES", "Error updating client", e);
            return false;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public boolean updateFotoPerfil(int clienteId, Bitmap foto, final ResponseCallback callback) {
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", clienteId);
            jsonParam.put("foto", bitmapToBase64(foto)); // Convierte la imagen Bitmap a base64

            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    HttpURLConnection conn = null;
                    try {
                        URL url = new URL(Constantes.SERVER_URL + "/subirFotoCliente.php");
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json; utf-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);

                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8")));
                        out.print(jsonParam);
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
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (callback != null) {
                        callback.onComplete(result, clientesList);
                    }
                }
            }.execute();

            return true;
        } catch (Exception e) {
            Log.e("CRUD_CLIENTES", "Error updating profile photo", e);
            return false;
        }
    }

    // Método auxiliar para convertir Bitmap a Base64
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }else {
            return null;
        }
    }



    public interface ResponseCallback {
        void onComplete(boolean success, List<Cliente> clientes);
    }


    public List<Cliente> getClientesList() {
        return clientesList;
    }


    public Cliente search(Cliente element) {
        for (Cliente cliente :clientesList) {
            if (cliente.getUsuario().equals(element.getUsuario())){
                return cliente;
            }
        }
        return null;
    }

    public Cliente searchById(int id) {
        for (Cliente cliente :clientesList) {
            if (cliente.getId() == id) return cliente;
        }
        return null;
    }


    public boolean delete(Cliente element) {
        return false;
    }

    public List<Cliente> listAll() {
        return clientesList;
    }

    public void listarClientes(){
        for (Cliente c: clientesList){
            System.out.println(c);
        }
    }
}