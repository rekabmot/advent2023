package day09

import utils.readInput

fun main() {
    val input = readInput("day09/input").map { line -> line.split(" ").map { it.toInt() } }
    println(input.sumOf { next(it) })
    println(input.sumOf { next(it.reversed()) })
}

fun next(s: List<Int>): Int = if (s.all { it == 0 }) 0 else s.last() + next(s.windowed(2).map { it[1] - it[0] })
