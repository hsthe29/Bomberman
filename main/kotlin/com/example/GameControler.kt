package com.example

import com.example.view.*
import javafx.animation.AnimationTimer
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.input.KeyCode
import tornadofx.Controller
import tornadofx.runLater
import tornadofx.seconds

class GameControler : Controller() {
    private val view : Game by inject()
    private val chaneLevel : ChaneLevel by inject()
    val rightPressed = SimpleBooleanProperty()
    val leftPressed = SimpleBooleanProperty()
    val upPressed = SimpleBooleanProperty()
    val downPressed = SimpleBooleanProperty()
    val isPlaying = SimpleBooleanProperty(false)
    val hitpoint = SimpleIntegerProperty(2)
    val flameScope = SimpleIntegerProperty(2)
    val bombs = SimpleIntegerProperty(1)

    private val keyPressed = rightPressed.or(leftPressed).or(downPressed).or(upPressed)

    private var timer: AnimationTimer = object : AnimationTimer() {
        override fun handle(timestamp: Long) {
            if (upPressed.get()) {
                view.playerObject.move(KeyCode.UP, view.gameEngine.layout, view.srl)
            }
            if (downPressed.get()) {
                view.playerObject.move(KeyCode.DOWN, view.gameEngine.layout,  view.srl)
            }
            if (leftPressed.get()) {
                view.playerObject.move(KeyCode.LEFT, view.gameEngine.layout,  view.srl)
            }
            if (rightPressed.get()) {
                view.playerObject.move(KeyCode.RIGHT, view.gameEngine.layout,  view.srl)
            }
        }
    }

    fun play() {
        view.root.children.remove(view.ps)
        view.noti.image = view.pauseIcon
        isPlaying.value = true
    }
    fun returnMapFromGamePlay(){
         exitPlaying()
         view.close()
    }

    fun returnMapFromChangeLevel() {
        exitPlaying()
        chaneLevel.close()
    }

    private fun setStart() {
        play()
        view.playerObject.reposition()
        view.srl.hvalue = 0.0
    }

    private fun exitPlaying() {
        setStart()
        find<GameMap>().openWindow(owner = null)
        view.srl.hvalue = 0.0
    }

    fun nextLevel() {
        setStart()
        find<Game>().openWindow(owner = null)
        runLater(1.seconds) { isPlaying.value = true }
        chaneLevel.close()
    }

    fun changeMap() {
        play()
        view.playerObject.reposition()
        find<ChaneLevel>().openWindow(owner = null)
        view.close()
    }

    fun messageState(n : Boolean){
        chaneLevel.isNewGame = n
        chaneLevel.message.value = if(n) "Do you want to play next level?"
                                        else "Do you want to play again?"
    }

    fun speedUp() {
        view.playerObject.speedup()
    }

    fun addBomb() {
        bombs.value++
    }

    fun reset() {
        rightPressed.value = false
        leftPressed.value = false
        upPressed.value = false
        downPressed.value = false
        isPlaying.value = false
        hitpoint.value = 2
        flameScope.value = 2
        bombs.value = 1
        view.playerObject.resetState()
    }
    init {
        timer.start()
    }
}