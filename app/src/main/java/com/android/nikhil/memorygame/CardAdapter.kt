package com.android.nikhil.memorygame

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
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
  val gameCallback: GameCallback
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
  private var params: RelativeLayout.LayoutParams? = null
  internal val handler = Handler()

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
    if (firstBinding)
      cardList[position].flipView = holder.flipView
    holder.textView.text = context.getString(string.question_mark)
    holder.rootLayout.layoutParams = params
    holder.cardImageView.setImageResource(cardList[position].imageRes)
    holder.flipView.setOnClickListener(View.OnClickListener {
      it as EasyFlipView
      firstBinding = false
      if (!it.isFlipEnabled)
        return@OnClickListener
      it.flipTheView()
      CARDS_FLIPPED++
      if (CARDS_FLIPPED == 1) {
        positionPreviousCard = position
        cardList[position]
            .flipView!!.isFlipEnabled = false
      } else if (CARDS_FLIPPED == 2 && position != positionPreviousCard) {
        val prevCard = cardList[positionPreviousCard]
        val currCard = cardList[position]
        if (prevCard.imageRes == currCard.imageRes) {
          cardList[positionPreviousCard]
              .flipView!!.isFlipEnabled = false
          cardList[position]
              .flipView!!.isFlipEnabled = false
          matches++
          if(matches == itemCount/2) {
            gameCallback.onWin()
          }
        } else {
          cardList[positionPreviousCard]
              .flipView!!.isFlipEnabled = true
          cardList[position]
              .flipView!!.isFlipEnabled = true
          handler.postDelayed({
            cardList[positionPreviousCard].flipView!!.flipTheView()
            it.flipTheView()
          }, 700)
        }
        CARDS_FLIPPED = 0
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

  companion object {
    private var CARDS_FLIPPED = 0
    private var positionPreviousCard = 0
    private var firstBinding = true
    private var matches = 0
  }
}
