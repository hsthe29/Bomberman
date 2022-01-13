package com.example.view

import javafx.animation.Animation
import javafx.animation.AnimationTimer
import javafx.animation.Interpolator
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import tornadofx.*


class TestView : View("My View") {
    val cl = ColorAdjust().also { it.brightness = 0.0 }
    override val root = vbox {
        effect = cl
        alignment = Pos.CENTER

        setPrefSize(300.0, 400.0)
        val rectangle = rectangle(width = 60.0, height = 40.0) {
            padding = Insets(20.0)
        }

        timeline {
            keyframe(2.seconds) {
                keyvalue(rectangle.rotateProperty(), 180.0, interpolator = Interpolator.EASE_BOTH)
                keyvalue(rectangle.scaleXProperty(), 1.5)
                keyvalue(rectangle.scaleYProperty(), 1.5)
            }
            isAutoReverse = true
            cycleCount = Animation.INDEFINITE
        }
        button("Click") {
            action {
                timeline {
                    keyframe(5.seconds) {
                        keyvalue(cl.brightnessProperty(), 1)
                    }
                }
            }
        }
    }
}

class Control : Controller() {
    val view : TestView by inject()
    val wPressed: BooleanProperty = SimpleBooleanProperty()
    val aPressed: BooleanProperty = SimpleBooleanProperty()
    val sPressed: BooleanProperty = SimpleBooleanProperty()
    val dPressed: BooleanProperty = SimpleBooleanProperty()
    private val keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed)

    var timer: AnimationTimer = object : AnimationTimer() {
        override fun handle(timestamp: Long) {
            if (wPressed.get()) {
                println("w")
            }
            if (sPressed.get()) {
                println("s")
            }
            if (aPressed.get()) {
//                shape1.setLayoutX(shape1.getLayoutX() - movementVariable)
                println("a")

            }
            if (dPressed.get()) {
//                shape1.setLayoutX(shape1.getLayoutX() + movementVariable)
                println("d")

            }
        }
    }
    init {
        timer.start()
    }

}