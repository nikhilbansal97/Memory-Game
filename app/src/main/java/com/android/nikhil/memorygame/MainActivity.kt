package com.android.nikhil.memorygame

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.RelativeLayout
import com.android.nikhil.memorygame.Constants.MARGIN
import kotlinx.android.synthetic.main.dialog_game.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GameCallback {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val cardsList = ArrayList<Card>()
    cardsList.add(Card(R.drawable.ic_color_lens))
    cardsList.add(Card(R.drawable.ic_fingerprint))
    cardsList.add(Card(R.drawable.ic_pets))
    cardsList.add(Card(R.drawable.ic_gamepad))
    cardsList.addAll(cardsList.map { it.copy() })
    cardsList.shuffle()

    val columns = 2
    val rows = cardsList.size / columns

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    val height = displayMetrics.heightPixels / rows - MARGIN
    val width = displayMetrics.widthPixels / columns - MARGIN
    val params = RelativeLayout.LayoutParams(width, height)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
    val cardAdapter = CardAdapter(this, cardsList, this)
    cardAdapter.setParams(params)
    recyclerView.adapter = cardAdapter
    recyclerView.layoutManager = GridLayoutManager(this, columns)
  }

  override fun onWin() {
    GameDialog.newInstance("WIN").show(fragmentManager, null)
  }

  override fun onLose() {
    GameDialog.newInstance("GAME OVER").show(fragmentManager, null)
  }
}

interface GameCallback {
  fun onWin()
  fun onLose()
}

class GameDialog : DialogFragment() {

  companion object {
    fun newInstance(message: String): GameDialog {
      val frag = GameDialog()
      val args = Bundle()
      args.putString("message", message);
      frag.arguments = args
      return frag
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val view = activity.layoutInflater.inflate(R.layout.dialog_game, null)
    val message = arguments.getString("message")
    message?.let { view.message.text = message }
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setContentView(view)
    dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
  }
}
