package day08

import utils.lcm
import utils.readInput

fun main() {
    val input = readInput("day08/input")

    val instructions = input[0]

    val map = input.drop(2).associate { line ->
        val (key, values) = line.split(" = ")

        key to values.subSequence(1, 9).split(", ")
    }

    // Part 1
    val part1 = cycleLengths(map, instructions, { it == "AAA" }, { it == "ZZZ" })
    println(part1[0])

    // Part 2
    val cycles = cycleLengths(map, instructions, { it.endsWith("A") }, { it.endsWith("Z") })
    val part2 = lcm(cycles.map { it.toLong() }.toLongArray())

    println(part2)
}

fun cycleLengths(
    map: Map<String, List<String>>,
    instructions: String,
    startCondition: (String) -> Boolean,
    endCondition: (String) -> Boolean
) = map.keys.filter(startCondition).map {
    var currentPos = it
    var moves = 0

    while (!endCondition(currentPos)) {
        currentPos = map.getValue(currentPos)[if (instructions[moves % instructions.length] == 'L') 0 else 1]
        moves++
    }
    moves
}