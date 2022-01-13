package com.example.view

import com.example.Styles
import com.example.gameobjects.Player
import com.sun.tools.javac.Main
import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.ParallelTransition
import javafx.animation.TranslateTransition
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.util.Duration
import tornadofx.*


class MainView : View(title = "Bomberman") {
    override val root = vbox {
        setPrefSize(1500.0, 800.0)
        val img = Image("/background/sky.jpeg")

        background =  Background(BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize(1.0, 1.0, true, true, false, false)))
        hbox {
            paddingTop = 10.0
            alignment = Pos.CENTER
            label("BOMBERMAN"){
                addClass(Styles.gametitle)
            }
        }
        vbox {
            paddingTop = 320.0
            alignment = Pos.CENTER
            spacing = 5.0
            button(text = "Play") {
                onHover {
                    if(isHover)
                        addClass(Styles.redText)
                    else removeClass(Styles.redText)
                }
                addClass(Styles.imgbg)
                setPrefSize(306.0, 96.0)
                action {
                    find<GameMap>().openWindow(owner = null)
                    this@MainView.close()
                }
            }
            button(text = "Settings" ) {
                onHover {
                    if(isHover)
                        addClass(Styles.redText)
                    else removeClass(Styles.redText)
                }
                addClass(Styles.imgbg)
                setPrefSize(306.0, 96.0)
                action { find<Settings>().openWindow(owner = null) }
            }
            button(text = "Quit") {
                onHover {
                    if(isHover)
                        addClass(Styles.redText)
                    else removeClass(Styles.redText)
                }
                addClass(Styles.imgbg)
                setPrefSize(306.0, 96.0)
                action { this@MainView.close() }
            }
        }
    }
}

