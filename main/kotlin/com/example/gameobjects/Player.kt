package com.example.gameobjects

import com.example.systems.Engine
import com.example.variables.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.StackPane
import tornadofx.*

class Player(pos : Point, url : String) : Objects(pos, url){
    private var playerIndex = PREF_POS.copy()// 7, 1
    private var speed = 0.6 // max = 1.5
    private val gameEngine = Engine
    var blastArea = SimpleIntegerProperty(2)
    private val player = getObjects()
    private var thisPos = BOMB_INIT.copy()
    private val center = Point(0.0, 0.0)
    private var edge = 0.0
    private var scroll = 0.00125
    var numberOfBomb = SimpleIntegerProperty(1)

    private val realCoord = {
        val t = player.localToScene(player.boundsInLocal)
        center.x = t.minX + 25
        center.y = t.minY + 50
    }

    fun resetState(){
        speed = 0.6
        scroll = 0.00125
    }

    fun self() = player

    fun getIndex() = playerIndex.copy()

    fun reposition() {
        player.translateX = START_POS.x
        player.translateY = START_POS.y
        playerIndex = PREF_POS.copy()
        thisPos = BOMB_INIT.copy()
    }

    fun feasible(): Boolean {
        if(BOMB_POS[playerIndex.x][playerIndex.y] && numberOfBomb.value > 0){
            --numberOfBomb.value
            BOMB_POS[playerIndex.x][playerIndex.y] = false
            return true
        }
        return false
    }

    fun reset(x : Int, y : Int) {
        ++numberOfBomb.value
        BOMB_POS[x][y] = true
    }

    fun putBomb(map : StackPane) {
        val bomb = Bomb(this,  "/fixeds/bomb.png", "/fixeds/fire.png", map) //thisPos, playerIndex, blastArea.value
        BOMBLIST[playerIndex.x][playerIndex.y] = bomb
        map.add(bomb.self())
    }

    fun move(key : KeyCode, layout : Array<Array<StackPane>>, scr: ScrollPane) {
        realCoord()

        if(key == KeyCode.DOWN){
            val nextNode = layout[playerIndex.x + 1][playerIndex.y]
            edge = nextNode.localToScene(nextNode.boundsInLocal).minY
            if((edge - center.y > 5) || BOARD[playerIndex.x + 1][playerIndex.y]){
                player.translateY += speed
            }
        }
        if(key == KeyCode.UP){
            val nextNode = layout[playerIndex.x - 1][playerIndex.y]
            edge = nextNode.localToScene(nextNode.boundsInLocal).maxY
            if((center.y - edge > 16) || BOARD[playerIndex.x - 1][playerIndex.y]){
                player.translateY -= speed
            }
        }
        if(key == KeyCode.RIGHT){
            val nextNode = layout[playerIndex.x][playerIndex.y + 1]
            edge = nextNode.localToScene(nextNode.boundsInLocal).minX
            if((edge - center.x > 16) || BOARD[playerIndex.x][playerIndex.y + 1]){
                if(edge < 780.0 || scr.hvalue == 1.0)
                    player.translateX += speed
                else {
                    scr.hvalue += scroll
                }
            }
        }
        if(key == KeyCode.LEFT){
            val nextNode = layout[playerIndex.x][playerIndex.y - 1]
            edge = nextNode.localToScene(nextNode.boundsInLocal).maxX
            if((center.x - edge > 19) || BOARD[playerIndex.x][playerIndex.y - 1]){
                if(edge > 780.0 || scr.hvalue == 0.0)
                player.translateX -= speed
                else {
                    scr.hvalue -= scroll
                }
            }
        }
        update(key)
    }

    private fun update(key : KeyCode){
        when(key) {
            KeyCode.UP -> {
                if(center.y < edge) {
                    playerIndex.x -= 1
                    thisPos.y -= 55.2
                }
            }
            KeyCode.DOWN -> {
                if(center.y > edge) {
                    playerIndex.x += 1
                    thisPos.y += 55.2
                }
            }
            KeyCode.LEFT -> {
                if(center.x < edge) {
                    playerIndex.y -= 1
                    thisPos.x -= 55.2
                }
            }
            KeyCode.RIGHT -> {
                if(center.x > edge) {
                    playerIndex.y += 1
                    thisPos.x += 55.2
                }
            }
            else -> {}
        }
        if (ITEMS[playerIndex.x][playerIndex.y] != 0)
            gameEngine.collectItem(playerIndex.x, playerIndex.y)
    }

    fun getPos() = thisPos.copy()

    override fun toString() = "x : ${player.translateX}, y : ${player.translateY}"
    fun speedup(){
        if(speed < 1.5) {
            speed += 0.08
            scroll += 0.00023 //0.0012
        }
    }
}