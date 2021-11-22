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


    init {
        _green = Paint(Paint.ANTI_ALIAS_FLAG)
        _white = Paint(Paint.ANTI_ALIAS_FLAG)
        _black = Paint(Paint.ANTI_ALIAS_FLAG)
        _red = Paint(Paint.ANTI_ALIAS_FLAG)
        _green.setColor(Color.argb(255, 0, 255, 0))
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
        if (widthMeasureSpec > heightMeasureSpec) {
            parent = heightMeasureSpec
        } else {
            parent = widthMeasureSpec
        }

        this.setMeasuredDimension(parent, parent)
    }


    private fun drawPieces(canvas: Canvas?) {
        for (row in 7 downTo 0) {
            for (col in 7 downTo 0) {
                var piece = draughtService?.pieceAt(col, row)
                if (piece != null && piece.color == DraughtColor.RED && piece.draughtRank==DraughtRank.normal) {
                    drawPlayer1(canvas, row, col, _red)
                } else if (piece != null && piece.color == DraughtColor.BLACK && piece.draughtRank==DraughtRank.normal) {
                    drawPlayer1(canvas, row, col, _black)
                }
                else if(piece != null && piece.color == DraughtColor.RED && piece.draughtRank==DraughtRank.king){
                    drawPlayer2(canvas, row, col, _red)
                }
                else if(piece != null && piece.color == DraughtColor.BLACK && piece.draughtRank==DraughtRank.king){
                    drawPlayer2(canvas, row, col, _black)
                }
            }
        }

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
        var tempPiece = draughtService?.pieceAt(col, row)
        if(playerOneCounter && findPlayer(fromCol, fromRow)==0){

            if(fromRow==7 || tempPiece?.draughtRank==DraughtRank.king){
                if(draughtService?.moveBlackKing(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=true
                    playerOneCounter=false
                }

            }
            else if(draughtService?.pieceAt(fromCol, fromRow)?.draughtRank==DraughtRank.king){
                if(draughtService?.moveBlackKing(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=true
                    playerOneCounter=false
                }
            }
            else{
                if(draughtService?.moveBlackPiece(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=true
                    playerOneCounter=false
                }
            }

        }
        else if (playerTwoCounter && findPlayer(fromCol, fromRow)==1){
            if(row==0 || tempPiece?.draughtRank==DraughtRank.king){
                if(draughtService?.moveKing(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=false
                    playerOneCounter=true
                }
            }
            else if(draughtService?.pieceAt(fromCol, fromRow)?.draughtRank==DraughtRank.king){
                if(draughtService?.moveKing(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=false
                    playerOneCounter=true
                }
            }
            else{

                if(draughtService?.movePiece(fromCol, fromRow, col, row)==0){
                    playerTwoCounter=false
                    playerOneCounter=true
                }
            }

        }
        else;

    }

    private fun findPlayer(fromCol: Int, fromRow: Int):Int{
        var draughtPiece = draughtService?.pieceAt(fromCol, fromRow)
        draughtPiece?:return -1
        if(draughtPiece!!.color==DraughtColor.BLACK){
            return 0
        }
        return 1
    }



}