package com.example.assignment_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), DraughtService {
    var draughtModel = DraughtClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "${draughtModel.showBoard()}")

        val draughtView = findViewById<CustomView>(R.id.draught_view)
        draughtView.draughtService = this

    }

    override fun pieceAt(col: Int, row: Int): DraughtPiece? {
        return draughtModel?.pieceAt(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.movePiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            return 0
        }
      return -1
    }

    override fun moveBlackPiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int): Int {
        if(draughtModel.moveBlackPiece(fromCol, fromRow, toCol, toRow)){
            val draughtView = findViewById<CustomView>(R.id.draught_view)
            draughtView.invalidate()
            return 0
        }

        return -1
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
}