package day01

import utils.readInput

const val NUMBERS = "(one|two|three|four|five|six|seven|eight|nine|[0-9])"
val FIRST = NUMBERS.toRegex()
val LAST = ".*$NUMBERS".toRegex()

fun stringToDigit(value: String) = when (value) {
    "one" -> "1"
    "two" -> "2"
    "three" -> "3"
    "four" -> "4"
    "five" -> "5"
    "six" -> "6"
    "seven" -> "7"
    "eight" -> "8"
    "nine" -> "9"
    else -> value
}

fun main() {
    val input = readInput("day01/input")

    val part1 = input.sumOf { x -> (x.first { it.isDigit() }.toString() + x.last { it.isDigit() }.toString()).toInt() }

    println(part1)

    val part2 = input.sumOf {
        val first = stringToDigit(FIRST.findAll(it).first().value)
        val last = stringToDigit(LAST.find(it)?.groupValues!![1])
        (first + last).toInt()
    }

    println(part2)
}