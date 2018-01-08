package com.android.nikhil.memorygame;

/**
 * Created by NIKHIL on 08-01-2018.
 */

public class Card {

    private int id;
    private int imageRes;
    private boolean turned = false;

    public Card(int id, int imageRes, boolean turned) {
        this.id = id;
        this.imageRes = imageRes;
        this.turned = turned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }
}
