package com.android.nikhil.memorygame

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.RelativeLayout
import com.android.nikhil.memorygame.Constants.MARGIN
import kotlinx.android.synthetic.main.dialog_game.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GameCallback {

  private lateinit var recyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView = findViewById(R.id.recyclerView)

    reset()
  }

  fun reset() {

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
    recyclerView.adapter.notifyDataSetChanged()
    recyclerView.layoutManager = GridLayoutManager(this, columns)
  }

  fun getCardsList(): ArrayList<Card> {
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

  private lateinit var dismissListener: DialogInterface.OnDismissListener

  fun setOnDismissListener(listener: DialogInterface.OnDismissListener) : GameDialog {
    dismissListener = listener
    return this
  }

  override fun onDismiss(dialog: DialogInterface?) {
    dismissListener.onDismiss(dialog)
    super.onDismiss(dialog)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val view = activity.layoutInflater.inflate(R.layout.dialog_game, null)
    val message = arguments.getString("message")
    message?.let { view.message.text = it }
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setContentView(view)
    dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
  }
}
