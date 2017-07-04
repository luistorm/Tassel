package com.example.luis.tassel;

/**
 * Created by luis on 01/07/17.
 */

public class NavItem {
    private int Icon;
    private String Name;

    public NavItem(int icon, String name) {
        Icon = icon;
        Name = name;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
