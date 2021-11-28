package com.example.assignment_2.service

import com.example.assignment_2.model.DraughtPiece

interface DraughtService {
    fun pieceAt(col: Int, row: Int)  : DraughtPiece?

    fun playerTwoMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int):Int

    fun playerOneMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int):Int

    fun playerTwoKingMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int):Int

    fun playerOneKingMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Int

    fun setFutureMove(): ArrayList<DraughtPiece>

    fun clearFutureMove()

    fun totalPlayerOnePieces(): Int

    fun totalPlayerTwoPieces(): Int
}