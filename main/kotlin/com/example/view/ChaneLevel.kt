package com.example.view

import com.example.GameControler
import com.example.Styles
import com.example.systems.Engine
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*

class ChaneLevel : View() {
    private val control : GameControler by inject()
    private val gameEngine = Engine
    val message = SimpleStringProperty("")
    var isNewGame = true
    override val root = vbox {
        setPrefSize(500.0, 200.0)
        spacing = 10.0
        alignment = Pos.CENTER
        addClass(Styles.bgLevel)
        label(message){
            addClass(Styles.pause)
        }
        hbox {
            spacing = 30.0
            alignment = Pos.CENTER
            imageview("/ui/play/exitplay.png") {
                setOnMouseClicked {
                    control.returnMapFromChangeLevel()
                }
            }
            imageview("/ui/play/playscale.png"){
                setOnMouseClicked {
                    if(isNewGame && gameEngine.level != 5)
                        gameEngine.level++
                    gameEngine.loadMap()
                    control.reset()
                    control.nextLevel()
                }
            }
        }
    }
}
