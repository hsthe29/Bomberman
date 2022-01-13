package com.example.gameobjects

import com.example.GameControler
import com.example.systems.Engine
import com.example.variables.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Point2D
import javafx.scene.layout.StackPane
import kotlinx.coroutines.delay
import tornadofx.*
import java.util.*
import kotlin.concurrent.schedule

class Bomb(private val playerObject: Player, url : String, private val img : String, private val map : StackPane) : Objects(playerObject.getPos(), url) {

    private val _pos = playerObject.getPos()
    private val gameEngine = Engine
    private val index = playerObject.getIndex()
    private var blastArea = 0
    private val checkTime = SimpleBooleanProperty(true)
    private var co_x = 0
    private var co_y = 0
    private var xc = 1
    private val bomb = getObjects()
    private var setTime : FXTimerTask? = null
    private val countdown = 0.4

    fun blast() {
        playerObject.reset(index.x, index.y)
        BOMBLIST[index.x][index.y] = null
        map.children.remove(bomb)
        blastArea = playerObject.blastArea.value

        var (up, down, left, right) = Array(4){true}
        val pos = _pos.copy()
        val t = Fire(pos, img)
        map.add(t.self().also {
            it.scale(countdown.seconds, Point2D(1.2, 1.2))
        })
        if(index.x == playerObject.getIndex().x && index.y == playerObject.getIndex().y)
            gameEngine.runOutHp()
        runLater(countdown.seconds) { map.children.remove(t.self())}
        for(i in 1..blastArea) {
            runLater((i * 100).millis) {
                if (right && !BOARD[index.x][index.y + i])
                    right = false || ICE_POS[index.x][index.y + i]
                if (left && !BOARD[index.x][index.y - i])
                    left = false || ICE_POS[index.x][index.y - i]
                if (up && !BOARD[index.x - i][index.y])
                    up = false || ICE_POS[index.x - i][index.y]
                if (down && !BOARD[index.x + i][index.y])
                    down = false || ICE_POS[index.x + i][index.y]
                if (right) {
                    co_x = index.x
                    co_y = index.y + i
                    pos.y = _pos.y
                    pos.x = _pos.x + 55.2 * i
                    trigger(co_x, co_y)
                    val temp = Fire(pos, img)
                    map.add(temp.self().also {
                        it.scale(countdown.seconds, Point2D(1.2, 1.2))
                    })
                    runLater(countdown.seconds) {
                        map.children.remove(temp.self())
                    }
                    if(co_x == playerObject.getIndex().x && co_y == playerObject.getIndex().y)
                        gameEngine.runOutHp()
                    if(ICE_POS[co_x][co_y]){
                        gameEngine.removeIce(co_x, co_y)
                        right = false
                    }
                }
                if (down) {
                    co_x = index.x + i
                    co_y = index.y
                    pos.x = _pos.x
                    pos.y = _pos.y + 55.2 * i
                    trigger(co_x, co_y)
                    val temp = Fire(pos, img)
                    map.add(temp.self().also {
                        it.scale(countdown.seconds, Point2D(1.2, 1.2))
                    })
                    runLater(countdown.seconds) {
                        map.children.remove(temp.self())
                    }
                    if(co_x == playerObject.getIndex().x && co_y == playerObject.getIndex().y)
                        gameEngine.runOutHp()
                    if(ICE_POS[co_x][co_y]){
                        gameEngine.removeIce(co_x, co_y)
                        down = false
                    }
                }
                if (left) {
                    co_x = index.x
                    co_y = index.y - i
                    pos.y = _pos.y
                    pos.x = _pos.x - 55.2 * i
                    trigger(co_x, co_y)
                    val temp = Fire(pos, img)
                    map.add(temp.self().also {
                        it.scale(countdown.seconds, Point2D(1.2, 1.2))
                    })
                    runLater(countdown.seconds) {
                        map.children.remove(temp.self())
                    }
                    if(co_x == playerObject.getIndex().x && co_y == playerObject.getIndex().y)
                        gameEngine.runOutHp()
                    if(ICE_POS[co_x][co_y]){
                        gameEngine.removeIce(co_x, co_y)
                        left = false
                    }
                }
                if (up) {
                    co_x = index.x - i
                    co_y = index.y
                    pos.x = _pos.x
                    pos.y = _pos.y - 55.2 * i
                    trigger(co_x, co_y)
                    val temp = Fire(pos, img)
                    map.add(temp.self().also {
                        it.scale(countdown.seconds, Point2D(1.2, 1.2))
                    })
                    if(co_x == playerObject.getIndex().x && co_y == playerObject.getIndex().y)
                        gameEngine.runOutHp()
                    runLater(countdown.seconds) { map.children.remove(temp.self()) }
                    if(ICE_POS[co_x][co_y]){
                        gameEngine.removeIce(co_x, co_y)
                        up = false
                    }
                }
            }
        }

    }

    fun trigger(x : Int, y : Int){
        if(BOMBLIST[x][y] != null){
            BOMBLIST[x][y]?.checkTime?.value = false
            BOMBLIST[x][y]?.blast()
        }
    }

    fun self() = bomb.also {
        it.scale(3.8.seconds, Point2D(1.4, 1.4))
        setTime  = runLater(4.seconds) {
            if(checkTime.value)
                blast()
        }
    }
}