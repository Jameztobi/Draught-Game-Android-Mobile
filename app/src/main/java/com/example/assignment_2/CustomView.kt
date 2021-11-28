package com.example.assignment_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomView(context: Context?, attribs: AttributeSet?) : View(context, attribs) {
    // private fields of the class
    private var _context: Context? = context
    private var _attribs: AttributeSet? = null
    private lateinit var _boardColorTwo: Paint
    var draughtService: DraughtService? = null
    private lateinit var _boardColorOne: Paint
    private lateinit var _playerOneColor: Paint
    private lateinit var _playerTwoColor: Paint
    private lateinit var paint: Paint
    private var playerList: ArrayList<DraughtPiece> = ArrayList()
    private var canvas_width: Int = 0
    private var canvas_height: Int = 0
    private var playerOneCounter:Boolean=true
    private var playerTwoCounter:Boolean=false
    private var recWidth = canvas_width / 8.0f
    private var len = (canvas_width / 8.0f) / 2f
    private var radius = (canvas_width / 8.0f) / 4f
    private var fromCol:Int = -1
    private var fromRow:Int = -1
    private var futureMoveOne=false
    private var futureMoveTwo=false
    private var _canvas: Canvas? = null


    init {
        _boardColorOne = Paint(Paint.ANTI_ALIAS_FLAG)
        _boardColorTwo = Paint(Paint.ANTI_ALIAS_FLAG)
        _playerOneColor = Paint(Paint.ANTI_ALIAS_FLAG)
        _playerTwoColor = Paint(Paint.ANTI_ALIAS_FLAG)
        _boardColorOne.color = Color.argb(255, 0, 255, 0)
        _boardColorTwo.color = Color.argb(255, 217, 219, 218)
        _playerOneColor.color = Color.argb(255, 17, 18, 17)
        _playerTwoColor.color =  Color.RED
        paint = Paint()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //get the width and height of the canvas which is available drawing area
        canvas_width = canvas!!.width
        canvas_height = canvas!!.height
        recWidth = canvas_width / 8.0f
        len = (canvas_width / 8.0f) / 2f
        radius = (canvas_width / 8.0f) / 4f
        _canvas= canvas
        drawBoard()
        drawPieces()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                 fromCol = ((event.x)/ recWidth).toInt()
                 fromRow = ((event.y)/ recWidth).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
            }

            MotionEvent.ACTION_UP -> {
                val col = ((event.x)/ recWidth).toInt()
                val row = ((event.y)/ recWidth).toInt()
                gameController(fromCol, fromRow, col, row)

            }

        }
        //setPlayers()
        return true
    }

    private fun drawBoard() {
        for (i in 0..7) {
            for (j in 0..7) {
                drawSquareAt(_canvas, i, j, (i + j) % 2 != 1)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var parent: Int = 0
        parent = if (widthMeasureSpec > heightMeasureSpec) {
            heightMeasureSpec
        } else {
            widthMeasureSpec
        }

        this.setMeasuredDimension(parent, parent)
    }


    private fun drawPieces() {
        for (row in 7 downTo 0) {
            for (col in 7 downTo 0) {
                var piece = draughtService?.pieceAt(col, row)
                if (piece != null && piece.player == DraughtPlayers.PLAYER2 && piece.draughtRank==DraughtRank.NORMAL) {
                    drawPlayer1(_canvas, row, col, _playerTwoColor)
                } else if (piece != null && piece.player == DraughtPlayers.PLAYER1 && piece.draughtRank==DraughtRank.NORMAL) {
                    drawPlayer1(_canvas, row, col, _playerOneColor)
                }
                else if(piece != null && piece.player == DraughtPlayers.PLAYER2 && piece.draughtRank==DraughtRank.KING){
                    drawPlayer2(_canvas, row, col, _playerTwoColor)
                }
                else if(piece != null && piece.player == DraughtPlayers.PLAYER1 && piece.draughtRank==DraughtRank.KING){
                    drawPlayer2(_canvas, row, col, _playerOneColor)
                }
            }
        }

       // setPlayers()

    }

    private fun drawSquareAt(canvas: Canvas?, row: Int, col: Int, isGreen: Boolean) {
        paint = if (!isGreen) _boardColorOne else _boardColorTwo
        canvas?.drawRect(
            row * canvas_width / 8.0f,
            col * canvas_height / 8.0f,
            (row + 1) * canvas_width / 8.0f,
            (col + 1) * canvas_height / 8.0f,
            paint
        )
    }

    private fun drawPlayer1(canvas: Canvas?, col: Int, row: Int, color:Paint) {
        canvas?.save()
        canvas?.translate(((row) * recWidth) + len, len * (1 + col * 2))
        canvas?.drawCircle(0.0f, 0.0f, radius, color)
        canvas?.restore()

    }
    private fun drawPlayer2(canvas: Canvas?, col: Int, row: Int, color:Paint) {
        canvas?.save()
        canvas?.translate(((row) * recWidth) + len , len * (1 + col * 2))
        canvas?.drawCircle(0.0f, 0.0f, radius+(0.5f*radius),   color)
        canvas?.restore()
    }


    private fun gameController(fromCol: Int, fromRow: Int, col: Int, row: Int) {
        if(playerOneCounter && findPlayer(fromCol, fromRow)==0){
            if(fromRow==7 && draughtService?.pieceAt(col, row)==null){
                playerOneKingController(fromCol, fromRow, col, row)
            }
            else if(draughtService?.pieceAt(fromCol, fromRow)?.draughtRank==DraughtRank.KING){
                playerOneKingController(fromCol, fromRow, col, row)
            }
            else{
                playerOneController(fromCol, fromRow, col, row)
            }

        }
        else if (playerTwoCounter && findPlayer(fromCol, fromRow)==1){
            if(row==0 && draughtService?.pieceAt(col, row)==null){
                playerTwoKingController(fromCol, fromRow, col, row)
            }
            else if(draughtService?.pieceAt(fromCol, fromRow)?.draughtRank==DraughtRank.KING){
                playerTwoKingController(fromCol, fromRow, col, row)
            }
            else{
                playerTwoController(fromCol, fromRow, col, row)
            }

        }
        else;

    }

    private fun playerTwoController(
        fromCol: Int,
        fromRow: Int,
        col: Int,
        row: Int
    ) {
        if (!futureMoveOne) {
            if (draughtService?.playerTwoMove(fromCol, fromRow, col, row) == 0) {
                if (draughtService?.setFutureMove()?.size == 2) {
                    futureMoveOne = true
                    playerTwoCounter = true
                    playerOneCounter = false
                } else {
                    playerTwoCounter = false
                    playerOneCounter = true
                }
            }
        } else if (futureMoveOne) {
            var draughtPiece1 = draughtService?.setFutureMove()?.get(0)
            var draughtPiece2 = draughtService?.setFutureMove()?.get(1)
            if (draughtPiece1?.col == fromCol && draughtPiece1?.row == fromRow && draughtPiece2?.row == row && draughtPiece2?.col == col) {
                draughtService?.playerTwoMove(fromCol, fromRow, col, row)
                futureMoveOne = false
                draughtService?.clearFutureMove()
                playerTwoCounter = false
                playerOneCounter = true
            } else {
                return
            }
        }
    }

    private fun playerTwoKingController(
        fromCol: Int,
        fromRow: Int,
        col: Int,
        row: Int
    ) {
        if (!futureMoveOne) {
            if (draughtService?.playerTwoKingMove(fromCol, fromRow, col, row) == 0) {
                if (draughtService?.setFutureMove()?.size == 2) {
                    futureMoveOne = true
                    playerTwoCounter = true
                    playerOneCounter = false
                } else {
                    playerTwoCounter = false
                    playerOneCounter = true
                }
            }
        } else if (futureMoveOne) {
            var draughtPiece1 = draughtService?.setFutureMove()?.get(0)
            var draughtPiece2 = draughtService?.setFutureMove()?.get(1)
            if (draughtPiece1?.col == fromCol && draughtPiece1?.row == fromRow && draughtPiece2?.row == row && draughtPiece2?.col == col) {
                draughtService?.playerTwoKingMove(fromCol, fromRow, col, row)
                futureMoveOne = false
                draughtService?.clearFutureMove()
                playerTwoCounter = false
                playerOneCounter = true
            } else {
                return
            }
        }
    }

    private fun playerOneController(
        fromCol: Int,
        fromRow: Int,
        col: Int,
        row: Int
    ) {
        if (!futureMoveTwo) {
            if (draughtService?.playerOneMove(fromCol, fromRow, col, row) == 0) {
                if (draughtService?.setFutureMove()?.size == 2) {
                    futureMoveTwo = true
                    playerTwoCounter = false
                    playerOneCounter = true
                } else {
                    playerTwoCounter = true
                    playerOneCounter = false
                }

            }
        } else if (futureMoveTwo) {
            var draughtPiece1 = draughtService?.setFutureMove()?.get(0)
            var draughtPiece2 = draughtService?.setFutureMove()?.get(1)
            if (draughtPiece1?.col == fromCol && draughtPiece1?.row == fromRow && draughtPiece2?.row == row && draughtPiece2?.col == col) {
                draughtService?.playerOneMove(fromCol, fromRow, col, row)
                futureMoveTwo = false
                draughtService?.clearFutureMove()
                playerTwoCounter = true
                playerOneCounter = false
            } else {
                return
            }
        }
    }

    private fun playerOneKingController(
        fromCol: Int,
        fromRow: Int,
        col: Int,
        row: Int
    ) {
        if (!futureMoveTwo) {
            if (draughtService?.playerOneKingMove(fromCol, fromRow, col, row) == 0) {
                if (draughtService?.setFutureMove()?.size == 2) {
                    futureMoveTwo = true
                    playerTwoCounter = false
                    playerOneCounter = true
                } else {
                    playerTwoCounter = true
                    playerOneCounter = false
                }

            }
        } else if (futureMoveTwo) {
            var draughtPiece1 = draughtService?.setFutureMove()?.get(0)
            var draughtPiece2 = draughtService?.setFutureMove()?.get(1)
            if (draughtPiece1?.col == fromCol && draughtPiece1?.row == fromRow && draughtPiece2?.row == row && draughtPiece2?.col == col) {
                draughtService?.playerOneKingMove(fromCol, fromRow, col, row)
                futureMoveTwo = false
                draughtService?.clearFutureMove()
                playerTwoCounter = true
                playerOneCounter = false
            } else {
                return
            }
        }
    }

    private fun findPlayer(fromCol: Int, fromRow: Int):Int{
        var draughtPiece = draughtService?.pieceAt(fromCol, fromRow)
        draughtPiece?:return -1
        if(draughtPiece!!.player==DraughtPlayers.PLAYER1){
            return 0
        }
        return 1
    }


    fun updateBoardColor(retrievedColor: RetrievedColor){
        for(element in  basicColors()){
            if(element.name ==retrievedColor.playerOneColor) _playerOneColor.color = parseColor(element.hex)
            if(element.name ==retrievedColor.playerTwoColor) _playerTwoColor.color = parseColor(element.hex)
            if(element.name ==retrievedColor.boardColorOne)  _boardColorOne.color = parseColor(element.hex)
            if(element.name ==retrievedColor.boardColorTwo) _boardColorTwo.color = parseColor(element.hex)

        }
        invalidate()
    }

    fun reset(){
        playerOneCounter=true
        playerTwoCounter=false
        futureMoveOne=false
        futureMoveTwo=false
    }

    private fun basicColors(): List<ColorObject>
    {
        return listOf(
            ColorObject("Black", "#010b13"),
            ColorObject("Silver", "#C0C0C0"),
            ColorObject("Gray", "#808080"),
            ColorObject("Maroon", "#800000"),
            ColorObject("Red", "#FF0000"),
            ColorObject("Fuchsia", "#FF00FF"),
            ColorObject("Green", "#008000"),
            ColorObject("Lime", "#00FF00"),
            ColorObject("Olive", "#808000"),
            ColorObject("Yellow", "#FFFF00"),
            ColorObject("Navy", "#000080"),
            ColorObject("Blue", "#0000FF"),
            ColorObject("Teal", "#008080"),
            ColorObject("Aqua", "#00FFFF"),
            ColorObject("White", "#FFFFFF")
        )

    }


}