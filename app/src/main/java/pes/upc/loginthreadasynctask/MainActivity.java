package pes.upc.loginthreadasynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText usuario;
    EditText contraseña;
    EditText respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
        respuesta = (EditText) findViewById(R.id.respuesta);

    }

    //Función para hacer el Login
    public void sayHelloThreads(View view) {

        if (usuario.getText().toString().equals("") || contraseña.getText().toString().equals("")){
            respuesta.setText(("Te falta completar todos los datos."));
            Log.i("Login", "No datos");
        }

        else{
            new Thread(new Runnable() {
                InputStream stream = null;
                String str = "";
                String result = null;
                Handler handler = new Handler();
                public void run() {

                    try {
                        String query = String.format("http://10.0.2.2:9000/Application/loginAndroid");
                        URL url = new URL(query);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000 );
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.connect();

                        String params = "user=" + usuario.getText().toString() + "&password=" + contraseña.getText().toString();
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(params);

                        writer.flush();
                        writer.close();
                        os.close();

                        stream = conn.getInputStream();

                        BufferedReader reader;

                        StringBuilder sb = new StringBuilder();

                        reader = new BufferedReader(new InputStreamReader(stream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        result = sb.toString();

                        //Codi correcte
                        Log.i("Login", result);
                        handler.post(new Runnable() {
                            public void run() {
                                respuesta.setText(result);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    //Función para hacer el registro
    public void sayHelloAsynktask(View view) {

        if (usuario.getText().toString().equals("") || contraseña.getText().toString().equals("")){
            respuesta.setText(("Te falta completar todos los datos."));
            Log.i("Register", "No datos");
        }

        else
            new HelloMessage(this).execute("http://10.0.2.2:9000/Application/registrarAndroid?user=" + usuario.getText().toString() + "&password=" + contraseña.getText().toString());
    }

    private class HelloMessage extends AsyncTask<String, Void, String> {
        Context context;
        InputStream stream = null;
        String str = "";
        String result = null;

        private HelloMessage(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                String query = String.format(urls[0]);
                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();

                BufferedReader reader = null;

                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(stream));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();


                Log.i("Register", result);


                return result;

            } catch(IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            respuesta.setText( result);
        }
    }
}
