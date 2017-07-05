package com.example.luis.tassel;

import android.content.Context;
import android.content.Intent;
import android.service.voice.VoiceInteractionSession;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class MenuHome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private RequestQueue requestQueue;
    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu));
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerMenu);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane2);
        mDrawerList = (ListView) findViewById(R.id.navList2);
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
        requestQueue = Volley.newRequestQueue(this);
        final Context context = this;
        final List items = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(lManager);

        String url = utilities.serverAddress+"/controllers/imageController.php?action=GetAll";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.compareTo("Something is wrong with the images query") != 0) {
                            String[] imagesInfo = response.split(";");
                            image[] Aux = new image[imagesInfo.length];
                            for (int i = 0; i < imagesInfo.length; i++) {
                                String[] info = imagesInfo[i].split(":");
                                Aux[i] = new image(Integer.parseInt(info[0]),info[1],Integer.parseInt(info[2]));
                            }
                            utilities.images = Aux;
                            String url2 = utilities.serverAddress+"/controllers/productController.php?action=GetAll";
                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.compareTo("Something is wrong with the images query") != 0) {
                                        String[] productsInfo = response.split(";");
                                        product[] Aux = new product[productsInfo.length];
                                        for (int i = 0; i < productsInfo.length; i++) {
                                            String[] info = productsInfo[i].split(":");
                                            Aux[i] = new product(Integer.parseInt(info[0]),info[1], Float.parseFloat(info[2]),info[3],Integer.parseInt(info[4]));
                                            boolean b = false;
                                            for (int j = 0; j < utilities.images.length; j++) {
                                                if(utilities.images[j].getProductId() == Aux[i].getId()){
                                                    Aux[i].setImagePath(utilities.images[j].getPath());
                                                    b = true;
                                                    break;
                                                }
                                            }
                                            if(!b){
                                                Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        utilities.products = Aux;
                                        String url3;
                                        for (int i = 0; i < utilities.products.length; i++) {
                                            items.add(utilities.products[i]);
                                            adapter = new productAdapter(items);
                                            recyclerView.setAdapter(adapter);
                                            recyclerView.refreshDrawableState();
                                            url3 = utilities.serverAddress+"/controllers/ingredientController.php?" +
                                                    "action=GetIngredients"+"&productId="
                                                    +Integer.toString(utilities.products[i].getId());
                                            StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3,
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
                                                        for (int j = 0; j < utilities.products.length; j++) {
                                                            if(Integer.parseInt(tokens[0]) == utilities.products[j].getId()){
                                                                ((product)items.get(j)).setIngredients(s);
                                                                adapter = new productAdapter(items);
                                                                recyclerView.setAdapter(adapter);
                                                                recyclerView.refreshDrawableState();
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                                    , new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(context, "Error: \n" + error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            requestQueue.add(stringRequest3);
                                        }

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context,"Error: \n"+error.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue.add(stringRequest2);
                        }
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error: \n"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
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

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }
}
