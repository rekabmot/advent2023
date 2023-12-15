package day13

import utils.readInput

fun main() {
    val input = readInput("day13/input").fold(mutableListOf(mutableListOf<String>())) { acc, next ->
        if (next.isEmpty()) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(next)
        }
        acc
    }

    val p1Indices = input.mapNotNull { getReflectiveIndex(it) ?: getReflectiveIndex(rotate(it))?.times(100) }

    val part1 = p1Indices.sum()
    println(part1)

    val part2 = input.mapIndexed { index, puzzle ->
        val x = getSmudgedReflectiveIndices(puzzle, if (p1Indices[index] < 100) p1Indices[index] else null)
        val y = getSmudgedReflectiveIndices(rotate(puzzle), if (p1Indices[index] >= 100) p1Indices[index] / 100 else null).map { it * 100 }
        listOf(x, y).flatten().first()
    }.sum()
    println(part2)
}

fun smudge(x: Int, y: Int, puzzle: List<String>) = puzzle.mapIndexed { index, it ->
    if (y == index) {
        val line = it.toMutableList()
        line[x] = if (line[x] == '.') '#' else '.'
        line.joinToString("")
    } else {
        it
    }
}

fun rotate(puzzle: List<String>) = puzzle.first().indices.map { index ->
    puzzle.map { it[index] }.joinToString("")
}

fun getSmudgedReflectiveIndices(puzzle: List<String>, exclusion: Int?) = puzzle.indices.map { y ->
    puzzle.first().indices.mapNotNull { x ->
        val smudged = smudge(x, y, puzzle)
        val index = getReflectiveIndex(smudged, exclusion)
        index
    }
}.flatten()

fun getReflectiveIndex(puzzle: List<String>, exclusion: Int? = null): Int? {
    val midPoint = puzzle.first().length / 2

    (0 until midPoint).forEach { index ->
        val sliceSize = midPoint - index

        val l = midPoint - 1 - index
        val r = midPoint + index

        val lResult = if (testIndex(puzzle, l, sliceSize)) l + 1 else null
        val rResult = if (testIndex(puzzle, r, sliceSize)) r + 1 else null

        if (lResult != null && lResult != exclusion) {
            return lResult
        } else if (rResult != null && rResult != exclusion) {
            return rResult
        }
    }

    return null
}

fun testIndex(puzzle: List<String>, midPoint: Int, sliceSize: Int) = puzzle.all {
    val l = midPoint - sliceSize
    val r = midPoint + sliceSize
    it.slice(l + 1..midPoint) == it.slice(midPoint + 1..r).reversed()
}