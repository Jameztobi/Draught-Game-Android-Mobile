package com.example.assignment_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomView(context: Context?, attribs: AttributeSet?) : View(context, attribs) {
    // private fields of the class
    private var _context: Context? = context
    private var _attribs: AttributeSet? = null
    private lateinit var _white: Paint
    var draughtService: DraughtService? = null
    private lateinit var _green: Paint
    private lateinit var _black: Paint
    private lateinit var _red: Paint
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
    private var playerOneTotalNumber=12
    private var playerTwoTotalNumber=12
    private var mainActivity:MainActivity= MainActivity()


    init {
        _green = Paint(Paint.ANTI_ALIAS_FLAG)
        _white = Paint(Paint.ANTI_ALIAS_FLAG)
        _black = Paint(Paint.ANTI_ALIAS_FLAG)
        _red = Paint(Paint.ANTI_ALIAS_FLAG)
        _green.color = Color.argb(255, 0, 255, 0)
        _white.setColor(Color.argb(255, 217, 219, 218))
        _black.setColor(Color.argb(255, 17, 18, 17))
        _red.setColor(Color.RED)
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
        drawBoard(canvas)
        drawPieces(canvas)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                 fromCol = ((event.x)/ recWidth).toInt()
                 fromRow = ((event.y)/ recWidth).toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "move")
            }

            MotionEvent.ACTION_UP -> {
                val col = ((event.x)/ recWidth).toInt()
                val row = ((event.y)/ recWidth).toInt()
                Log.d(TAG, "from ( $fromRow,$fromCol) to ( $row, $col)")
                gameController(fromCol, fromRow, col, row)

            }

        }
        //setPlayers()
        return true
    }

    private fun drawBoard(canvas: Canvas?) {
        for (i in 0..7) {
            for (j in 0..7) {
                drawSquareAt(canvas, i, j, (i + j) % 2 != 1)
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


    private fun drawPieces(canvas: Canvas?) {
        for (row in 7 downTo 0) {
            for (col in 7 downTo 0) {
                var piece = draughtService?.pieceAt(col, row)
                if (piece != null && piece.player == DraughtPlayers.PLAYER2 && piece.draughtRank==DraughtRank.NORMAL) {
                    drawPlayer1(canvas, row, col, _red)
                } else if (piece != null && piece.player == DraughtPlayers.PLAYER1 && piece.draughtRank==DraughtRank.NORMAL) {
                    drawPlayer1(canvas, row, col, _black)
                }
                else if(piece != null && piece.player == DraughtPlayers.PLAYER2 && piece.draughtRank==DraughtRank.KING){
                    drawPlayer2(canvas, row, col, _red)
                }
                else if(piece != null && piece.player == DraughtPlayers.PLAYER1 && piece.draughtRank==DraughtRank.KING){
                    drawPlayer2(canvas, row, col, _black)
                }
            }
        }

       // setPlayers()

    }

    private fun drawSquareAt(canvas: Canvas?, row: Int, col: Int, isGreen: Boolean) {
        paint = if (!isGreen) _green else _white
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




}