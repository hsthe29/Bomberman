package com.example.systems

import com.example.GameControler
import com.example.variables.*
import javafx.animation.Animation
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import tornadofx.*
import java.io.BufferedReader
import java.io.FileReader

object Engine {
    val layout = Array(15) {Array(35) {StackPane()} }
    var control : GameControler? = null
    var eff : ColorAdjust? = null
    private const val borderUrl = "/ui/play/border.png"
    private const val flatUrl = "/gameicon/flat.png"
    private const val brickUrl = "/gameicon/brick.png"
    private const val iceUrl = "/gameicon/ice.png"
    private const val keyUrl = "/gameicon/key (1).png"
    private const val doorUrl = "/gameicon/close.png"
    private const val openUrl = "/gameicon/open.png"
    private const val lifeUrl = "/gameicon/player/add_life.png"
    private val flameUrl = "/gameicon/player/add_flame.png"
    private const val speedUrl = "/gameicon/player/add_speed.png"
    private const val bombUrl = "/gameicon/player/add_bomb.png"
    private var isCompleted = false
    private val gate = Position(0, 0)
    var level = 0

    private fun reset() {
        BOMBLIST.forEach { it.fill(null) } //Array(15){ Array<Bomb?>(35) {null}}
        BOARD.forEach { it.fill(false) } // Array(16){Array(36) {false} }
        BOMB_POS.forEach { it.fill(true) } // Array(16) {Array (36) {true} }
        ICE_POS.forEach { it.fill(false) } // Array(16) {Array (36) {false} }
        ITEMS.forEach { it.fill(0) }
        isCompleted = false
        eff?.brightness = 0.0
    }
    fun loadMap() {

        reset()
        val url = "src/main/resources/level/level$level.txt"

        val br = BufferedReader(FileReader(url))
        var line: String
        for(i in 0 until 15) {
            line = br.readLine().trim()
            for(j in line.indices){
                layout[i][j].children.clear()
                when(line[j]) {
                    '#' -> {
                        layout[i][j].add(ImageView(borderUrl))
                    }
                    '*' -> {
                        layout[i][j].add(ImageView(brickUrl))
                    }
                    'x' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                    }
                    'k' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(keyUrl).also {
                            timeline {
                                keyframe(2.seconds) {
                                    keyvalue(it.scaleXProperty(), 1.5)
                                    keyvalue(it.scaleYProperty(), 1.5)
                                }
                                isAutoReverse = true
                                cycleCount = Animation.INDEFINITE
                            }
                        })
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                        ITEMS[i][j] = 10
                    }
                    '@' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(doorUrl))
                        BOARD[i][j] = true
                        ITEMS[i][j] = 100
                        gate.x = i
                        gate.y = j
                    }
                    'h' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(lifeUrl).also {
                            timeline {
                                keyframe(2.seconds) {
                                    keyvalue(it.scaleXProperty(), 1.2)
                                    keyvalue(it.scaleYProperty(), 1.2)
                                }
                                isAutoReverse = true
                                cycleCount = Animation.INDEFINITE
                            }
                        })
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                        ITEMS[i][j] = 20
                    }
                    'f' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(flameUrl).also {
                            timeline {
                                keyframe(2.seconds) {
                                    keyvalue(it.scaleXProperty(), 1.2)
                                    keyvalue(it.scaleYProperty(), 1.2)
                                }
                                isAutoReverse = true
                                cycleCount = Animation.INDEFINITE
                            }
                        })
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                        ITEMS[i][j] = 30
                    }
                    's' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(speedUrl).also {
                            timeline {
                                keyframe(2.seconds) {
                                    keyvalue(it.scaleXProperty(), 1.2)
                                    keyvalue(it.scaleYProperty(), 1.2)
                                }
                                isAutoReverse = true
                                cycleCount = Animation.INDEFINITE
                            }
                        })
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                        ITEMS[i][j] = 40
                    }
                    'b' -> {
                        layout[i][j].add(ImageView(flatUrl))
                        layout[i][j].add(ImageView(bombUrl).also {
                            timeline {
                                keyframe(2.seconds) {
                                    keyvalue(it.scaleXProperty(), 1.2)
                                    keyvalue(it.scaleYProperty(), 1.2)
                                }
                                isAutoReverse = true
                                cycleCount = Animation.INDEFINITE
                            }
                        })
                        layout[i][j].add(ImageView(iceUrl))
                        ICE_POS[i][j] = true
                        ITEMS[i][j] = 50
                    }
                    else -> {
                        layout[i][j].add(ImageView(flatUrl))
                        BOARD[i][j] = true
                    }
                }
            }
        }
        br.close()
    }

    fun removeIce(x : Int, y : Int) {
        ICE_POS[x][y] = false
        layout[x][y].children.removeLast()
        BOARD[x][y] = true
    }

    fun collectItem(x: Int, y : Int) {
        if(ITEMS[x][y] == 0)
            return
        if(ITEMS[x][y] == 100 && isCompleted) {
            timeline {
                keyframe(2.seconds) {
                    eff?.let { keyvalue(it.brightnessProperty(), 1.0) }
                }
            }
            runLater(2.2.seconds) {
                control?.messageState(true)
                control?.changeMap()
            }
            return
        }
        when(ITEMS[x][y]) {
            10 -> {
                isCompleted = true
                (layout[gate.x][gate.y].children.last() as ImageView).image = Image(openUrl)
            }
            20 -> { // healing
                control?.let{it.hitpoint.value++}
            }
            30 -> { // flamescope
                control?.let{it.flameScope.value++}
            }
            40 -> { // speed
                control?.speedUp()
            }
            50 -> {
                control?.addBomb()
            }
        }
        if(ITEMS[x][y] != 100) {
            ITEMS[x][y] = 0
            layout[x][y].children.removeLast()
        }
    }

    fun runOutHp(){
        control?.let{it.hitpoint.value--}
        if(control?.hitpoint?.value == 0){
            timeline {
                keyframe(2.seconds) {
                    eff?.let { keyvalue(it.brightnessProperty(), 1.0) }
                }
            }
            runLater(2.2.seconds) {
                control?.messageState(false)
                control?.changeMap()
                control?.reset()
            }
        }
    }
}