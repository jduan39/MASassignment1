package com.justinduan.masassignment1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.net.URL;
public class register extends AppCompatActivity {
    private static final String TAG = register.class.getName();
    private Button btnRequest;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.thedogapi.com/v1/images/search";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRequest = (Button) findViewById(R.id.buttonRequest);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              try {
                                                  sendAndRequestResponse();
                                              } catch (JSONException ex) {
                                                  Log.i(TAG, "Error :" + ex.toString());
                                              }
                                          }
                                      }
        );


    }
    private void sendAndRequestResponse() throws JSONException {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    JSONObject jObject = jArray.getJSONObject(0);
                    String url = jObject.getString("url");
                    JSONArray breeds =new JSONArray(jObject.getString("breeds"));  ;
                    String name = "Unknown breed.";
                    if(breeds.length() != 0 ){
                        name = breeds.getJSONObject(0).getString("name");
                    }
                    Toast.makeText(getApplicationContext(), "This dog is a : " + name, Toast.LENGTH_LONG).show();//display the response on screen
                    URL img = new URL(url);
                    Bitmap bmp = BitmapFactory.decodeStream(img.openConnection().getInputStream());
                    imageView.setImageBitmap(bmp);
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
}
