package day04

import utils.readInput
import kotlin.math.pow

const val BASE = 2.0
val CACHE = HashMap<Int, Int>()

fun main() {
    val games = readInput("day04/input").map { game ->
        val (gameId, gameNumbers) = game.split(": ")

        val (winningNumbersRaw, myNumbersRaw) = gameNumbers.split(" | ")

        val winningNumbers = winningNumbersRaw.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val myNumbers = myNumbersRaw.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val matchedNumbers = myNumbers.filter { winningNumbers.contains(it) }

        gameId.split(" ").last().toInt() to matchedNumbers.size
    }.toMap()

    // Part 1
    val part1 = games.values.sumOf { matches ->
        if (matches > 0) {
            BASE.pow(matches - 1).toInt()
        } else {
            0
        }
    }

    println(part1)

    // Part 2
    val part2 = games.keys.sumOf { processCard(it, games) }
    println(part2)
}


fun processCard(gameId: Int, games: Map<Int, Int>): Int {
    if (games[gameId] == 0) {
        return 1
    }

    return 1 + (gameId + 1 .. gameId + games.getValue(gameId)).fold(0) { acc, next ->
        if (CACHE.containsKey(next)) {
            acc + CACHE.getValue(next)
        } else {
            val processedCardResult = processCard(next, games)
            CACHE[next] = processedCardResult
            acc + processedCardResult
        }
    }
}