package com.example.gameobjects.obstacles

import com.example.gameobjects.Objects
import com.example.variables.Point

class IceBlock(pos : Point, url : String) : Objects(pos, url) {
    private val ice = getObjects()
    fun self() = ice

    fun collision() {

    }
}