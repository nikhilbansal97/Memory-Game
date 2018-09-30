package com.android.nikhil.memorygame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import com.android.nikhil.memorygame.Constants.MARGIN
import com.android.nikhil.memorygame.Constants.SPAN_COUNT
import java.util.ArrayList
import java.util.Collections

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var cardAdapter: CardAdapter
  private lateinit var cardsList: ArrayList<Card>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    val height = displayMetrics.heightPixels / 4 - MARGIN
    val width = displayMetrics.widthPixels / 2 - MARGIN
    val params = RelativeLayout.LayoutParams(width, height)

    cardsList = ArrayList()
    cardsList.add(Card(1, R.drawable.ic_color_lens))
    cardsList.add(Card(2, R.drawable.ic_color_lens))
    cardsList.add(Card(3, R.drawable.ic_fingerprint))
    cardsList.add(Card(4, R.drawable.ic_fingerprint))
    cardsList.add(Card(5, R.drawable.ic_pets))
    cardsList.add(Card(6, R.drawable.ic_pets))
    cardsList.add(Card(7, R.drawable.ic_gamepad))
    cardsList.add(Card(8, R.drawable.ic_gamepad))
    cardsList.shuffle()

    recyclerView = findViewById(R.id.recyclerView)
    cardAdapter = CardAdapter(this, cardsList)
    cardAdapter.setParams(params)
    recyclerView.adapter = cardAdapter
    recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
  }

}
