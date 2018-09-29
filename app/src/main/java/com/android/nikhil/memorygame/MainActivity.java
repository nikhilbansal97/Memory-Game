package com.android.nikhil.memorygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private ArrayList<Card> cardsList;
    private int MARGIN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = getWindowManager();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels / 4) - MARGIN;
        int width = (displayMetrics.widthPixels / 2) - MARGIN;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);

        cardsList = new ArrayList<>();
        cardsList.add(new Card(1, R.drawable.ic_color_lens));
        cardsList.add(new Card(2, R.drawable.ic_color_lens));
        cardsList.add(new Card(3, R.drawable.ic_fingerprint));
        cardsList.add(new Card(4, R.drawable.ic_fingerprint));
        cardsList.add(new Card(5, R.drawable.ic_pets));
        cardsList.add(new Card(6, R.drawable.ic_pets));
        cardsList.add(new Card(7, R.drawable.ic_gamepad));
        cardsList.add(new Card(8, R.drawable.ic_gamepad));
        Collections.shuffle(cardsList);

        recyclerView = findViewById(R.id.recyclerView);
        cardAdapter = new CardAdapter(this, cardsList);
        cardAdapter.setParams(params);
        recyclerView.setAdapter(cardAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

}
