package com.example.android.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class TicTacToe : AppCompatActivity() {

    private var round: Int = 0;
    private val board: Array<Array<Int?>> = Array<Array<Int?>>(3) { arrayOfNulls<Int>(3)};
    val currentRoundImage: Int
        get() = if (this.round % 2 == 0) R.drawable.o else R.drawable.x;

    public fun dropIn(view: View) {
        this.round = this.round + 1;
        val movePosition = view.tag.toString().toInt();


        val imageView = view as ImageView;
        // move view away from the screen
        imageView.translationY = -1000f;
        imageView.translationX = -1000f;
        imageView.setImageResource(this.currentRoundImage);
        imageView
                .animate()
                .translationYBy(1000f)
                .translationXBy(1000f)
                .rotation(720f)
                .duration = 500;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)
    }
}
