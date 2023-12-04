package day03

import utils.Vec2
import utils.readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day03/input")

    var part1 = 0

    val potentialGears = HashMap<Vec2, ArrayList<Int>>()

    // Find potential gears
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '*') {
                potentialGears[Vec2(x, y)] = ArrayList()
            }
        }
    }

    input.forEachIndexed { i, line ->
        var j = 0

        while (j < line.length) {
            if (line[j].isDigit()) {
                var len = 0
                while (j + len < line.length && line[j + len].isDigit()) {
                    len++
                }

                val number = line.slice((j until (j + len))).toInt()

                val yRange = (max(i - 1, 0) .. min(i + 1, input.size - 1))
                val xRange = (max(j - 1, 0) until min(j + len + 1, line.length))


                if (isValid(yRange, xRange, input, number, potentialGears)) {
                    part1 += number
                }

                j += len
            } else {
                j++
            }
        }
    }

    println(part1)

    val foo = potentialGears.values.filter { it.size == 2 }.map { it.reduce { acc, next -> acc * next } }.sum()
    println(foo)
}



private fun isValid(
    yRange: IntRange,
    xRange: IntRange,
    input: List<String>,
    number: Int,
    potentialGears: HashMap<Vec2, ArrayList<Int>>
): Boolean {
    for (y in yRange) {
        for (x in xRange) {
            if (input[y][x] == '*') {
                potentialGears.getValue(Vec2(x, y)).add(number)
            }

            if (!input[y][x].isDigit() && input[y][x] != '.') {
                return true
            }
        }
    }
    return false
}