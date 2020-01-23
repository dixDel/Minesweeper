package be.technifutur.devmob9.minesweeper

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
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
                button.text = ""
                button.tag = this.mines[i][j].toString()
                val layoutParams = LinearLayout.LayoutParams(
                    0,
                    this.dpToPx(50),
                    1.0f
                )
                button.layoutParams = layoutParams
                button.setOnClickListener {
                    Log.d(TAG, button.tag.toString())
                    if (button.tag.toString().toBoolean()) {
                        button.background = getDrawable(R.drawable.mine)
                    }
                }
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

    /*
     * @source https://medium.com/@euryperez/android-pearls-set-size-to-a-view-in-dp-programatically-71d22eed7fc0
     */
    private fun dpToPx(dp: Int): Int {
        val density: Float = this.resources
            .displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}
