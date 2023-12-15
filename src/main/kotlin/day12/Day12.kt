package day12

import utils.readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day12/input")

    val part1 = input.sumOf { line ->
        val (springs, groupsRaw) = line.split(" ")
        val groups = groupsRaw.split(",").map { it.toInt() }

        val permutations = generatePermutations(springs)

        permutations.count { isValid(it, groups) }
    }
    println(part1)
}

fun isValid(springs: String, groups: List<Int>): Boolean {
    val springGroups = springs.split(".").filter { it.isNotEmpty() }.map { it.length }

    return springGroups.size == groups.size && springGroups.withIndex().all {
        it.value == groups[it.index]
    }
}

fun generatePermutations(input: String): List<String> {
    val unknownCount = input.count { it == '?' }
    val numberOfPermutations = 2.0.pow(unknownCount).toInt()

    val unknownIndices = input.withIndex().filter { it.value == '?' }.map { it.index }

    val permutations = (0 until numberOfPermutations).map {
        val mask = Integer.toBinaryString(it).padStart(unknownCount, '0')

        input.mapIndexed { index, c ->
            if (c == '?') {
                if (mask[unknownIndices.indexOf(index)] == '1') {
                    '#'
                } else {
                    '.'
                }
            } else {
                c
            }
        }.joinToString("")
    }

    return permutations
}