package com.example.luis.tassel;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager lManager;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        requestQueue = Volley.newRequestQueue(this);
        final Context context = this;
        final List items = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setHasFixedSize(true);

        lManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(lManager);

        String url2 = utilities.serverAddress+"/controllers/imageController.php?" +
                "action=GetAll";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.compareTo("Something is wrong with the images query") != 0) {
                            String[] imagesInfo = response.split(";");
                            for (int i = 0; i < imagesInfo.length; i++) {
                                String[] info = imagesInfo[i].split(":");
                                items.add(new image(Integer.parseInt(info[0]),info[1],Integer.parseInt(info[2])));
                            }
                            adapter = new imageAdapter(items);
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: \n" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest2);
    }
}
