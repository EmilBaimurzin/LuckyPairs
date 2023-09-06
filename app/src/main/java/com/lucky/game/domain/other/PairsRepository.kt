package com.lucky.game.domain.other

class PairsRepository {
    fun generateList(amount: Int): List<PairsItem> {
        val listToReturn = mutableListOf<PairsItem>()
        repeat(amount) {
            val value = when (it + 1) {
                in 1..2 -> 1
                in 3..4 -> 2
                in 5..6 -> 3
                in 7..8 -> 4
                in 9..10 -> 5
                in 11..12 -> 6
                in 13..14 -> 7
                in 15..16 -> 8
                in 17..18 -> 9
                in 19..20 -> 10
                in 21..22 -> 11
                else -> 12
            }
            listToReturn.add(PairsItem(value))
        }
        listToReturn.shuffle()
        return listToReturn
    }
}