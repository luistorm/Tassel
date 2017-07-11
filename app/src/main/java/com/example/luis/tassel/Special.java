package com.example.luis.tassel;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Special extends AppCompatActivity implements View.OnClickListener{

    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private RequestQueue requestQueue;
    private TextView tV,tV2,tV3,tV4;
    private NetworkImageView nIV;
    private ImageView iV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.today_special));
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerSpecial);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane3);
        mDrawerList = (ListView) findViewById(R.id.navList3);
        DrawerListAdapter adapter2 = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter2);
        mNavItems.add(new NavItem(R.mipmap.menu,getString(R.string.menu)));
        mNavItems.add(new NavItem(R.mipmap.platodia,getString(R.string.today_special)));
        mNavItems.add(new NavItem(R.mipmap.gallery,getString(R.string.gallery)));
        mNavItems.add(new NavItem(R.mipmap.contact,getString(R.string.contact)));
        mNavItems.add(new NavItem(R.mipmap.messages,getString(R.string.comments)));

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        tV = (TextView) findViewById(R.id.textView11);
        tV2 = (TextView) findViewById(R.id.textView13);
        tV3 = (TextView) findViewById(R.id.textView15);
        tV4 = (TextView) findViewById(R.id.textView16);
        nIV = (NetworkImageView) findViewById(R.id.photo2);
        nIV.setDefaultImageResId(R.drawable.loading);
        nIV.setErrorImageResId(R.drawable.error);

        requestQueue = Volley.newRequestQueue(this);
        final Context context = this;
        String url = utilities.serverAddress+"/controllers/productController.php?" +
                "action=GetSpecial";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.compareTo("Something is wrong with the products query") != 0) {
                            String[] info = response.split(":");
                            tV.setText(info[1]);
                            tV2.setText(info[2]);
                            tV4.setText("\""+info[3]+"\"");
                            ImageLoader.ImageCache imageCache = new BitmapLruCache();
                            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
                            nIV.setImageUrl(utilities.serverAddress+info[4],imageLoader);
                            String url2 = utilities.serverAddress+"/controllers/ingredientController.php?" +
                                    "action=GetIngredients"+"&productId="
                                    +info[0];
                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.compareTo("Something is wrong with the ingredients query") != 0) {
                                                String[] tokens = response.split(":");
                                                String[] ingredients = tokens[1].split(";");
                                                String s = "";
                                                for (int j = 0; j < ingredients.length; j++) {
                                                    s+= ingredients[j];
                                                    if(j!= ingredients.length - 1)
                                                        s+=", ";
                                                }
                                                tV3.setText(s);
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
        requestQueue.add(stringRequest);
        iV = (ImageView) findViewById(R.id.imageView19);
        iV.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /*
    * Called when a particular item from the navigation drawer
    * is selected.
    * */
    private void selectItemFromDrawer(int position) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this,MenuHome.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, Special.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, Gallery.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, Contact.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, Comments.class);
                startActivity(intent);
                break;
        }
        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public void onClick(View view) {
        if(iV.getId() == view.getId()) {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }
    }
}
