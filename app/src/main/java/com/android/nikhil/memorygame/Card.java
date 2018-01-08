package com.android.nikhil.memorygame;

import com.wajahatkarim3.easyflipview.EasyFlipView;

/**
 * Created by NIKHIL on 08-01-2018.
 */

public class Card {

    private int id;
    private int imageRes;
    private EasyFlipView flipView;

    public EasyFlipView getFlipView() {
        return flipView;
    }

    public void setFlipView(EasyFlipView flipView) {
        this.flipView = flipView;
    }

    public Card(int id, int imageRes) {
        this.id = id;
        this.imageRes = imageRes;
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
}
