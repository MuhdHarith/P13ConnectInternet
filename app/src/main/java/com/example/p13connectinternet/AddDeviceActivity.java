package com.example.p13connectinternet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class AddDeviceActivity extends AppCompatActivity {

    private static final String TAG = AddDeviceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }

    void sendData(String name, double price) {
        String url = "https://mapp-harith.firebaseio.com/products.json";
        final String n = name;
        final double p = price;
        //request string response from provided url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //display first 500 char of response
                        Log.d(TAG, "Response is: "+response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Doesn't work!");
                    }
                }) {
                    //inner class of StringRequest
                    // format teh body content of Request to be sent to network
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            //set up json object
                            JSONObject obj = new JSONObject();
                            obj.put("name", n);
                            obj.put("price", p);

                            Log.d(TAG, obj.toString());
                            //send bytes
                            return obj.toString().getBytes();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                        return super.getBody();
                    }
                    //set content type to json
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                        //return super.gerBodyContentType();
                    }
                };
        //Add request to RequestQueue
        //reuse queue by setting it as static variable in MainActivity
        MainActivity.queue.add(stringRequest);
    }

    public void returnReply(View view) {
        String name = ((EditText) findViewById(R.id.edit_name)).getText().toString();
        Double price = Double.valueOf(((EditText) findViewById(R.id.edit_price)).getText().toString());

        sendData(name, price);

        Intent replyIntent = new Intent();
        //replyIntent.putExtra("com.example.android.ADD_NAME", name);
        //replyIntent.putExtra("com.example.android.ADD_PRICE", price);
        //replyIntent.putExtra(ProductListAdapter.EXTRA_ID, mId);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

}
