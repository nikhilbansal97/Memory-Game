package com.android.nikhil.memorygame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 08-01-2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Card> cardList = new ArrayList<>();
    private int CARDS_COUNT = 8;
    private RelativeLayout.LayoutParams params;

    public CardAdapter(Context context, ArrayList<Card> list) {
        this.context = context;
        cardList = list;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.textView.setText("Card Name");
        holder.rootLayout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return CARDS_COUNT;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RelativeLayout rootLayout;

        public CardViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardViewTextView);
            rootLayout = itemView.findViewById(R.id.cardViewRootLayout);
        }
    }

    public void setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
    }
}
