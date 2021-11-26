package com.example.assignment_2

import android.util.Log

class DraughtModel {

    var pieceBox = mutableSetOf<DraughtPiece>()
    var myBox = ArrayList<DraughtPiece>()
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
            if (it.player == movingPiece.player) {
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
            if(tempObject !=null && offCol!=0 && offRow!=0 || toCol==0  || toRow==0 || toCol==7) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)
        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId, DraughtRank.NORMAL)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.player!=DraughtPlayers.PLAYER2){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true

        }

        return true

    }

    fun moveBlackPiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean{
        if(!correctBlackMove(fromRow, fromCol, toRow, toCol)) return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if (it.player == movingPiece.player) {
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
            if( tempObject !=null && offCol!=0 && offRow!=0 || toCol==0 || toCol==7 || toRow==7) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId, DraughtRank.NORMAL)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.player!=DraughtPlayers.PLAYER2){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }

        return true

    }

    private fun checkForFutureMove(movingPiece: DraughtPiece):Boolean{
        when {
            pieceAt(movingPiece.col+1, movingPiece.row-1)!=null &&
                    pieceAt(movingPiece.col+1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row-1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col-1<=6
                     -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }


            else -> {
                return false
            }
        }
    }



    private fun checkForBlackFutureMove(movingPiece: DraughtPiece):Boolean{
        when {
            pieceAt(movingPiece.col+1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col+1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.col-1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                        return false
                    }
                }
                return true
            }


            else -> {
                return false
            }
        }
    }

    private fun checkForFutureKingMove(movingPiece: DraughtPiece):Boolean{
        when{
            pieceAt(movingPiece.col+1, movingPiece.row+1)!=null
                    &&
                    movingPiece.draughtRank==DraughtRank.KING
                    &&
                    pieceAt(movingPiece.col+1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row+1)!=null
                    &&
                    movingPiece.draughtRank==DraughtRank.KING
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col-1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col+1, movingPiece.row-1)!=null &&
                    pieceAt(movingPiece.col+1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row-1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER2
                    &&
                    movingPiece.col-1<=6
            -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }
            else -> {
                return false
            }

        }

    }

    private fun checkForBlackKingFutureMove(movingPiece: DraughtPiece):Boolean{
        when{
            pieceAt(movingPiece.col+1, movingPiece.row-1)!=null &&
                    pieceAt(movingPiece.col+1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.draughtRank==DraughtRank.KING
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row-1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row-1)!!.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.draughtRank==DraughtRank.KING
                    &&
                    movingPiece.col-1<=6
            -> {
                if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row-1)){
                    if(!futureMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row-1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col+1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col+1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.col+1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                        return false
                    }
                }
                return true
            }
            pieceAt(movingPiece.col-1, movingPiece.row+1)!=null
                    &&
                    pieceAt(movingPiece.col-1, movingPiece.row+1)?.player!=DraughtPlayers.PLAYER1
                    &&
                    movingPiece.col-1<=6
            -> {
                if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col-1, movingPiece.row+1)){
                    if(!futureBlackMove(movingPiece.col, movingPiece.row, movingPiece.col+1, movingPiece.row+1)){
                        return false
                    }
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
                pieceBox.add(DraughtPiece(toCol + offCol, toRow + offRow, movingPiece.player, movingPiece.resId, DraughtRank.KING)
                )
                Log.d(TAG, "KING")
            }
            movingPiece.draughtRank==DraughtRank.KING -> {
                pieceBox.add(DraughtPiece(toCol + offCol, toRow + offRow, movingPiece.player, movingPiece.resId, DraughtRank.KING)
                )
                Log.d(TAG, "KING")
                if(offCol!=0 && offRow!=0 && toCol > 0 && toCol <=6 && toRow > 0 && toRow<=6 ){
                    checkForFutureKingMove(DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        movingPiece.draughtRank
                    ))
                }
            }
            else -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        DraughtRank.NORMAL
                    )
                )
                if(offCol!=0 && offRow!=0 && toCol > 0 && toCol <=6 && toRow > 0 && toRow<=6 ){
                    checkForFutureMove(DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        movingPiece.draughtRank
                    ))
                }
            }
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
                        movingPiece.player,
                        movingPiece.resId,
                        DraughtRank.KING
                    )
                )
                Log.d(TAG, "KING")
            }
            movingPiece.draughtRank==DraughtRank.KING -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        DraughtRank.KING
                    )
                )
                Log.d(TAG, "KING")
                if(offCol!=0 && offRow!=0 && toCol > 0 && toCol <= 6 && toRow > 0 && toRow<=6){
                    checkForBlackKingFutureMove(DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        movingPiece.draughtRank
                    ))
                }
            }
            else -> {
                pieceBox.add(
                    DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        DraughtRank.NORMAL
                    )
                )

                if(offCol!=0 && offRow!=0 && toCol > 0 && toCol <= 6 && toRow > 0 && toRow<=6){
                    checkForBlackFutureMove(DraughtPiece(
                        toCol + offCol,
                        toRow + offRow,
                        movingPiece.player,
                        movingPiece.resId,
                        movingPiece.draughtRank
                    ))
                }
            }
        }



    }

    fun moveKing(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int):Boolean{
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var nextTempObject: DraughtPiece? = null
        var draughtPiece = pieceAt(toCol, toRow)?.let { it ->

            if (it.player == movingPiece.player) {
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

            if( tempObject !=null && offCol!=0 && offRow!=0 || toCol==0 || toRow==7) return false

            pieceBox.remove(it)
        }

        pieceBox.remove(movingPiece)
        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId, DraughtRank.KING)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getTypePlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }
        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.player!=DraughtPlayers.PLAYER2){
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

            if (it.player == movingPiece.player) {
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

            if( tempObject !=null && offCol!=0 && offRow!=0 || toCol==0 || toRow==7) return false
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        var mainPiece = DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId, DraughtRank.NORMAL)
        if(pieceAt(mainPiece.col, mainPiece.row)==null){
            getBlackPlayer(toRow, offRow, toCol, offCol, movingPiece)
            return true
        }
        else if(pieceAt(mainPiece.col, mainPiece.row)!=null && pieceAt(mainPiece.col, mainPiece.row)!!.player!=DraughtPlayers.PLAYER2){
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
        var draughtPiece: Unit? = pieceAt(toCol, toRow)?.let {

            if (it.player == movingPiece.player) {
                return false
            }


            if(it.row >  movingPiece.row){
                return false
            }

            if(fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            } else if(fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }


            if( tempObject !=null && offCol!=0 && offRow!=0 || toCol==0  || toRow==7) return false
            //pieceBox.remove(it)
        } ?: return false

        //pieceBox.remove(movingPiece)


        return when {
            toRow+offRow==0 -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.KING))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
                true
            }
            movingPiece.draughtRank==DraughtRank.KING -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.KING))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
                true
            }
            else -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.NORMAL))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal))
                true
            }
        }

    }

    private fun futureBlackMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int): Boolean {
        var movingPiece = pieceAt(fromCol, fromRow) ?: return false
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece: Unit? = pieceAt(toCol, toRow)?.let {
            if (it==null) return false
            if (it.player == movingPiece.player) {
                return false
            }

            if(it.row <  movingPiece.row){
                return false
            }
            if(fromCol > toCol) {
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            } else if(fromCol < toCol){
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }

            if( tempObject !=null && offCol!=0 && offRow!=0 || toCol==0  || toRow==7) return false
            // pieceBox.remove(it)
        } ?: return false

        //pieceBox.remove(movingPiece)

        return when {
            toRow+offRow==7 -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.KING))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
                true
            }
            movingPiece.draughtRank==DraughtRank.KING -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.KING))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.king))
                true
            }
            else -> {
                myBox.add(movingPiece)
                myBox.add(DraughtPiece(toCol, toRow, movingPiece.player, movingPiece.resId, DraughtRank.NORMAL))
                //pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.color, movingPiece.resId, DraughtRank.normal))
                true

            }
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
                pieceBox.add(DraughtPiece(i,0, DraughtPlayers.PLAYER1, listId1[i], DraughtRank.NORMAL))
            }
            else{
                pieceBox.add(DraughtPiece(i,7, DraughtPlayers.PLAYER2, listId1[i], DraughtRank.NORMAL))
            }
        }

        for(i in 0..7){
            if(i % 2 != 1){
                pieceBox.add(DraughtPiece(i,1, DraughtPlayers.PLAYER1, listId2[i], DraughtRank.NORMAL))
            }
            else{
                pieceBox.add(DraughtPiece(i,6, DraughtPlayers.PLAYER2, listId2[i], DraughtRank.NORMAL))
            }

        }

        for(i in 0..7){
            if(i % 2 == 1){
                pieceBox.add(DraughtPiece(i,2, DraughtPlayers.PLAYER1, listId3[i], DraughtRank.NORMAL))
            }
            else{
                pieceBox.add(DraughtPiece(i,5, DraughtPlayers.PLAYER2, listId3[i], DraughtRank.NORMAL))
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

    fun setFutureMove(): ArrayList<DraughtPiece> {
        return myBox
    }

    fun clearFutureMove(){
        myBox.removeAll(myBox)
    }

    fun playOne(): Int {
        var count=0
        pieceBox.forEach { piece ->
            if(piece.player == DraughtPlayers.PLAYER1){
                count++
            }

        }
        return count
    }

    fun playTwo(): Int{
        var count=0
        pieceBox.forEach { piece ->
            if(piece.player == DraughtPlayers.PLAYER2){
                count++
            }
        }
        return count
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
                    val red = piece.player == DraughtPlayers.PLAYER2
                    val black = piece.player == DraughtPlayers.PLAYER1
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