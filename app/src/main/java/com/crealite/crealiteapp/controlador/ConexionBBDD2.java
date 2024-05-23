package com.crealite.crealiteapp.controlador;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionBBDD2 {

    private static final String TAG = "DatabaseConnection";
    private static final String SERVER_URL = "http://172.16.178.168/Crealite/";

    @SuppressLint("StaticFieldLeak")
    public void sendHttpRequest(String endpoint, String method, JSONObject jsonParam, ResponseCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(SERVER_URL + endpoint);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");

                    if (method.equals("POST") && jsonParam != null) {
                        conn.setDoOutput(true);
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8")));
                        out.print(jsonParam.toString());
                        out.flush();
                        out.close();
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        return response.toString();
                    } else {
                        Log.e(TAG, "Server returned non-OK status: " + responseCode);
                        return null;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error sending request", e);
                    return null;
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError("No response received from server.");
                }
            }
        }.execute();
    }

    public interface ResponseCallback {
        void onSuccess(String response);
        void onError(String error);
    }
}
