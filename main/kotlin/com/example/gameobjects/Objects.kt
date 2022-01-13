package com.example.gameobjects

import com.example.variables.Point
import javafx.geometry.Point2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView

open class Objects (pos : Point, url: String) {
    private val self = ImageView()
    init {
        visualize(url)
        self.translateX = pos.x
        self.translateY = pos.y
    }
    fun visualize(url : String){
        self.image = Image(url)
    }
    protected open fun getObjects() = self
}