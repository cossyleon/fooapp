package com.example.memegen;
//push
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    /**
     * intent.
     */

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start2);
        start.setOnClickListener(unused -> startButtonClicked());
    }
    private void startButtonClicked() {
        setContentView(R.layout.activity_main);
        Button next = findViewById(R.id.nextImage);
        Button start = findViewById(R.id.start2);
        start.setVisibility(View.GONE);
        //next.setVisibility(View.VISIBLE);
        final TextView joke = (TextView) findViewById(R.id.joke);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://sv443.net/jokeapi/category/Programming";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject obj = response.getJSONObject(1);
                            joke.setText(response.get(3).toString());
                        } catch (JSONException e) {
                            System.out.println("Error");
                        }
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                joke.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        joke.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);
    }

    private void populate(final JsonObject result) {
        LinearLayout layout = findViewById(R.id.pictureLayout);
        View chunk = getLayoutInflater().inflate(R.layout.activity_main, layout, false);
        TextView joke = chunk.findViewById(R.id.joke);
        joke.setVisibility(View.VISIBLE);
        String type = result.get("type").getAsString();
        if (type == "single") {
            joke.setText(result.get("joke").getAsString());
        } else if (type == "twopart") {
            joke.setText(result.get("setup") + "" + result.get("delivery"));
        }

        layout.addView(chunk);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
