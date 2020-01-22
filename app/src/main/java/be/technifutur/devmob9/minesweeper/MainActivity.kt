package be.technifutur.devmob9.minesweeper

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val nbCol = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setupBoard()
    }

    fun setupBoard() {
        for (i in 0..this.nbCol) {
            val button = Button(this)
            button.text = "1"
            val layoutParams = ViewGroup.LayoutParams(
                //100, 100
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            button.layoutParams = layoutParams
            boardLayout.addView(button)
        }
    }
}
