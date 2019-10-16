package com.android.nikhil.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnEasy.setOnClickListener(this)
        btnMedium.setOnClickListener(this)
        btnHard.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        //default: Easy Stage
        var numTiles = 4
        var numTries = 4
        when (view?.id) {
            R.id.btnMedium -> {
                numTiles = 6
                numTries = 6
            }
            R.id.btnHard -> {
                numTiles = 8
                numTries = 8
            }
        }

        val launchIntent = Intent(this, MainActivity::class.java)
        launchIntent.putExtra("NUM_TILES", numTiles)
        launchIntent.putExtra("NUM_TRIES", numTries)
        startActivity(launchIntent)
    }
}
