package com.example.view

import com.example.GameControler
import com.example.Styles
import com.example.systems.Engine
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class GameMap : View("Level") {
    private val gameEngine = Engine
    private val control : GameControler by inject()
    override val root = stackpane {
        setPrefSize(811.0, 800.0)

        imageview("/ui/level/maplevel.png")
        imageview("/ui/level/num1.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            setOnMouseClicked {
                gameEngine.level = 1
                openGame()
            }
            translateX = -50.0
            translateY = 190.0
        }
        imageview("/ui/level/num2.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            setOnMouseClicked {
                gameEngine.level = 2
                openGame()
            }
            translateX = 90.0
            translateY = 70.0
        }
        imageview("/ui/level/num3.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            setOnMouseClicked {
                gameEngine.level = 3
                openGame()
            }
            translateX = -70.0
            translateY = -70.0
        }
        imageview("/ui/level/num4.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            setOnMouseClicked {
                gameEngine.level = 4
                openGame()
            }
            translateX = -55.0
            translateY = -240.0
        }
        imageview("/ui/level/num5.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            setOnMouseClicked {
                gameEngine.level = 5
                openGame()
            }
            translateX = 110.0
            translateY = -200.0
        }
        imageview("/ui/level/exit.png"){
            onHover {
                if(isHover)
                    addClass(Styles.redText)
                else removeClass(Styles.redText)
            }
            translateX = 0.0
            translateY = 350.0
            setOnMouseClicked {
                find<MainView>().openWindow(owner = null)
                this@GameMap.close()
            }
        }
    }
    private fun openGame() {
        gameEngine.loadMap()
        control.reset()
        find<Game>().openWindow(owner = null)
        runLater(1.seconds) { control.isPlaying.value = true }
        this@GameMap.close()
    }
}
