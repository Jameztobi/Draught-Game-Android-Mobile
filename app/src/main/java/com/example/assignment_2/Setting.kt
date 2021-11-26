package com.example.assignment_2

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Setting : AppCompatActivity() {

    private var playerOneColor: TextView? = null
    private var playerTwoColor: TextView? = null
    private var boardColor: TextView? = null
    private var updateBtn: Button? = null
    private var cancelBtn: Button? = null
    private lateinit var defaultColorOne: Paint
    private lateinit var defaultColorTwo: Paint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

//        playerOneColor= findViewById(R.id.playerOneColor)
//        playerTwoColor = findViewById(R.id.playerTwoColor)
//        boardColor = findViewById(R.id.boardColor)
//        updateBtn = findViewById(R.id.updateBtn)
//        cancelBtn = findViewById(R.id.cancelBtn)
//
//        Color.argb(255, 0, 255, 0).also { defaultColorOne.color = it }
//        Color.argb(255, 217, 219, 218).also { defaultColorTwo.color = it }

//        playerOneColor?.setOnClickListener{
//            val colorPickerPopUp: ColorPickerPopup() = ColorPickerPopup.Builder(this)
//        }



    }

}