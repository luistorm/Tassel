package com.example.luis.tassel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Splash extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashScreen = (RelativeLayout) findViewById(R.id.splashScreen);
        splashScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (splashScreen.getId() == view.getId()) {
            Intent intent = new Intent(this,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
