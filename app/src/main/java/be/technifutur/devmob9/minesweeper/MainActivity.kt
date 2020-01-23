package be.technifutur.devmob9.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val boardSize = 10
    private val mines: ArrayList<ArrayList<Boolean>> = ArrayList(boardSize)//arrayOf(BooleanArray(boardSize))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMines()
        setupBoard()

    }

    private fun setupBoard() {
        for (i in 0 until this.boardSize) {
            val linearLayout = LinearLayout(this)
            boardLayout.addView(linearLayout)
            for (j in 0 until this.boardSize) {
                val button = Button(this)
                button.text = "1"
                val layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                button.layoutParams = layoutParams
                linearLayout.addView(button)
            }
        }
    }

    private fun setupMines() {
        for (i in 0..this.boardSize) {
            Log.d(TAG, i.toString())
            this.mines.add(arrayListOf())
            for (j in 0..this.boardSize) {
                Log.d(TAG, j.toString())
                this.mines[i].add(Random.nextBoolean())
            }
        }
        Log.d(TAG, this.mines.toString())
    }
}
