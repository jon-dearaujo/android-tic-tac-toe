package com.example.android.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import android.widget.Toast

class TicTacToe : AppCompatActivity() {

    private var round: Int = 0;
    private val board: Array<Char?> = arrayOfNulls<Char>(9)
    private val currentRoundImage: Int
        get() = if (isCurrentRoundEven()) R.drawable.o else R.drawable.x

    private val currentRoundChar: Char
        get() = if (isCurrentRoundEven()) 'O' else 'X'

    private fun isCurrentRoundEven(): Boolean {
        return this.round % 2 == 0
    }
    public fun dropIn(view: View) {
        val movePosition = view.tag.toString().toInt()
        if (board[movePosition] == null) {
            this.round = this.round + 1
            board[movePosition] = currentRoundChar
            animate(view)
                    .withEndAction(this::checkForWinnerOrDrawToFinalizeTheGame)
        } else {
            showToast("Position already filled")
        }
    }

    private fun checkForWinnerOrDrawToFinalizeTheGame() {
        val currentWinner: Char? = winnerOnCurrentMove()
        if (currentWinner != null) {
            finalizeCurrentGame(currentWinner)
        } else if(round == 9) {
            draw()
        }
    }

    private fun draw() {
        showToast("Draw")
        finish()
        startActivity(intent)
    }

    private fun finalizeCurrentGame(winner: Char?) {
        showToast(winner.toString() + " won the game!")
        finish()
        startActivity(intent)
    }

    private fun winnerOnCurrentMove(): Char? {
        val horizontalWinner = winnerOnPositions(board, 0, 1, 2, { pos -> pos+3 })
        val verticalWinner = winnerOnPositions(board, 0, 3, 6, { pos -> pos+1 })
        val firstDiagonalWinner = valueInAllPositionsOrNull(board, 0, 4, 8)
        val secondDiagonalWinner = valueInAllPositionsOrNull(board, 2, 4, 6)

        val winnerArray = arrayOf(
                horizontalWinner,
                verticalWinner,
                firstDiagonalWinner,
                secondDiagonalWinner).filter { moveChar -> moveChar != null }
        if (!winnerArray.isEmpty()) {
            return winnerArray[0]
        } else {
            return null;
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    /*Recursive method used to search winner on vertical and horizontal positions*/
    private fun winnerOnPositions(
            board: Array<Char?>,
            pos1: Int,
            pos2: Int,
            pos3: Int,
            transform: (Int) -> Int): Char? {
        val winner: Char? = valueInAllPositionsOrNull(board, pos1, pos2, pos3);
        if (winner != null) {
            return winner
        } else if (pos3 == 8) {//end of board
            return null
        } else {
            return winnerOnPositions(
                    board,
                    transform(pos1),
                    transform(pos2),
                    transform(pos3),
                    transform)
        }
    }

    private fun valueInAllPositionsOrNull(board: Array<Char?>, pos1: Int, pos2: Int, pos3:Int): Char? {
        if (board[pos1] == board[pos2] && board[pos2] == board[pos3]) {
            return board[pos1]
        } else {
            return null
        }
    }

    private fun animate(view: View): ViewPropertyAnimator {
        val imageView = view as ImageView
        // move view away from the screen
        imageView.translationY = -1000f
        imageView.translationX = -1000f
        imageView.setImageResource(this.currentRoundImage)
        return imageView
                .animate()
                .translationYBy(1000f)
                .translationXBy(1000f)
                .rotation(720f)
                .setDuration(500)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)
    }
}
