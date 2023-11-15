package com.example.thayhuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> arrayCourse;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        arrayCourse = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayCourse);
        lv.setAdapter(adapter);

        new ReadJSON().execute("https://dummyjson.com/products");

    }
        private class ReadJSON extends  AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {

                StringBuilder content = new StringBuilder();


                try {
                    URL url = new URL(strings[0]);

                    InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line);
                    }
                    bufferedReader.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return content.toString();
            }

                @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("products");

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object1 = array.getJSONObject(i);

                        String title = object1.getString("title");
                        String description = object1.getString("description");
                        arrayCourse.add(title + " - " + description);
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }