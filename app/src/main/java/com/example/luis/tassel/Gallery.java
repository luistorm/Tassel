package com.example.luis.tassel;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager lManager;
    private RequestQueue requestQueue;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ImageView iV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        requestQueue = Volley.newRequestQueue(this);
        final Context context = this;
        final List items = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.gallery));
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerGallery);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane5);
        mDrawerList = (ListView) findViewById(R.id.navList5);
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
        iV = (ImageView) findViewById(R.id.imageView20);
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
