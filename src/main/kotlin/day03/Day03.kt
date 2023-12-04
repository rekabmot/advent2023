package day03

import utils.Vec2
import utils.readInput
import kotlin.math.max
import kotlin.math.min

val POTENTIAL_GEARS = HashMap<Vec2, ArrayList<Int>>()
val INPUT = readInput("day03/input")

fun main() {
    // Find potential gears
    INPUT.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '*') {
                POTENTIAL_GEARS[Vec2(x, y)] = ArrayList()
            }
        }
    }

    var part1 = 0

    INPUT.forEachIndexed { i, line ->
        var j = 0

        while (j < line.length) {
            if (line[j].isDigit()) {
                var len = 0
                while (j + len < line.length && line[j + len].isDigit()) {
                    len++
                }

                val number = line.slice((j until (j + len))).toInt()

                val yRange = (max(i - 1, 0) .. min(i + 1, INPUT.size - 1))
                val xRange = (max(j - 1, 0) until min(j + len + 1, line.length))

                if (isValid(yRange, xRange, number)) {
                    part1 += number
                }

                j += len
            } else {
                j++
            }
        }
    }

    println(part1)

    val part2 = POTENTIAL_GEARS.values.filter { it.size == 2 }.sumOf { it.reduce { acc, next -> acc * next } }
    println(part2)
}

private fun isValid(
    yRange: IntRange,
    xRange: IntRange,
    number: Int,
): Boolean {
    for (y in yRange) {
        for (x in xRange) {
            if (INPUT[y][x] == '*') {
                POTENTIAL_GEARS.getValue(Vec2(x, y)).add(number)
            }

            if (!INPUT[y][x].isDigit() && INPUT[y][x] != '.') {
                return true
            }
        }
    }
    return false
}