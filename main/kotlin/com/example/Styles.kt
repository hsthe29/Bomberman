package com.example

import javafx.geometry.Pos
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BackgroundSize
import javafx.scene.text.FontWeight
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.io.File
import java.net.URI

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val imgbg by cssclass()
        val bgrImg by cssclass()
        val redText by cssclass()
        val imgborder by cssclass()
        val gametitle by cssclass()
        val ice by cssclass()
        val pause by cssclass()
        val bgLevel by cssclass()
        val heal by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        imgbg {
            fontSize = 32.px
            fontFamily = "Comic Sans MS"
            textFill = Color.BLACK
            fontWeight = FontWeight.BLACK
            backgroundImage += URI("/menu/button.jpg")
            backgroundColor += Color.TRANSPARENT
            textBoxBorder = Color.WHITE
        }
        bgrImg {
            backgroundImage += URI("/background/clouds.png")
        }
        redText {
            fontSize = 40.px
            textFill = Color.RED
            opacity = 0.9
            scaleX = 1.1
            scaleY = 1.1
        }
        imgborder {
            borderColor += box(Color.BLUE)
            padding = box(2.px)
            borderWidth += box(4.px)
        }
        gametitle {
            fontWeight = FontWeight.BLACK
            fontFamily = "Courier New"
            fontSize = 100.px
        }
        ice {
            backgroundImage += URI("/gameicon/flat.png")
        }
        pause {
            fontFamily = "Comic Sans MS"
            fontWeight = FontWeight.BLACK
            fontSize = 30.px
            backgroundColor += c("#5996ff")
            borderWidth += box(2.px)
            borderRadius += box(10.0.px)
            backgroundRadius += box(10.0.px)
            borderColor += box(c("#0356fc"))
        }
        bgLevel {
            backgroundColor += c("#05a2fc")
        }
        heal {
            fontFamily = "Comic Sans MS"
            fontSize = 30.px
            fontWeight = FontWeight.BLACK
            text
            stroke = Color.WHITE
            strokeWidth = 1.px
        }
    }
}



















