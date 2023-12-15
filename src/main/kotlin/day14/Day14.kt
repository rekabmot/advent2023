package day14

import utils.readInput

fun main() {
    var input = readInput("day14/input").map { it.toMutableList() }

    val seenGrids = mutableListOf<String>()
    val weights = mutableListOf<Int>()

    var matchIndex = -1

    while (matchIndex < 0) {
        tiltNorth(input)
        tiltWest(input)
        tiltSouth(input)
        tiltEast(input)

        weights.add(input.mapIndexed { y, line ->
            line.filter { it == 'O' }.size * (input.size - y)
        }.sum())

        val gridString = input.joinToString("") { it.joinToString("") }

        matchIndex = seenGrids.indexOf(gridString)

        seenGrids.add(gridString)
    }

    val cycle = weights.subList(matchIndex, weights.size - 1)
    val part2 = cycle[(1000000000 - matchIndex) % cycle.size - 1]

    println(part2)
}

fun tiltNorth(input: List<MutableList<Char>>) {
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == 'O' && y > 0) {
                val ahead = ((y - 1) downTo 0).map { input[it][x] }
                val destination = ahead.indexOfFirst { it != '.' }

                if (destination == -1) {
                    input[y][x] = '.'
                    input[0][x] = 'O'
                } else {
                    input[y][x] = '.'
                    input[y - destination][x] = 'O'
                }
            }
        }
    }
}

fun tiltWest(input: List<MutableList<Char>>) {
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == 'O' && x > 0) {
                val ahead = ((x - 1) downTo 0).map { input[y][it] }

                val destination = ahead.indexOfFirst { it != '.' }

                if (destination == -1) {
                    input[y][x] = '.'
                    input[y][0] = 'O'
                } else {
                    input[y][x] = '.'
                    input[y][x - destination] = 'O'
                }
            }
        }
    }
}

fun tiltSouth(input: List<MutableList<Char>>) {
    input.indices.reversed().forEach { y ->
        val line = input[y]
        line.forEachIndexed { x, c ->
            if (c == 'O' && y < input.size - 1) {
                val ahead = ((y + 1) until input.size).map { input[it][x] }

                val destination = ahead.indexOfFirst { it != '.' }

                if (destination == -1) {
                    input[y][x] = '.'
                    input[input.size - 1][x] = 'O'
                } else {
                    input[y][x] = '.'
                    input[y + destination][x] = 'O'
                }
            }
        }
    }
}

fun tiltEast(input: List<MutableList<Char>>) {
    input.forEachIndexed { y, line ->
        line.indices.reversed().forEach { x ->
            val c = line[x]
            if (c == 'O' && x < line.size - 1) {
                val ahead = ((x + 1) until line.size).map { input[y][it] }
                val destination = ahead.indexOfFirst { it != '.' }

                if (destination == -1) {
                    input[y][x] = '.'
                    input[y][line.size - 1] = 'O'
                } else {
                    input[y][x] = '.'
                    input[y][x + destination] = 'O'
                }
            }
        }
    }
}