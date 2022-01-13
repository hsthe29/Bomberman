package com.example.view

import com.example.GameControler
import com.example.Styles
import com.example.gameobjects.Bomb
import com.example.gameobjects.Player
import com.example.systems.Engine
import com.example.variables.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import tornadofx.*

class Game : View(title = "Bomberman") {
    private val control : GameControler by inject()
    val gameEngine = Engine
    private var row = 15
    private var col = 35
    private val mainBox = HBox()
    val playerObject = Player(START_POS, "/movable/player.png").also {
        it.blastArea = control.flameScope
        it.numberOfBomb = control.bombs
    }
    val player = playerObject.self()
    lateinit var sceneMap : StackPane
    lateinit var test : ImageView
    private val vbList = MutableList(35){ VBox() }
    val pauseIcon = Image("/ui/play/pause.png")
    private val playIcon = Image("/ui/play/play.png")
    lateinit var srl : ScrollPane
    lateinit var noti : ImageView
    val eff = ColorAdjust().also { it.brightness = 0.0 }

    val ps = VBox().also{
        it.spacing = 10.0
        it.add(label("Paused"))
        it.add(hbox {
            alignment = Pos.CENTER
            spacing = 20.0
            imageview("/ui/play/exitplay.png") {
                setOnMouseClicked {
                    control.returnMapFromGamePlay()
                }
            }
            imageview("/ui/play/playscale.png"){
                setOnMouseClicked {
                    control.play()
                }
            }
        })
        it.alignment = Pos.CENTER
        it.setMaxSize(300.0, 200.0)
        it.addClass(Styles.pause)
    }

    init {
        gameEngine.control = control
        prepare()
    }
    private fun prepare() {
        gameEngine.eff = eff
        mainBox.clear()
        createMap()
    }
    private fun createMap() {
        for(i in 0 until col){
            mainBox.add(vbList[i])
            vbList[i].children.clear()
            for(j in 0 until row){
                vbList[i].add(gameEngine.layout[j][i])
            }
        }
    }

    override val root = stackpane {
        effect = eff
        srl = scrollpane {
            vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            setPrefSize(1500.0, 830.0)
            sceneMap = stackpane {
                add(mainBox)
            }
        }

        add(player)

        keyboard {
            setOnKeyPressed {
                if(it.code == KeyCode.UP)
                    control.upPressed.value = true && control.isPlaying.value
                if(it.code == KeyCode.DOWN)
                    control.downPressed.value = true && control.isPlaying.value
                if(it.code == KeyCode.LEFT)
                    control.leftPressed.value = true && control.isPlaying.value
                if(it.code == KeyCode.RIGHT)
                    control.rightPressed.value = true && control.isPlaying.value
                if(it.code == KeyCode.SPACE) {
                    if(playerObject.feasible() && control.isPlaying.value){
                        playerObject.putBomb(sceneMap)
                        player.toFront()
                    }
                }
            }

            setOnKeyReleased {
                if(it.code == KeyCode.UP)
                    control.upPressed.value = false
                if(it.code == KeyCode.DOWN)
                    control.downPressed.value = false
                if(it.code == KeyCode.LEFT)
                    control.leftPressed.value = false
                if(it.code == KeyCode.RIGHT)
                    control.rightPressed.value = false
            }
        }
        // bombs
        imageview("/gameicon/player/add_bomb.png"){
            translateX = 410.0
            translateY = -390.0
        }

        text(control.bombs.asString()){
            translateX = 440.0
            translateY = -385.0
            addClass(Styles.heal)
        }
        // flames
        imageview("/gameicon/player/add_flame.png"){
            translateX = 490.0
            translateY = -385.0
        }

        text(control.flameScope.asString()){
            translateX = 520.0
            translateY = -385.0
            addClass(Styles.heal)
        }
        // heal
        imageview("/gameicon/player/add_life.png"){
            translateX = 570.0
            translateY = -385.0
        }

        text(control.hitpoint.asString()){
            translateX = 600.0
            translateY = -385.0
            addClass(Styles.heal)
        }
        imageview("/ui/play/exit.png"){
            translateX = 650.0
            translateY = -385.0
            setOnMouseClicked { control.returnMapFromGamePlay() }
        }
        noti = imageview(){
            image = pauseIcon
            translateX = 710.0
            translateY = -385.0
            setOnMouseClicked {
                control.isPlaying.value = false
                image = playIcon
                this@stackpane.add(ps)
            }
        }

    }
}