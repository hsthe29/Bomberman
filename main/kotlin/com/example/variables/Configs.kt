package com.example.variables

import com.example.gameobjects.Bomb
data class Point(var x : Double, var y : Double){
    override fun toString() = "$x,  $y"
}
data class Position(var x : Int, var y : Int){
    override fun toString() = "$x,  $y"
}

val START_POS = Point(-663.0, -3.0) // -663.0, -3.0
val PREF_POS = Position(7, 1)
val BOMB_INIT = Point(-883.2, 0.0)
// BOMLIST represents if BOMLIST[i][j] == null then Coor(i, j) has bomb
val BOMBLIST = Array(15){ Array<Bomb?>(35) {null}}

// BOARD: if player can walk through BOARD[i][j] then BOARD[i][j] = true
val BOARD = Array(16){Array(36) {false} }

// BOMB_POS: if coor(i, j) can put bomb then BOMB_POS[i][j] = true, Each cell can only hold one bomb at a time
val BOMB_POS = Array(16) {Array (36) {true} }

// ICE_POS: if coor(i, j) has ice then ICE_POS[i][j] = true
val ICE_POS = Array(16) {Array (36) {false} }

val ITEMS = Array(16) {Array (36) {0} }



