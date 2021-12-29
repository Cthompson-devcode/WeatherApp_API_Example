package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt_cityID, bt_getWeatherByID, bt_getWeatherCityName;
    EditText et_userCityInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_cityID = findViewById(R.id.btn_cityID);
        bt_getWeatherByID = findViewById(R.id.btn_weatherbyCity);
        bt_getWeatherCityName = findViewById(R.id.btn_weathbyCityName);

        et_userCityInput = findViewById(R.id.et_searchData);

        lv_weatherReport = findViewById(R.id.lv_weatherReport);

        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
        // Instantiate the RequestQueue.
        // RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.metaweather.com/api/location/search/?query=" + et_userCityInput.getText().toString();

        //listeners

        bt_cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
               String cityID = weatherDataService.getCityID(et_userCityInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                   @Override
                   public void onError(String message) {

                   }

                   @Override
                   public void onResponse(String cityID) {
                       Toast.makeText(MainActivity.this, cityID.toString(), Toast.LENGTH_SHORT).show();
                   }
               });

                //pasted this code into weather data

                String url ="https://www.metaweather.com/api/location/search/?query="+ et_userCityInput.getText().toString();


                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERRO:" + error, Toast.LENGTH_SHORT).show();
                        System.out.println("ERROR: " + error);
                    }
                });

                // Get a RequestQueue


// ...

// Add a request (in this example, called stringRequest) to your RequestQueue.
                mySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
// Request a string response from the provided URL.
              /*  StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(MainActivity.this, "that didn't work", Toast.LENGTH_SHORT).show();
                    }
                });



// Add the request to the RequestQueue.
                queue.add(stringRequest);


               // Toast.makeText(MainActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
               */

            }
        });


        bt_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              weatherDataService.getCityForecastbyID(et_userCityInput.getText().toString(), new WeatherDataService.ForecastByIDResponse() {
                  @Override
                  public void onError(String message) {
                      Toast.makeText(MainActivity.this, "YOU HAVE AN ERROR", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onResponse(List<WeatherReportModel> weatherReportModels) {

                      // put in a list view

                      ArrayAdapter arrayAdapter = new ArrayAdapter( MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                      lv_weatherReport.setAdapter(arrayAdapter);

                  }


              });

                //pasted this code into weather data



            }
        });


        bt_getWeatherCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataService.getCityForecastByName(et_userCityInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {

                        System.out.println(" YOU ARE GETTING AN ERROR!!!!!!!!!!!!");

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                            lv_weatherReport.setAdapter(arrayAdapter);
                    }
                });


            }
        });
    }
}