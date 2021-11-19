package com.example.assignment_2

class DraughtClass {

    var pieceBox = mutableSetOf<DraughtPiece>()
    var listId1=arrayOf<Int>(1,  2,  3,  4, 5,  6,  7,  8)
    var listId2=arrayOf<Int>(9, 10, 11, 12, 13, 14, 15, 16)
    var listId3=arrayOf<Int>(7, 18, 19, 20, 21, 22, 23, 24)

    init{
        reset()
        //TODO
    }

    fun movePiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int){
        if(!correctMove(fromRow, fromCol, toRow, toCol)) return
        var movingPiece = pieceAt(fromCol, fromRow) ?: return
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var nextTempObject: DraughtPiece? = null
        var draughtPiece = pieceAt(toCol, toRow)?.let { it ->

            if (it.player == movingPiece.player) {
                return
            }
            if(it.row >  movingPiece.row){
                return
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

            if( tempObject !=null) return

            pieceBox.remove(it)
        }

        pieceBox.remove(movingPiece)
        if (pieceAt(toCol+offCol+offCol, toRow+offRow+offRow)!=null && toCol+offCol+offCol>0 && toRow+offRow+offRow<8){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))
            futureMove(toCol+offCol, toRow+offRow, toCol+offCol+offCol, toRow+offRow+offRow)
        }
        else{
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))
        }

    }

    fun moveBlackPiece(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int){
        if(!correctBlackMove(fromRow, fromCol, toRow, toCol)) return
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var movingPiece = pieceAt(fromCol, fromRow) ?: return
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if (it.player == movingPiece.player) {
                return
            }

            if(it.row <  movingPiece.row){
                return
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

            if( tempObject !=null) return
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        if (pieceAt(toCol+offCol+offCol, toRow+offRow+offRow)!=null
            && toCol+offCol+offCol>0 && toCol+offCol+offCol<6
            && toRow+offRow+offRow>0 && toRow+offRow+offRow<6){
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))
            futureBlackMove(toCol+offCol, toRow+offRow, toCol+offCol+offCol, toRow+offRow+offRow)
        }
        else{
            pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))
        }


    }

    private fun futureMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int) {
        var movingPiece = pieceAt(fromCol, fromRow) ?: return
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if(fromCol > toCol) {
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row-2)
                offRow=-1
                offCol=-1
            }
            else if(fromCol < toCol){
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row-2)
                offRow=-1
                offCol=1
            }


            if( tempObject !=null ) return
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))

    }

    private fun futureBlackMove(fromCol: Int, fromRow:Int, toCol:Int, toRow:Int) {
        var movingPiece = pieceAt(fromCol, fromRow) ?: return
        var offRow = 0
        var offCol=  0
        var tempObject: DraughtPiece? =null
        var draughtPiece = pieceAt(toCol, toRow)?.let {

            if(fromCol < toCol) {
                tempObject =  pieceAt(movingPiece.col +2, movingPiece.row+2)
                offRow=1
                offCol=1
            }
            else if(fromCol > toCol){
                tempObject =  pieceAt(movingPiece.col -2, movingPiece.row+2)
                offRow=1
                offCol=-1
            }

            if( tempObject !=null ) return
            pieceBox.remove(it)
        }
        pieceBox.remove(movingPiece)

        pieceBox.add(DraughtPiece(toCol+offCol, toRow+offRow, movingPiece.player, movingPiece.resId))


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
                pieceBox.add(DraughtPiece(i,0, DraughtPlayer.BLACK, listId1[i]))
            }
            else{
                pieceBox.add(DraughtPiece(i,7, DraughtPlayer.RED, listId1[i]))
            }
        }

        for(i in 0..7){
            if(i % 2 != 1){
                pieceBox.add(DraughtPiece(i,1, DraughtPlayer.BLACK, listId2[i]))
            }
            else{
                pieceBox.add(DraughtPiece(i,6, DraughtPlayer.RED, listId2[i]))
            }

        }

        for(i in 0..7){
            if(i % 2 == 1){
                pieceBox.add(DraughtPiece(i,2, DraughtPlayer.BLACK, listId3[i]))
            }
            else{
                pieceBox.add(DraughtPiece(i,5, DraughtPlayer.RED, listId3[i]))
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
                    val red = piece.player == DraughtPlayer.RED
                    val black = piece.player == DraughtPlayer.BLACK
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