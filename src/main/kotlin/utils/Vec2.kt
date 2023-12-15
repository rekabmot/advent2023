package utils

import kotlin.math.abs

data class Vec2(val x: Int, val y: Int) {
    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
    fun manhattanTo(other: Vec2) = abs(other.x - x) + abs(other.y - y)
}