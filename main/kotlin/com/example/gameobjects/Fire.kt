package com.example.gameobjects

import com.example.variables.Point

class Fire(pos : Point, url : String) : Objects(pos, url){
    private val fire = getObjects()
    fun self() = fire
}