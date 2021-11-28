package com.example.assignment_2.controller

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment_2.R
import com.example.assignment_2.model.ColorObject
import com.example.assignment_2.model.RetrievedColor
import android.content.Intent as Intent

class SettingActivity : AppCompatActivity() {

    private var playerOneColor: Spinner? = null
    private var playerTwoColor: Spinner? = null
    private var boardColorOne: Spinner? = null
    private var boardColorTwo: Spinner? = null
    private var colorList: ArrayList<String> = ArrayList()
    private var updateBtn: Button? = null
    private  var defaultColorOne: Paint? = null
    private  var defaultColorTwo: Paint? = null
    private var retrievedColor: RetrievedColor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

        playerOneColor = findViewById(R.id.playerOneColor)
        playerTwoColor = findViewById(R.id.playerTwoColor)
        boardColorOne = findViewById(R.id.boardColor1)
        boardColorTwo = findViewById(R.id.boardColor2)

        updateBtn = findViewById(R.id.updateBtn)


        Color.argb(255, 0, 255, 0).also { defaultColorOne?.color = it }
        Color.argb(255, 217, 219, 218).also { defaultColorTwo?.color = it }
        setUpColorDropDownMenu()

        updateGameSetting()
    }

    private fun updateGameSetting() {
        updateBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var temp = playerOneColor?.selectedItem.toString()
                retrievedColor = RetrievedColor(
                    playerOneColor?.selectedItem.toString(),
                    playerTwoColor?.selectedItem.toString(),
                    boardColorOne?.selectedItem.toString(),
                    boardColorTwo?.selectedItem.toString()
                )
                retrievedColor.let {
                    val startMainIntent = Intent(Intent.ACTION_VIEW)
                    startMainIntent.putExtra("retrievedColor", it)!!
                    setResult(RESULT_OK, startMainIntent)
                    finish()
                }

            }

        })
    }


    private fun setUpColorDropDownMenu() {
        basicColors()
        getColorList()
        setPlayerOneDropDown()
        setPlayerTwoDropDown()
        boardColorOneDropDown()
        boardColorTwoDropDown()
    }

    private fun setPlayerOneDropDown(){
        playerOneColor?.adapter = ArrayAdapter<String>(
          this,
          android.R.layout.simple_list_item_1,
          colorList
        )

        playerOneColor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setPlayerTwoDropDown(){
        playerTwoColor?.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            colorList
        )

        playerTwoColor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun boardColorOneDropDown(){
        boardColorOne?.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            colorList
        )

        boardColorOne?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun boardColorTwoDropDown(){
        boardColorTwo?.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            colorList
        )

        boardColorTwo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun basicColors(): List<ColorObject>
    {
        return listOf(
            ColorObject("Black", "blackHex"),
            ColorObject("Silver", "C0C0C0"),
            ColorObject("Gray", "808080"),
            ColorObject("Maroon", "800000"),
            ColorObject("Red", "FF0000"),
            ColorObject("Fuchsia", "FF00FF"),
            ColorObject("Green", "008000"),
            ColorObject("Lime", "00FF00"),
            ColorObject("Olive", "808000"),
            ColorObject("Yellow", "FFFF00"),
            ColorObject("Navy", "000080"),
            ColorObject("Blue", "0000FF"),
            ColorObject("Teal", "008080"),
            ColorObject("Aqua", "00FFFF"),
            ColorObject("White", "#FFFFFF")
        )

    }

    private fun getColorList(): ArrayList<String>? {
        for(element in basicColors()){
            colorList.add(element._name)
        }
        return colorList
    }


}