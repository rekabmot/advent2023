package day01

import utils.readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val NUMBERS = "(one|two|three|four|five|six|seven|eight|nine|[0-9])"
val FIRST = NUMBERS.toRegex()
val LAST = ".*$NUMBERS".toRegex()

fun stringToDigit(value: String) = when(value) {
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

@OptIn(ExperimentalTime::class)
fun main() {
    val duration = measureTime {
        val input = readInput("day01/input")

        val part1 =
            input.sumOf { x -> (x.first { it.isDigit() }.toString() + x.last { it.isDigit() }.toString()).toInt() }
        println(part1)

        val part2 = input.sumOf {
            val first = stringToDigit(FIRST.find(it)?.value!!)
            val last = stringToDigit(LAST.find(it)?.groupValues?.last()!!)
            (first + last).toInt()
        }

        println(part2)
    }

    println(duration.inWholeMilliseconds)
}