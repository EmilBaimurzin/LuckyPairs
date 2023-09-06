package com.lucky.game.domain.other

data class PairsItem(
    val value: Int,
    var isOpened: Boolean = false,
    var openAnimation: Boolean = false,
    var closeAnimation: Boolean = false
)