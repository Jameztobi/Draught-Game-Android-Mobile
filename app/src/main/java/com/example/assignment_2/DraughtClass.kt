package com.example.assignment_2

import android.util.Log

class DraughtClass {

    var pieceBox = mutableSetOf<DraughtPiece>()
    var listId1=arrayOf<Int>(1,  2,  3,  4, 5,  6,  7,  8)
    var listId2=arrayOf<Int>(9, 10, 11, 12, 13, 14, 15, 16)
    var listId3=arrayOf<Int>(7, 18, 19, 20, 21, 22, 23, 24)

    init{
        reset()
        //TODO
    }

    fun movePiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean{
        if(!correctMove(fromRow, fromCol, toRow, toCol)) return false
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece = pieceAt(toCol, toRow)?.let { it ->
            if (it.color == movingPiece.color) {
                return false
            }
            if(it.row >  movingPiece.row){
                return false
            }
            if(fromRow > toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            }
            else if(fromRow > toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }
            if( tempObject !=null) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)
        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.color!=DraughtColor.RED){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true

        }

//
        return true

    }

    fun moveBlackPiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean{
        if(!correctBlackMove(fromRow, fromCol, toRow, toCol)) return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if (it.color == movingPiece.color) {
                return false
            }

            if(it.row <  movingPiece.row){
                return false
            }
            if(fromRow < toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }
            else if(fromRow < toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            }
            if( tempObject !=null) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.color!=DraughtColor.RED){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        return true

    }

    private fun checkForFutureMove(toRow: Int, offRow: Int, toCol: Int, offCol: Int, movingPiece: DraughtPiece):Boolean{
        when {
            pieceAt(movingPiece.col+1, movingPiece.row-1)!=null &&
                    pieceAt(movingPiece.col+1, movingPiece.row-1)!!.color!=DraughtColor.RED
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                    futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row-1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row-1)!!.color!=DraughtColor.RED
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                    futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)
                }
                return true
            }

            pieceAt(movingPiece.col+1, movingPiece.row+1)!=null
                    &&
                    movingPiece.draughtRank==DraughtRank.king
                    &&
                    pieceAt(movingPiece.col+1, movingPiece.row+1)?.color!=DraughtColor.RED
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                    futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row+1)!=null
                    &&
                    movingPiece.draughtRank==DraughtRank.king
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row+1)?.color!=DraughtColor.RED
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                    futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)
                }
                return true
            }

            else -> {
                return false
            }
        }
    }

    private fun checkForBlackFutureMove(toRow: Int, offRow: Int, toCol: Int, offCol: Int, movingPiece: DraughtPiece):Boolean{
        when {
            pieceAt(movingPiece.col+1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col+1, movingPiece.row+1)?.color!=DraughtColor.BLACK
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                    futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row+1)?.color!=DraughtColor.BLACK
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                    futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)
                }
                return true
            }
            pieceAt(movingPiece.col+1, movingPiece.row-1)!=null &&
                    pieceAt(movingPiece.col+1, movingPiece.row-1)!!.color!=DraughtColor.BLACK
                    &&
                    movingPiece.draughtRank==DraughtRank.king
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                    futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row-1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row-1)!!.color!=DraughtColor.BLACK
                    &&
                    movingPiece.draughtRank==DraughtRank.king
                    &&
                    movingPiece.col+1!=7 -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                    futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)
                }
                return true
            }


            else -> {
                return false
            }
        }
    }

    private fun getTypePlayer(toRow: Int, offRow: Int, toCol: Int, offCol: Int, movingPiece: DraughtPiece) {

        when {
            toRow + offRow == 0 -> {
                pieceBox.add(DraughtPiece(toCol + offCol, toRow + offRow, movingPiece.color, movingPiece.resId, DraughtRank.king)
                )
                Log.d(TAG, "KING")
            }
            movingPiece.draughtRank==DraughtRank.king -> {
                pieceBox.add(DraughtPiece(toCol + offCol, toRow + offRow, movingPiece.color, movingPiece.resId, DraughtRank.king)
                )
                Log.d(TAG, "KING")
            }
            else -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.color,
                        movingPiece.resId,
                        DraughtRank.normal
                    )
                )
            }
        }
        if(offCol!=0 && offRow!=0){
            checkForFutureMove(toRow, offRow, toCol, offCol,  DraughtPiece(
                toCol + offCol,
                toRow + offRow,
                movingPiece.color,
                movingPiece.resId,
                movingPiece.draughtRank
            ))
        }

    }
    private fun getBlackPlayer(
        toRow: Int,
        offRow: Int,
        toCol: Int,
        offCol: Int,
        movingPiece: DraughtPiece
    ) {
        when {
            toRow + offRow == 7 -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.color,
                        movingPiece.resId,
                        DraughtRank.king
                    )
                )
                Log.d(TAG, "KING")
            }
            movingPiece.draughtRank==DraughtRank.king -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.color,
                        movingPiece.resId,
                        DraughtRank.king
                    )
                )
                Log.d(TAG, "KING")
            }
            else -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.color,
                        movingPiece.resId,
                        DraughtRank.normal
                    )
                )
            }
        }

        if(offCol!=0 && offRow!=0){
            checkForBlackFutureMove(toRow, offRow, toCol, offCol,  DraughtPiece(
                toCol + offCol,
                toRow + offRow,
                movingPiece.color,
                movingPiece.resId,
                movingPiece.draughtRank
            ))
        }

    }

    fun moveKing(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int):Boolean{
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var nextTempObject: DraughtPiece? = null
        var draughtPiece = pieceAt(toCol, toRow)?.let { it ->

            if (it.color == movingPiece.color) {
                return false
            }

            if(fromRow < toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }
            else if(fromRow < toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            }
            else if(fromRow > toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            }
            else if(fromRow > toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }


            if( tempObject !=null) return false

            pieceBox.remove(it)
        }

        pieceBox.remove(movingPiece)
        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }
        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.color!=DraughtColor.RED){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

     return true
    }

    fun moveBlackKing(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean{
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if (it.color == movingPiece.color) {
                return false
            }

            if(fromRow < toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }
            else if(fromRow < toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            }
            else if(fromRow > toRow && fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            }
            else if(fromRow > toRow && fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }

            if( tempObject !=null) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }
        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.color!=DraughtColor.RED){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true

        }

        return true
    }


    private fun futureMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean {
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece = pieceAt(toCol, toRow)?.let {
            if (it.color == movingPiece.color) {
                return false
            }

            if(it.row >  movingPiece.row){
                return false
            }

            if(fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            }
            else if(fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }


            if( tempObject !=null ) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        if (toRow+offRow==0){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
            return true
        }
        else if(movingPiece.draughtRank==DraughtRank.king){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
            return true
        }
        else{
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal))
            return true
        }

    }

    private fun futureBlackMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean {
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece = pieceAt(toCol, toRow)?.let {
            if (it.color == movingPiece.color) {
                return false
            }

            if(it.row <  movingPiece.row){
                return false
            }
            if(fromCol > toCol) {
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            }
            else if(fromCol < toCol){
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }


            if( tempObject !=null ) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        if(toRow+offRow==7){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
            return true
        }
        else if(movingPiece.draughtRank==DraughtRank.king){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
            return true
        }
        else{
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal))
            return true

        }



    }



    private fun correctMove(fromRow:Int, fromCol:Int, toRow:Int, toCol:Int): Boolean {
        var offSetRow = fromRow-toRow
        var addOptionCol1 = fromCol+1
        var addOptionCol2 = fromCol-1

        if (offSetRow==1 && addOptionCol1 == toCol){
            return true
        }
        if (offSetRow==1 && addOptionCol2 == toCol)
            return true

        return false
    }

    private fun correctBlackMove(fromRow:Int, fromCol:Int, toRow:Int, toCol:Int): Boolean {
        var offSetRow = fromRow-toRow
        var addOptionCol1 = fromCol+1
        var addOptionCol2 = fromCol-1

        if (offSetRow==-1 && addOptionCol1 == toCol){
            return true
        }
        if (offSetRow==-1 && addOptionCol2 == toCol){
            return true
        }
        return false

    }

    fun reset(){
        pieceBox.removeAll(pieceBox)
        for(i in 0..7){
            if(i % 2==1){
                pieceBox.add(DraughtPiece(i,0, DraughtColor.BLACK, listId1[i], DraughtRank.normal))
            }
            else{
                pieceBox.add(DraughtPiece(i,7, DraughtColor.RED, listId1[i], DraughtRank.normal))
            }
        }

        for(i in 0..7){
            if(i % 2 != 1){
                pieceBox.add(DraughtPiece(i,1, DraughtColor.BLACK, listId2[i], DraughtRank.normal))
            }
            else{
                pieceBox.add(DraughtPiece(i,6, DraughtColor.RED, listId2[i], DraughtRank.normal))
            }

        }

        for(i in 0..7){
            if(i % 2 == 1){
                pieceBox.add(DraughtPiece(i,2, DraughtColor.BLACK, listId3[i], DraughtRank.normal))
            }
            else{
                pieceBox.add(DraughtPiece(i,5, DraughtColor.RED, listId3[i], DraughtRank.normal))
            }

        }
    }

    fun pieceAt(col:Int, row:Int) : DraughtPiece?{
        for(piece in pieceBox){
            if(col==piece.col && row==piece.row){
                return piece
            }
        }
        return null
    }


    fun showBoard(): String{
        var board = " \n"
        for(row in 7 downTo 0){
            board +="$row"
            for(col in 0..7){
                val piece = pieceAt(col, row)
                if(piece == null){
                    board+= " ."
                }
                else{
                    val red = piece.color == DraughtColor.RED
                    val black = piece.color == DraughtColor.BLACK
                    board +=" "

                    if(red){
                        board +="R"
                    }
                    else{
                        board +="B"
                    }

                }

            }

            board+= "\n"
        }
        board += "  0 1 2 3 4 5 6 7"
        return board
    }
}