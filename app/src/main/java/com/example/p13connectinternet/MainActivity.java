package com.example.p13connectinternet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    ProductListAdapter mAdapter;
    JSONObject items = new JSONObject();
    //store RequestQueue as static to share in app
    public static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add a floating action click handler for creating new entries.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start empty edit activity.
                Intent intent = new Intent(getBaseContext(), AddDeviceActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        queue = Volley.newRequestQueue(this);
        setupRecycler();
        connectToInternet();  //test yebhbf
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToInternet();
    }

    void connectToInternet() {
        //instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mapp-harith.firebaseio.com/products.json";

        //Request string response from URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Display first 500 characters of response String
                        Log.d(TAG, "Response is: "+response);

                        parseData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "That did not work.");
            }
        });
        //add request to request queue
        queue.add(stringRequest);
    }

    void parseData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            //once data is loaded, update adapter for RecyclerView
            mAdapter.setItems(jsonObject);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setupRecycler() {
        recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ProductListAdapter(this, items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
