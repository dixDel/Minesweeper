package be.technifutur.devmob9.minesweeper

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val boardSize = 10
    private val mines: ArrayList<ArrayList<MineButton>> = ArrayList(boardSize)//arrayOf(BooleanArray(boardSize))

    private var isSetMarkerActivated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMines()
        setupBoard()
        setupButtons()

    }

    private fun setupButtons() {
        putMarkerButton.background = getDrawable(android.R.drawable.btn_default)
        resetButton.background = getDrawable(android.R.drawable.btn_default)
        putMarkerButton.setOnClickListener {
            isSetMarkerActivated = !isSetMarkerActivated
            if (isSetMarkerActivated) {
                putMarkerButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.putMarkerOn));
            } else {
                putMarkerButton.background = getDrawable(android.R.drawable.btn_default)
                putMarkerButton.backgroundTintList = null
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
                button.background = getDrawable(android.R.drawable.btn_default)
                button.setOnClickListener {
                    Log.d(TAG, button.tag.toString())
                    if (this.isSetMarkerActivated) {
                        putMarker(button, i, j)
                    } else {
                        checkMines(button, i, j)
                    }
                }
                linearLayout.addView(button)
            }
        }
    }

    private fun putMarker(button: Button, i: Int, j: Int) {
        val mineButton = this.mines[i][j]
        if (mineButton.isMarked) {
            button.background = getDrawable(android.R.drawable.btn_default)
            mineButton.isMarked = false
        } else if (mineButton.isActive) {
            button.background = getDrawable(R.drawable.flag)
            mineButton.isMarked = true
        }
    }

    private fun checkMines(button: Button, i: Int, j: Int) {
        val mineButton = this.mines[i][j]
        if (mineButton.isMined) {
            button.background = getDrawable(R.drawable.mine)
            mineButton.isActive = false
            this.gameOver()
        } else {
            var nbMines = getNbMinesSurrounding(i, j)
            if (nbMines > 0) {
                button.text = nbMines.toString()
            } else {
                button.visibility = View.INVISIBLE
            }
            mineButton.isActive = false
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
                this.mines[i].add(MineButton(
                    Random.nextBoolean(),
                    false
                ))
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
            if (this.mines[row][col - 1].isMined) {
                nbMines++
            }
            if (row > 0 && this.mines[row - 1][col - 1].isMined) {
                nbMines++
            }
            if (row < this.boardSize - 1 && this.mines[row + 1][col - 1].isMined) {
                nbMines++
            }
        }
        if (col < this.boardSize - 1) {
            if (this.mines[row][col + 1].isMined) {
                nbMines++
            }
            if (row > 0 && this.mines[row - 1][col + 1].isMined) {
                nbMines++
            }
            if (row < this.boardSize - 1 && this.mines[row + 1][col + 1].isMined) {
                nbMines++
            }
        }
        if (row > 0 && this.mines[row - 1][col].isMined) {
            nbMines++
        }
        if (row < this.boardSize - 1 && this.mines[row + 1][col].isMined) {
            nbMines++
        }
        return nbMines
    }
}
