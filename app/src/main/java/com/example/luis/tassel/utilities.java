package com.example.luis.tassel;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by luis on 03/07/17.
 */

public class utilities {
    public static final String serverAddress = "http://192.168.0.113/tassel";
    public static image[] images = null;
    public static  product[] products = null;
    public static comment[] comments = null;
    public static String dbFilename = "tassel.db";
    public static SQLiteDatabase dbObj = null;
}
