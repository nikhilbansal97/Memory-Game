package com.android.nikhil.memorygame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.RelativeLayout
import com.android.nikhil.memorygame.Constants.MARGIN
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GameCallback {

    private var numTries: Int? = 4
    private var numTiles: Int? = 4
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        numTiles = intent?.getIntExtra("NUM_TILES", 4)
        numTries = intent?.getIntExtra("NUM_TRIES", 4)

        prepareGame(numTiles!!, numTries!!)
    }

    private fun prepareGame(numTiles: Int, numTries: Int) {

        val cards = getCardsList(numTiles / 2)

        val cardAdapter = CardAdapter(this, cards, this, numTries)

        val columns = 2
        val rows = cards.size / columns

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val height = displayMetrics.heightPixels / rows - MARGIN
        val width = displayMetrics.widthPixels / columns - MARGIN
        val params = RelativeLayout.LayoutParams(width, height)

        cardAdapter.setParams(params)
        recyclerView.adapter = cardAdapter
        (recyclerView.adapter as CardAdapter).notifyDataSetChanged()
        recyclerView.layoutManager = GridLayoutManager(this, columns)
    }

    private val listOfCards: ArrayList<Card>
        get() {
            val cards = ArrayList<Card>()
            cards.add(Card(R.drawable.ic_color_lens))
            cards.add(Card(R.drawable.ic_fingerprint))
            cards.add(Card(R.drawable.ic_pets))
            cards.add(Card(R.drawable.ic_gamepad))
            return cards
        }

    private fun getCardsList(numTiles: Int): ArrayList<Card> {
        val cards = ArrayList<Card>()
        cards.addAll(listOfCards.subList(0, numTiles))
        cards.addAll(cards.map { it.copy() })
        cards.shuffle()
        return cards
    }

    override fun onWin() {
        GameDialog.newInstance(getString(R.string.win))
                .setOnDismissListener(DialogInterface.OnDismissListener {
                    prepareGame(this.numTiles!!, this.numTries!!)
                }).show(fragmentManager, null)
    }

    override fun onLose() {
        GameDialog.newInstance(getString(R.string.lose))
                .setOnDismissListener(DialogInterface.OnDismissListener {
                    prepareGame(this.numTiles!!, this.numTries!!)
                }).show(fragmentManager, null)
    }
}