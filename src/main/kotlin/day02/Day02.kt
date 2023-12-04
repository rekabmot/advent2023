package day02

import utils.readInput
import kotlin.math.max


class Game(val id: Int, private val rounds: List<Map<String, Int>>) {
    fun isPossible(available: Map<String, Int>): Boolean {
        return rounds.all { round ->
            available.keys.all { color ->
                available.getOrDefault(color, 0) >= round.getOrDefault(color, 0)
            }
        }
    }

    fun minRequirement(): Map<String, Int> {
        val result = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0
        )
        return rounds.fold(result) { acc, next ->
            acc.keys.forEach {
               acc[it] = max(acc.getOrDefault(it, 0), next.getOrDefault(it, 0))
            }
            acc
        }
    }

    fun power(): Int {
        return minRequirement().values.reduce { acc, next ->
            acc * next
        }
    }
}

fun main() {
    val input = readInput("day02/input").map {
        val (game, rounds) = it.split(": ")

        val gameId = game.split(" ")[1].toInt()

        val x = rounds.split("; ").map { round ->
            round.split(", ").associate { roundBalls ->
                val (count, color) = roundBalls.split(" ")
                color to count.toInt()
            }
        }

        Game(gameId, x)
    }

    // Part 1
    val available = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    val part1 = input.filter { it.isPossible(available) }.sumOf { it.id }
    println(part1)

    val part2 = input.sumOf { it.power() }
    println(part2)
}