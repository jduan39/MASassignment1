package com.justinduan.masassignment1;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String linkurl = "https://9xjijr3isf.execute-api.us-east-2.amazonaws.com/MASapi/username/";
    private boolean result;




    private void sendAndRequestResponse(String url) throws JSONException {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject apiJson = new JSONObject(response);
                    String count = apiJson.get("ScannedCount").toString();

                    if(count.equals("1")) {
                        result = true;
                    } else {
                        result = false;
                    }
                } catch (Exception ex) {
                    Log.i(TAG, "Error :" + ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final EditText username = (EditText) findViewById(R.id.username);
       final EditText password = (EditText) findViewById(R.id.password);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());
            }
        });


        final Button register = (Button) findViewById(R.id.homeregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String newlink = linkurl + user;
                try {
                    sendAndRequestResponse(newlink);
                } catch (JSONException ex) {
                    Log.i(TAG, "Error :" + ex.toString());
            }

            if (result == true) {
                Intent registerIntent = new Intent(MainActivity.this, register.class);
                startActivity(registerIntent);
            }
        }


    });



}
}
