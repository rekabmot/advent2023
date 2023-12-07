package day06

import utils.readInput

fun main() {
    val input = readInput("day06/input")

    val times = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    val distances = input[1].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

    val part1 = times.mapIndexed { index, time ->
        (1 until time).map { i -> i * (time - i) }.count { it > distances[index] }
    }.reduce { acc, i -> acc * i }

    println(part1)

    val p2Time = times.joinToString("").toLong()
    val p2distance = distances.joinToString("").toLong()

    val part2 = (1 until p2Time).map { i -> i * (p2Time - i) }.count { it > p2distance }
    println(part2)
}