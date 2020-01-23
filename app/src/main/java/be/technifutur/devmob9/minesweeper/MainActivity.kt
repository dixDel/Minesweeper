package be.technifutur.devmob9.minesweeper

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val boardSize = 10
    private val mines: ArrayList<ArrayList<Boolean>> = ArrayList(boardSize)//arrayOf(BooleanArray(boardSize))

    private var isSetMarkerActivated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMines()
        setupBoard()
        setupButtons()

    }

    private fun setupButtons() {
        putMarkerButton.setBackgroundColor(Color.LTGRAY)
        putMarkerButton.setOnClickListener {
            isSetMarkerActivated = !isSetMarkerActivated
            if (isSetMarkerActivated) {
                putMarkerButton.setBackgroundColor(Color.GREEN)
            } else {
                putMarkerButton.setBackgroundColor(Color.LTGRAY)
            }
        }
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
                    if (this.isSetMarkerActivated) {
                        putMarker(button)
                    } else {
                        checkMines(button, i, j)
                    }
                }
                linearLayout.addView(button)
            }
        }
    }

    private fun putMarker(button: Button) {
        button.background = getDrawable(R.drawable.flag)
    }

    private fun checkMines(button: Button, i: Int, j: Int) {
        if (button.tag.toString().toBoolean()) {
            button.background = getDrawable(R.drawable.mine)
            this.gameOver()
        } else {
            var nbMines = getNbMinesSurrounding(i, j)
            if (nbMines > 0) {
                button.text = nbMines.toString()
            } else {
                button.visibility = View.INVISIBLE
            }
        }
    }

    private fun gameOver() {
        Snackbar.make(mainLayout, "ET BOUUUUUUM !", Snackbar.LENGTH_LONG).show()
    }

    private fun setupMines() {
        for (i in 0 until this.boardSize) {
            Log.d(TAG, i.toString())
            this.mines.add(arrayListOf())
            for (j in 0 until this.boardSize) {
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

    private fun getNbMinesSurrounding(row: Int, col: Int): Int {
        var nbMines = 0
        if (col > 0) {
            if (this.mines[row][col - 1]) {
                nbMines++
            }
            if (row > 0 && this.mines[row - 1][col - 1]) {
                nbMines++
            }
            if (row < this.boardSize - 1 && this.mines[row + 1][col - 1]) {
                nbMines++
            }
        }
        if (col < this.boardSize - 1) {
            if (this.mines[row][col + 1]) {
                nbMines++
            }
            if (row > 0 && this.mines[row - 1][col + 1]) {
                nbMines++
            }
            if (row < this.boardSize - 1 && this.mines[row + 1][col + 1]) {
                nbMines++
            }
        }
        if (row > 0 && this.mines[row - 1][col]) {
            nbMines++
        }
        if (row < this.boardSize - 1 && this.mines[row + 1][col]) {
            nbMines++
        }
        return nbMines
    }
}
