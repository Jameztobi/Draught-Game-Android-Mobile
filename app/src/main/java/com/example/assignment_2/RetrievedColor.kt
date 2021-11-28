package com.example.assignment_2

import java.io.Serializable

class RetrievedColor(var playerOneColor: String, var playerTwoColor: String, var boardColorOne: String, var boardColorTwo: String): Serializable{
    private var _playerOne=playerOneColor
    private var _playerTwo=playerTwoColor
    private var _boardOne = boardColorOne
    private var _boardTwo = boardColorTwo
}