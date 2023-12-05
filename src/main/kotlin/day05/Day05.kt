package day05

import utils.readInput

data class Mapping(val source: Long, val destination: Long, val range: Long)

fun main() {

    val rawInput = readInput("day05/input")

    val seeds = rawInput[0].split(": ")[1].split(" ").map { it.toLong() }

    val mappings = mutableListOf<MutableList<Mapping>>()
    var currentMappings = mutableListOf<Mapping>()

    rawInput.drop(2).forEach { line ->
        if (line.isEmpty()) {
            mappings.add(currentMappings)
            currentMappings = mutableListOf()
        } else if (!line.contains(":")){
            val (destination, source, range) = line.split(" ").map { it.toLong() }
            currentMappings.add(Mapping(source, destination, range))
        }
    }

    mappings.add(currentMappings)

    // Part 1
    val part1 = seeds.minOf { seed -> processSeed(seed, mappings) }
    println(part1)

    // Part 2
    val part2 = seeds.chunked(2).minOf {
        val (start, range) = it

        var x = start

        var min = Long.MAX_VALUE

        while (x < start + range) {
            val seedResult = processSeed(x, mappings)
            if (seedResult < min) {
                min = seedResult
            }
            x++
        }

        min
    }

    println(part2)
}

private fun processSeed(
    seed: Long,
    mappings: MutableList<MutableList<Mapping>>
): Long {
    var seedValue = seed
    mappings.forEach { currentMapping ->
        val mapping = currentMapping.firstOrNull {
            it.source <= seedValue && seedValue < it.source + it.range
        }

        if (mapping != null) {
            seedValue = mapping.destination + (seedValue - mapping.source)
        }
    }
    return seedValue
}