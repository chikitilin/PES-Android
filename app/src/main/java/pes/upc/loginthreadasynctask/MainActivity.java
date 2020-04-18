package pes.upc.loginthreadasynctask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void sayHelloThreads(View view) {

        new Thread(new Runnable() {
            InputStream stream = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {


                try {

                    String query = String.format("http://192.168.1.40:9000/Application/loginAndroid");
                    //String query = String.format("http://10.192.171.29:9000/Application/hello");
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.connect();



                    String params = "user=fer&password=ferp";
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params.toString());

                    writer.flush();
                    writer.close();
                    os.close();

                    stream = conn.getInputStream();

                    BufferedReader reader = null;

                    StringBuilder sb = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();

                    // Mostrar resultat en el quadre de text.
                    // Codi incorrecte
                    // EditText n = (EditText) findViewById (R.id.edit_message);
                    //n.setText(result);

                    //Codi correcte
                    Log.i("lolaforms1", result);
                    handler.post(new Runnable() {
                        public void run() {
                            EditText n = (EditText) findViewById (R.id.editText2);
                            n.setText(result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void sayHelloAsynktask(View view) {
        new HelloMessage(this).execute("http://192.168.1.40:9000/Application/registrarAndroid?user=david&password=davidp" );
        //new HelloMessage(this).execute("http://10.192.171.29:9000/Application/hello" );
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


                Log.i("lolaforms1", result);


                return result;

            } catch(IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            EditText n = (EditText) findViewById (R.id.editText2);
            n.setText( result);

        }
    }

}
