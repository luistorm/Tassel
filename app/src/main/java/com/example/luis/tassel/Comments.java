package com.example.luis.tassel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comments extends AppCompatActivity implements View.OnClickListener{

    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ImageButton iB;
    private RequestQueue requestQueue;
    private EditText eT,eT2;
    private ImageView iV,iV2,iV3,iV4,iV5,iV6;
    private int stars;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.comments));
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerComments);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane6);
        mDrawerList = (ListView) findViewById(R.id.navList6);
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

        iB = (ImageButton) findViewById(R.id.imageButton2);
        iB.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        eT = (EditText) findViewById(R.id.editText);
        eT2 = (EditText) findViewById(R.id.editText2);
        iV = (ImageView) findViewById(R.id.imageView7);
        iV2 = (ImageView) findViewById(R.id.imageView13);
        iV3 = (ImageView) findViewById(R.id.imageView14);
        iV4 = (ImageView) findViewById(R.id.imageView15);
        iV5 = (ImageView) findViewById(R.id.imageView16);
        try
        {
            File databaseFile = getDatabasePath(utilities.dbFilename);
            utilities.dbObj = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);
        } catch (Exception e)
        {
            String databasePath =  getFilesDir().getPath() +  "/" + utilities.dbFilename;
            File databaseFile = new File(databasePath);
            utilities.dbObj = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);
        }
        utilities.dbObj.execSQL("create table if not exists options(" +
                "val integer not null," +
                "primary key(val)" +
                ");");
        Cursor cursor = utilities.dbObj.rawQuery("SELECT * FROM options WHERE val=1", null);
        if(cursor.moveToFirst())
        {
            eT.setEnabled(false);
            eT2.setEnabled(false);
            iV.setEnabled(false);
            iV2.setEnabled(false);
            iV3.setEnabled(false);
            iV4.setEnabled(false);
            iV5.setEnabled(false);
            iB.setEnabled(false);
        }
        iV.setOnClickListener(this);
        iV2.setOnClickListener(this);
        iV3.setOnClickListener(this);
        iV4.setOnClickListener(this);
        iV5.setOnClickListener(this);
        stars = 0;
        final List items = new ArrayList();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lManager);
        final Context context = this;
        String url = utilities.serverAddress+"/controllers/commentController.php?action=GetAll";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.compareTo("Something is wrong with the comments query") != 0) {
                            String[] info = response.split(";");
                            comment[] Aux = new comment[info.length];
                            for (int i = 0; i < info.length; i++) {
                                String[] commentInfo = info[i].split(":");
                                Aux[i] = new comment(Integer.parseInt(commentInfo[0])
                                        ,commentInfo[1],commentInfo[3]
                                        ,Integer.parseInt(commentInfo[2])
                                        ,Integer.parseInt(commentInfo[4]));
                                items.add(Aux[i]);
                            }
                            utilities.comments = Aux;
                            adapter = new commentAdapter(items);
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
        requestQueue.add(stringRequest);

        iV6 = (ImageView) findViewById(R.id.imageView22);
        iV6.setOnClickListener(this);
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
        if(view.getId() == iB.getId()) {
            String url = utilities.serverAddress+"/controllers/commentController.php";
            Map<String, String> params = new HashMap<>();
            params.put("action", "insert");
            params.put("name",eT.getText().toString());
            params.put("comment",eT2.getText().toString());
            params.put("stars",Integer.toString(stars));
            final Context context = this;
            CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url,
                    params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String s = response.getString("response");
                        if(s.compareTo("true") == 0){
                            Toast.makeText(context,getString(R.string.opinion),Toast.LENGTH_LONG).show();
                            eT.setText("");
                            eT2.setText("");
                            stars = 0;
                            iV.setImageResource(R.drawable.star1);
                            iV2.setImageResource(R.drawable.star1);
                            iV3.setImageResource(R.drawable.star1);
                            iV4.setImageResource(R.drawable.star1);
                            iV5.setImageResource(R.drawable.star1);
                            utilities.dbObj.execSQL("INSERT INTO options VALUES(1);");
                            EndActivity();
                        }
                        else {
                            Toast.makeText(context, getString(R.string.opinion), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError response) {
                    Log.d("Response: ", response.toString());
                }
            });
            requestQueue.add(jsObjRequest);
        }
        if(view.getId() == iV.getId()) {
            stars = 1;
            iV.setImageResource(R.drawable.star2);
            iV2.setImageResource(R.drawable.star1);
            iV3.setImageResource(R.drawable.star1);
            iV4.setImageResource(R.drawable.star1);
            iV5.setImageResource(R.drawable.star1);
        }
        if(view.getId() == iV2.getId()) {
            stars = 2;
            iV.setImageResource(R.drawable.star2);
            iV2.setImageResource(R.drawable.star2);
            iV3.setImageResource(R.drawable.star1);
            iV4.setImageResource(R.drawable.star1);
            iV5.setImageResource(R.drawable.star1);
        }
        if(view.getId() == iV3.getId()) {
            stars = 3;
            iV.setImageResource(R.drawable.star2);
            iV2.setImageResource(R.drawable.star2);
            iV3.setImageResource(R.drawable.star2);
            iV4.setImageResource(R.drawable.star1);
            iV5.setImageResource(R.drawable.star1);
        }
        if(view.getId() == iV4.getId()) {
            stars = 4;
            iV.setImageResource(R.drawable.star2);
            iV2.setImageResource(R.drawable.star2);
            iV3.setImageResource(R.drawable.star2);
            iV4.setImageResource(R.drawable.star2);
            iV5.setImageResource(R.drawable.star1);
        }
        if(view.getId() == iV5.getId()) {
            stars = 5;
            iV.setImageResource(R.drawable.star2);
            iV2.setImageResource(R.drawable.star2);
            iV3.setImageResource(R.drawable.star2);
            iV4.setImageResource(R.drawable.star2);
            iV5.setImageResource(R.drawable.star2);
        }
        if(iV6.getId() == view.getId()) {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }
    }

    private void EndActivity() {
        this.finish();
    }
}
