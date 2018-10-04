package com.android.nikhil.memorygame

import com.wajahatkarim3.easyflipview.EasyFlipView

/**
 * Created by NIKHIL on 08-01-2018.
 */

data class Card (
  val imageRes: Int
) {
  var flipView: EasyFlipView? = null
}
