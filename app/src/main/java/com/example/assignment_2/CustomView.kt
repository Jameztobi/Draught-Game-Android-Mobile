package com.example.assignment_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

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
                //draughtService?.movePiece(fromCol, fromRow, col, row)
                //draughtService?.moveBlackPiece(fromCol, fromRow, col, row)
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
                if (piece != null && piece.player == DraughtPlayer.RED) {
                    drawRedPlayer(canvas, row, col)
                } else if (piece != null && piece.player == DraughtPlayer.BLACK) {
                    drawBlackPlayer(canvas, row, col)
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

    private fun drawRedPlayer(canvas: Canvas?, col: Int, row: Int) {
        canvas?.save()
        canvas?.translate(((row) * recWidth) + len , len * (1 + col * 2))
        canvas?.drawCircle(0.0f, 0.0f, radius,   _red)
        canvas?.restore()
    }

    private fun drawBlackPlayer(canvas: Canvas?, col: Int, row: Int) {
        canvas?.save()
        canvas?.translate(((row) * recWidth) + len, len * (1 + col * 2))
        canvas?.drawCircle(0.0f, 0.0f, radius, _black)
        canvas?.restore()

    }

    private fun gameController(fromCol: Int, fromRow: Int, col: Int, row: Int) {
        if(playerOneCounter){
            draughtService?.moveBlackPiece(fromCol, fromRow, col, row)
            playerTwoCounter=true
            playerOneCounter=false
        }
        else if (playerTwoCounter){
            draughtService?.movePiece(fromCol, fromRow, col, row)
            playerTwoCounter=false
            playerOneCounter=true
        }
        else;

    }

    private fun findPlayer(fromCol: Int, fromRow: Int):Int{
        var draughtPiece = draughtService?.pieceAt(fromCol, fromRow)
        if(draughtPiece!!.player==DraughtPlayer.BLACK){
            return 0
        }
        return 1

    }



}