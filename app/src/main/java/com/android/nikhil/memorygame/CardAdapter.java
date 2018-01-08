package com.android.nikhil.memorygame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 08-01-2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private ArrayList<Card> cardList = new ArrayList<>();
    private int CARDS_COUNT = 8;
    private RelativeLayout.LayoutParams params;
    private static int CARDS_FLIPPED = 0;
    private static int positionPreviousCard = 0;
    private static final String TAG = "CardAdapter";
    private static boolean firstBinding = true;
    final Handler handler = new Handler();

    public CardAdapter(Context context, ArrayList<Card> list) {
        this.context = context;
        cardList = list;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        if (firstBinding)
            cardList.get(position).setFlipView(holder.flipView);
        Log.d(TAG, "onBindViewHolder: " + String.valueOf(position));
        holder.textView.setText("Card Name");
        holder.rootLayout.setLayoutParams(params);
        holder.cardImageView.setImageResource(cardList.get(position).getImageRes());
        holder.flipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstBinding = false;
                if (!cardList.get(position).getFlipView().isFlipEnabled())
                    return;
                cardList.get(position).getFlipView().flipTheView();
                CARDS_FLIPPED++;
                if (CARDS_FLIPPED == 1) {
                    positionPreviousCard = position;
                    Log.d(TAG, "onViewFlipCompleted: CARDS_FLIPPED = 1, Previous = " + String.valueOf(positionPreviousCard));
                    cardList.get(position).getFlipView().setFlipEnabled(false);
                } else if (CARDS_FLIPPED == 2  && position != positionPreviousCard) {
                    Log.d(TAG, "onViewFlipCompleted: CARDS_FLIPPED = 2, Previous = " + String.valueOf(positionPreviousCard)
                            + " Current = " + String.valueOf(position));
                    Card prevCard = cardList.get(positionPreviousCard);
                    Card currCard = cardList.get(position);
                    if (prevCard.getImageRes() == currCard.getImageRes()){
                        Log.d(TAG, "onViewFlipCompleted: Cards Matched");
                        cardList.get(positionPreviousCard).getFlipView().setFlipEnabled(false);
                        cardList.get(position).getFlipView().setFlipEnabled(false);
                    } else {
                        Log.d(TAG, "onViewFlipCompleted: Cards Don't Match");
                        cardList.get(positionPreviousCard).getFlipView().setFlipEnabled(true);
                        cardList.get(position).getFlipView().setFlipEnabled(true);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cardList.get(positionPreviousCard).getFlipView().flipTheView();
                                cardList.get(position).getFlipView().flipTheView();
                            }
                        },700);

                    }
                    CARDS_FLIPPED = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return CARDS_COUNT;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RelativeLayout rootLayout;
        private EasyFlipView flipView;
        private ImageView cardImageView;

        public CardViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardViewTextView);
            rootLayout = itemView.findViewById(R.id.cardViewRootLayout);
            flipView = itemView.findViewById(R.id.flipView);
            cardImageView = itemView.findViewById(R.id.cardImageView);
        }
    }

    public void setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
    }
}
