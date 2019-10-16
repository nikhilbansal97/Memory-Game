package com.android.nikhil.memorygame

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.os.Handler
import com.android.nikhil.memorygame.R.string
import com.wajahatkarim3.easyflipview.EasyFlipView
import kotlinx.android.synthetic.main.card_front.view.cardViewTextView

import java.util.ArrayList

/**
 * Created by NIKHIL on 08-01-2018.
 */

class CardAdapter(
        private val context: Context,
        private val cardList: ArrayList<Card>,
        private val gameCallback: GameCallback,
        private val tryLimit: Int
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var params: RelativeLayout.LayoutParams? = null
    private val handler = Handler()

    private var cardsFlipped = 0
    private var positionPreviousCard = 0
    private var matchesCount = 0
    private var tryCount = 0
    private var touchTempDisabled = false

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false))
    }

    override fun onBindViewHolder(
            holder: CardViewHolder,
            position: Int
    ) {
        cardList[position].flipView = holder.flipView
        holder.textView.text = context.getString(string.question_mark)
        holder.rootLayout.layoutParams = params
        holder.cardImageView.setImageResource(cardList[position].imageRes)
        holder.flipView.setOnClickListener(View.OnClickListener {
            if (touchTempDisabled)
                return@OnClickListener
            it as EasyFlipView
            if (!it.isFlipEnabled)
                return@OnClickListener
            it.flipTheView()
            cardsFlipped++
            if (cardsFlipped == 1) {
                positionPreviousCard = position
                cardList[position]
                        .flipView!!.isFlipEnabled = false
            } else if (cardsFlipped == 2 && position != positionPreviousCard) {
                // Disable card flipping while 2 cards are flipped to prevent >2 cards flipped
                // Especially for during animation below
                touchTempDisabled = true
                val prevCard = cardList[positionPreviousCard]
                val currCard = cardList[position]
                if (prevCard.imageRes == currCard.imageRes) {
                    cardList[positionPreviousCard]
                            .flipView!!.isFlipEnabled = false
                    cardList[position]
                            .flipView!!.isFlipEnabled = false
                    touchTempDisabled = false

                    // If the number of matches is equal to the number of pairs of cards then win
                    matchesCount++
                    if (matchesCount == itemCount / 2) {
                        gameCallback.onWin()
                        return@OnClickListener
                    }
                } else {
                    cardList[positionPreviousCard]
                            .flipView!!.isFlipEnabled = true
                    cardList[position]
                            .flipView!!.isFlipEnabled = true
                    handler.postDelayed({
                        cardList[positionPreviousCard].flipView!!.flipTheView()
                        it.flipTheView()
                        touchTempDisabled = false
                    }, 700)
                }

                cardsFlipped = 0

                tryCount++
                if (tryCount >= tryLimit) {
                    gameCallback.onLose()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.cardViewTextView as TextView
        val rootLayout = itemView.findViewById(R.id.cardViewRootLayout) as RelativeLayout
        val flipView = itemView.findViewById(R.id.flipView) as EasyFlipView
        val cardImageView = itemView.findViewById(R.id.cardImageView) as ImageView
    }

    fun setParams(params: RelativeLayout.LayoutParams) {
        this.params = params
    }
}
