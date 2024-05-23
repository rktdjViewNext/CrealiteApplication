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
import java.util.concurrent.CompletableFuture;

public class ConexionBBDD {
    private static final String TAG = "ConexionBBDD";
    private static final String SERVER_URL = "http://172.16.178.168/Crealite/";

    @SuppressLint("StaticFieldLeak")
    public CompletableFuture<JSONObject> sendPostRequest(String endpoint, JSONObject jsonParam) {
        CompletableFuture<JSONObject> future = new CompletableFuture<>();
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(SERVER_URL + endpoint);
                    conn = (HttpURLConnection) url.openConnection();
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
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        return new JSONObject(response.toString());
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
            protected void onPostExecute(JSONObject result) {
                if (result != null) {
                    future.complete(result);
                } else {
                    future.completeExceptionally(new Exception("No response received from server."));
                }
            }
        }.execute();
        return future;
    }
}
