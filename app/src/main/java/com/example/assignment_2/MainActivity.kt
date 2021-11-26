package com.example.assignment_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), DraughtService {
    private var draughtModel = DraughtModel()
    private var playerOneText: TextView? = null
    private var playerTwoText: TextView? = null
    private var playerOneValue: TextView? = null
    private var playerTwoValue: TextView? = null
    private var currentPlayer: TextView? = null
    private var settingButton: Button? = null
    private var reset: Button? = null
    private var isTrue:Boolean=false
    private var previousPlayOne:Boolean=false
    private var previousPlayTwo:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initiate variable
        playerOneText = findViewById<TextView>(R.id.player1_text)
        playerTwoText = findViewById<TextView>(R.id.player2_text)
        playerOneValue = findViewById<TextView>(R.id.player1Value)
        playerTwoValue =  findViewById<TextView>(R.id.player2_value)
        currentPlayer = findViewById(R.id.playerMove)
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
        currentPlayerMovement()


    }

    override fun pieceAt(col: Int, row: Int): DraughtPiece? {
        return draughtModel?.pieceAt(col, row)
    }

    override fun playerTwoMove(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.movePiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            isTrue=false
            currentPlayerMovement()
            setPlayerScores()

            return 0
        }
      return -1
    }

    override fun playerOneMove(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveBlackPiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            isTrue=true
            currentPlayerMovement()
            setPlayerScores()
            return 0
        }

        return -1
    }

    private fun setPlayerScores() {
        playerOneValue?.text = totalPlayerOnePieces().toString()
        playerTwoValue?.text = totalPlayerTwoPieces().toString()
    }

    override fun playerTwoKingMove(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveKing(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            isTrue=false
            currentPlayerMovement()
            setPlayerScores()
            return 0
        }
        return -1
    }

    override fun playerOneKingMove(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveBlackKing(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            isTrue=true
            currentPlayerMovement()
            setPlayerScores()
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


    override fun totalPlayerOnePieces(): Int {
        return draughtModel.playOne()
    }

    override fun totalPlayerTwoPieces(): Int {
        return draughtModel.playTwo()
    }

    private fun startSettingActivity(){
        var intent: Intent = Intent(this, Setting::class.java)
        startActivity(intent)
    }

    private fun currentPlayerMovement(){
        if(!isTrue && !previousPlayOne && setFutureMove().size!=2){
            currentPlayer?.text = "PlayerOne"
            previousPlayOne=true
            previousPlayTwo=false
        }
        else if(isTrue && previousPlayOne && setFutureMove().size==2){
            currentPlayer?.text = "PlayerOne"
            previousPlayOne=false
            previousPlayTwo=false
        }
        else if(isTrue && !previousPlayTwo && setFutureMove().size!=2){
            currentPlayer?.text = "PlayerTwo"
            previousPlayTwo=true
            previousPlayOne=false

        }
        else if(!isTrue && previousPlayTwo && setFutureMove().size==2){
            currentPlayer?.text = "PlayerTwo"
            previousPlayTwo=false
            previousPlayOne=false

        }
    }



}