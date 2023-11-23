package com.zalomsky.sendto.presentation

import kotlin.random.Random

class ColorClass {

    fun generateRandomColor(): Int {
        val alpha = 255
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return android.graphics.Color.argb(alpha, red, green, blue)
    }
}