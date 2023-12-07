package day07

import utils.readInput

enum class HandType(val value: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

enum class CompareMode { P1, P2 }

fun cardValue(card: Char, mode: CompareMode) = when(card) {
    'A' -> 14
    'K' -> 13
    'Q' -> 12
    'J' -> if (mode == CompareMode.P1) 11 else 1
    'T' -> 10
    else -> card.digitToInt()
}

fun compareHands(a: Hand, b: Hand, mode: CompareMode): Int {
    val aType = if (mode == CompareMode.P1) a.p1Type else a.p2Type
    val bType = if (mode == CompareMode.P1) b.p1Type else b.p2Type

    if (aType == bType) {
        for (i in (0 until a.cards.length)) {
            val aValue = cardValue(a.cards[i], mode)
            val bValue = cardValue(b.cards[i], mode)
            if (aValue != bValue) {
                return aValue - bValue
            }
        }
        return 0
    } else {
        return aType.value - bType.value
    }
}

data class Hand(val cards: String, val p1Type: HandType, val p2Type: HandType, val bet: Int)

fun getHandType(groupings: Map<Char, Int>): HandType {
    val maxGroup = groupings.values.maxOf { it }
    return when (groupings.size) {
        1 -> HandType.FIVE_OF_A_KIND
        2 -> if (maxGroup == 4) HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
        3 -> if (maxGroup == 3) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
        4 -> HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
    }
}

fun main() {
    val input = readInput("day07/input").map { line ->
        val (cards, bet) = line.split(" ")

        val groupings = cards.groupingBy { it }.eachCount().toMutableMap()

        val p1HandType = getHandType(groupings)

        if (p1HandType != HandType.FIVE_OF_A_KIND && groupings.containsKey('J')) {
            val jokers = groupings.remove('J')
            val max = groupings.maxBy { (_, value) -> value }.key
            groupings[max] = groupings.getValue(max) + jokers!!
        }

        val p2HandType = getHandType(groupings)

        Hand(cards, p1HandType, p2HandType, bet.toInt())
    }

    val part1 = input.sortedWith { a, b -> compareHands(a, b, CompareMode.P1) }.mapIndexed { index, hand -> hand.bet * (index + 1) }.sum()
    println(part1)

    val part2 = input.sortedWith { a, b -> compareHands(a, b, CompareMode.P2) }.mapIndexed { index, hand -> hand.bet * (index + 1) }.sum()
    println(part2)
}