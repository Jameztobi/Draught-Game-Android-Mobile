package com.example.assignment_2

interface DraughtService {
    fun pieceAt(col: Int, row: Int)  : DraughtPiece?

    fun movePiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int)

    fun moveBlackPiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int)
}