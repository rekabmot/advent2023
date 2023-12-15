package day10

import utils.Vec2
import utils.readInput

val NORTH = Vec2(0, -1)
val SOUTH = Vec2(0, 1)
val EAST = Vec2(1, 0)
val WEST = Vec2(-1, 0)
val NONE = Vec2(0, 0)

var pipes = mapOf(
    '|' to mapOf(NORTH to NORTH, SOUTH to SOUTH),
    '-' to mapOf(EAST to EAST, WEST to WEST),
    'L' to mapOf(SOUTH to EAST, WEST to NORTH),
    'J' to mapOf(SOUTH to WEST, EAST to NORTH),
    '7' to mapOf(NORTH to WEST, EAST to SOUTH),
    'F' to mapOf(WEST to SOUTH, NORTH to EAST),
    'S' to mapOf(NORTH to NONE, EAST to NONE, SOUTH to NONE, WEST to NONE)
)

fun main() {
    var maze = readInput("day10/test2")

    val startY = maze.indexOfFirst { it.contains("S") }
    val startX = maze[startY].indexOf("S")

    val startPosition = Vec2(startX, startY)
    val startDirection = arrayOf(NORTH, EAST, SOUTH, WEST).first {
        val dir = startPosition + it
        maze[dir.y][dir.x] != '.' && pipes.getValue(maze[dir.y][dir.x]).containsKey(it)
    }

    var (currentPosition, currentDirection) = move(startPosition, startDirection, maze)
    var steps = 1

    val positionsOnPath = mutableListOf(currentPosition, startPosition)

    while (currentPosition != startPosition) {
        val (nextPos, nextDirection) = move(currentPosition, currentDirection, maze)
        currentPosition = nextPos
        currentDirection = nextDirection
        positionsOnPath.add(nextPos)
        steps++
    }

    // Part 1
    println(steps / 2)
//    val declutteredMaze = maze.mapIndexed { y, line ->
//        line.mapIndexed { x, c ->
//            if (c != '.' && positionsOnPath.contains(Vec2(x, y))) {
//                c
//            } else {
//                '.'
//            }
//        }.joinToString("")
//    }
//
//    declutteredMaze.forEach { println(it) }
//
//    val boundaries = listOf('|', 'L', 'J', '7', 'F')
}

fun move(position: Vec2, direction: Vec2, maze: List<String>): Array<Vec2> {
    val nextPosition = position + direction
    val pipeType = maze[nextPosition.y][nextPosition.x]
    val nextDirection = pipes.getValue(pipeType).getValue(direction)
    return arrayOf(nextPosition, nextDirection)
}