package com.example.assignment_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), DraughtService {
    private var draughtModel = DraughtClass()
    private var playerOneText: TextView? = null
    private var playerTwoText: TextView? = null
    private var playerOneValue: TextView? = null
    private var playerTwoValue: TextView? = null
    private var settingButton: Button? = null
    private var reset: Button? = null
    private var playerOne=0
    private var playerTwo=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initiate variable
        playerOneText = findViewById<TextView>(R.id.player1_text)
        playerTwoText = findViewById<TextView>(R.id.player2_text)
        playerOneValue = findViewById<TextView>(R.id.player1Value)
        playerTwoValue =  findViewById<TextView>(R.id.player2_value)
        reset = findViewById<Button>(R.id.reset)
        settingButton=findViewById<Button>(R.id.settings)
        Log.d(TAG, "${draughtModel.showBoard()}")

        val draughtView = findViewById<CustomView>(R.id.draught_view)
        draughtView.draughtService = this

        reset?.setOnClickListener{
            draughtModel.reset()
            draughtView.invalidate()
            setPlayerScores()
        }

        settingButton?.setOnClickListener { startSettingActivity() }
        setPlayerScores()


    }

    override fun pieceAt(col: Int, row: Int): DraughtPiece? {
        return draughtModel?.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.movePiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            setPlayerScores()
            return 0
        }
      return -1
    }

    override fun moveBlackPiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveBlackPiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            setPlayerScores()
            return 0
        }

        return -1
    }

    private fun setPlayerScores() {
        playerOneValue?.text = playOne().toString()
        playerTwoValue?.text = playTwo().toString()
    }

    override fun moveKing(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveKing(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            return 0
        }
        return -1
    }

    override fun moveBlackKing(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveBlackKing(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            return 0
        }
        return -1
    }

    override fun setFutureMove(): ArrayList<DraughtPiece> {
        return draughtModel.setFutureMove()
    }

    override fun clearFutureMove() {
        draughtModel.clearFutureMove()
    }


    override fun playOne(): Int {
        return draughtModel.playOne()
    }

    override fun playTwo(): Int {
        return draughtModel.playTwo()
    }

    private fun startSettingActivity(){
        var intent: Intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }


}