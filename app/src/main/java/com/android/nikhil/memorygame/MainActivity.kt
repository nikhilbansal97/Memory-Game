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

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        reset()
    }

    private fun reset() {

        val cards = getCardsList()

        val cardAdapter = CardAdapter(this, cards, this, 10)

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

    private fun getCardsList(): ArrayList<Card> {
        val cards = ArrayList<Card>()
        cards.add(Card(R.drawable.ic_color_lens))
        cards.add(Card(R.drawable.ic_fingerprint))
        cards.add(Card(R.drawable.ic_pets))
        cards.add(Card(R.drawable.ic_gamepad))
        cards.addAll(cards.map { it.copy() })
        cards.shuffle()
        return cards
    }

    override fun onWin() {
        GameDialog.newInstance(getString(R.string.win)).setOnDismissListener(DialogInterface.OnDismissListener { reset() }).show(fragmentManager, null)
    }

    override fun onLose() {
        GameDialog.newInstance(getString(R.string.lose)).setOnDismissListener(DialogInterface.OnDismissListener { reset() }).show(fragmentManager, null)
    }
}