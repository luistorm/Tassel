package com.example.luis.tassel;

/**
 * Created by luis on 08/07/17.
 */

public class comment {
    private int id;
    private String name;
    private String comment;
    private int stars;
    private int visible;

    public comment(int id, String name, String comment, int stars, int visible) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.stars = stars;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
