package com.example.view

import javafx.geometry.Pos
import tornadofx.*

class ShowCredit : View("Credit") {
    override val root = vbox {
        setPrefSize(300.0, 180.0)
        alignment = Pos.CENTER
        spacing = 10.0
        hbox {
            alignment = Pos.CENTER
            text("Bomberman")
        }
        hbox {
            alignment = Pos.CENTER
            spacing = 10.0
            text("Author: ")
            text("Ho Sy The")
        }
        hbox {
            alignment = Pos.CENTER
            spacing = 10.0
            text("Facebook: ")
            text("https://facebook.com/segunda.etapa")
        }
        hbox {
            alignment = Pos.CENTER
            spacing = 10.0
            text("Github: ")
            text("https://github.com/hsthe29")
        }

    }
}
