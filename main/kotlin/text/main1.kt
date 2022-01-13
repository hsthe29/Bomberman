package text

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main() {
    val url = "src/main/resources/level/level1.txt"

    val file = File(url)
    println(file.absolutePath)
}