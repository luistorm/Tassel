package com.example.luis.tassel;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private TextView tV,tV2,tV3,tV4;
    private FloatingActionButton fab,fab2,fab3,fab4,fab5;
    private boolean fabVal;
    private CardView cV,cV2,cV3,cV4;
    private ImageView iV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerMain);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
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

        tV = (TextView) findViewById(R.id.textView);
        tV2 = (TextView) findViewById(R.id.textView2);
        tV3 = (TextView) findViewById(R.id.textView3);
        tV4 = (TextView) findViewById(R.id.textView4);

        Typeface tF = Typeface.createFromAsset(getAssets(),"tf1.ttf");
        float s1 = tV.getTextSize();
        float s2 = tV2.getTextSize();
        float s3 = tV3.getTextSize();
        float s4 = tV4.getTextSize();
        tV.setTypeface(tF);
        tV2.setTypeface(tF);
        tV3.setTypeface(tF);
        tV4.setTypeface(tF);
        tV.setTextSize(s1*2);
        tV2.setTextSize(s2*2);
        tV3.setTextSize(s3*2);
        tV4.setTextSize(s4*2);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab3 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fab4 = (FloatingActionButton) findViewById(R.id.floatingActionButton4);
        fab5 = (FloatingActionButton) findViewById(R.id.floatingActionButton5);
        fab.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);
        fabVal = false;

        cV = (CardView) findViewById(R.id.cardView);
        cV.setOnClickListener(this);
        cV2 = (CardView) findViewById(R.id.cardView3);
        cV2.setOnClickListener(this);
        cV3 = (CardView) findViewById(R.id.cardView4);
        cV3.setOnClickListener(this);
        cV4 = (CardView) findViewById(R.id.cardView2);
        cV4.setOnClickListener(this);

        iV = (ImageView) findViewById(R.id.imageView17);
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
        if(fab.getId() == view.getId()) {
            if(!fabVal) {
                final ScaleAnimation anim = new ScaleAnimation(0,1,0,1,22,22);
                anim.setFillBefore(true);
                anim.setFillAfter(true);
                anim.setFillEnabled(true);
                anim.setDuration(250);
                anim.setInterpolator(new BounceInterpolator());
                //CountDownTimer(TotalTime,IntervalTime)
                fab2.setEnabled(true);
                fab2.show();
                fab2.startAnimation(anim);
                new CountDownTimer(100, 100) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        ScaleAnimation anim2 = new ScaleAnimation(0,1,0,1,22,22);
                        anim2.setFillBefore(true);
                        anim2.setFillAfter(true);
                        anim2.setFillEnabled(true);
                        anim2.setDuration(250);
                        anim2.setInterpolator(new BounceInterpolator());
                        fab3.setEnabled(true);
                        fab3.show();
                        fab3.startAnimation(anim2);
                        new CountDownTimer(100, 100) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                ScaleAnimation anim3 = new ScaleAnimation(0,1,0,1,22,22);
                                anim3.setFillBefore(true);
                                anim3.setFillAfter(true);
                                anim3.setFillEnabled(true);
                                anim3.setDuration(250);
                                anim3.setInterpolator(new BounceInterpolator());
                                fab4.setEnabled(true);
                                fab4.show();
                                fab4.startAnimation(anim3);
                                new CountDownTimer(100, 100) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        ScaleAnimation anim4 = new ScaleAnimation(0,1,0,1,22,22);
                                        anim4.setFillBefore(true);
                                        anim4.setFillAfter(true);
                                        anim4.setFillEnabled(true);
                                        anim4.setDuration(250);
                                        anim4.setInterpolator(new BounceInterpolator());
                                        fab5.setEnabled(true);
                                        fab5.show();
                                        fab5.startAnimation(anim4);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
                fab.setImageResource(R.drawable.minus);
                fabVal = true;
            }
            else {
                fab5.hide();
                fab5.setEnabled(false);
                new CountDownTimer(100, 100) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        fab4.hide();
                        fab4.setEnabled(false);
                        new CountDownTimer(100, 100) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                fab3.hide();
                                fab3.setEnabled(false);
                                new CountDownTimer(100, 100) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        fab2.hide();
                                        fab2.setEnabled(false);
                                    }
                                }.start();
                            }
                        }.start();
                    }
                }.start();
                fab.setImageResource(R.drawable.plus);
                fabVal = false;
            }
        }
        if(fab2.getId() == view.getId()) {
            Intent intent = new Intent(this,WebNavigation.class);
            intent.putExtra("URL","http://www.facebook.com");
            startActivity(intent);
        }
        if(fab3.getId() == view.getId()) {
            Intent intent = new Intent(this,WebNavigation.class);
            intent.putExtra("URL","http://www.twitter.com");
            startActivity(intent);
        }
        if(fab4.getId() == view.getId()) {
            Intent intent = new Intent(this,WebNavigation.class);
            intent.putExtra("URL","http://www.instagram.com");
            startActivity(intent);
        }
        if(cV.getId() == view.getId()) {
            Intent intent = new Intent(this,MenuHome.class);
            startActivity(intent);
        }
        if(cV2.getId() == view.getId()) {
            Intent intent = new Intent(this,Special.class);
            startActivity(intent);
        }
        if(cV3.getId() == view.getId()) {
            Intent intent = new Intent(this,Contact.class);
            startActivity(intent);
        }
        if(cV4.getId() == view.getId()) {
            Intent intent = new Intent(this,Gallery.class);
            startActivity(intent);
        }
        if(fab5.getId() == view.getId()) {
            Intent intent = new Intent(this,Comments.class);
            startActivity(intent);
        }
        if(iV.getId() == view.getId()) {
            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }
    }
}
