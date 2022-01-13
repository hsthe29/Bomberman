package com.example.view

import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.control.Slider
import javafx.scene.paint.Color
import tornadofx.*

class Settings : View("Settings") {
    lateinit var sld : Slider
    override val root = vbox {
        setPrefSize(500.0, 300.0)
        alignment = Pos.CENTER
        spacing = 20.0
        label("Volume")

        hbox {
            spacing = 10.0
            alignment = Pos.CENTER
            label("Mute")
            checkbox {
                action {
                    if(isSelected){
                        println("1")
                        sld.isDisable = true
                    }
                    else {
                        sld.isDisable = false
                    }
                }
            }
            sld = slider(0, 5, 2)
        }
        button("Save") {
            action {
                println(sld.value)
                runLater(100.millis) {
                    this@Settings.close()
                }
            }
        }
        hbox {
            paddingTop = 20.0
            paddingRight = 10.0
            alignment = Pos.CENTER_RIGHT
            text("Show Credit") {
                setOnMouseClicked {
                    find<ShowCredit>().openWindow(owner = null)
                    this@Settings.close()
                }
            }
        }
    }
}
