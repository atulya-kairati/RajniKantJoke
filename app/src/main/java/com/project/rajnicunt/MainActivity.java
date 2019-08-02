package com.project.rajnicunt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView factText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factText = findViewById(R.id.factText);
        progressBar = findViewById(R.id.progressBar);

        refresh(factText);
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... urls) {

            String result ="";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return "Turn On The Internet";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jsonObject = new JSONObject(s);
                s = jsonObject.getString("value");

                jsonObject = new JSONObject(s);
                s = jsonObject.getString("joke");

                s = s.replace("&quot;"," ' ");
                s = s.replace("Norris","Kant");
                s = s.replace("America","India");
                s = s.replace("United States","India");
                s = s.replace("MacGyver","Aryabhatta");
                s = s.replace("Mr. T","Mithun");
                s = s.replace("?s","'s");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.w(">>>>>>>>", s );
            factText.setText(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void refresh(View v){

        progressBar.setVisibility(View.VISIBLE);
        DownloadTask task = new DownloadTask();
        task.execute("http://api.icndb.com/jokes/random?firstName=Rajni");

    }

}

