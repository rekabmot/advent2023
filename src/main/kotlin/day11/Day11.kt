package day11

import utils.Vec2
import utils.readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day11/input")

    val emptyColumns = input.first().indices.filter { i -> input.all { it[i] != '#' } }
    val emptyRows = input.indices.filter { i -> !input[i].contains('#') }

    val galaxies = input.mapIndexed { y, line ->
        line.mapIndexed { x, c -> if (c == '#') Vec2(x, y) else null }.filterNotNull()
    }.flatten()

    val part1 = getDistances(galaxies, emptyColumns, emptyRows, 2)
    println(part1)

    val part2 = getDistances(galaxies, emptyColumns, emptyRows, 1000000)
    println(part2)
}

fun getDistances(
    galaxies: List<Vec2>,
    emptyColumns: List<Int>,
    emptyRows: List<Int>,
    expandFactor: Long
) = galaxies.mapIndexed { index, galaxy ->
    (index + 1 until galaxies.size).map { galaxies[it] }.sumOf { other ->
        val xRange = (min(galaxy.x, other.x) until max(galaxy.x, other.x))
        val yRange = (min(galaxy.y, other.y) until max(galaxy.y, other.y))

        val xExpand = emptyColumns.filter { xRange.contains(it) }.size
        val yExpand = emptyRows.filter { yRange.contains(it) }.size

        (xRange.count() - xExpand) + (xExpand * expandFactor) + (yRange.count() - yExpand) + (yExpand * expandFactor)
    }
}.sum()