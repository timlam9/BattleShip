package com.timgt.battleship

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class GameState(
    val stage: Stage,
    val myBoard: Board
)

@kotlinx.serialization.Serializable
@SerialName("stage")
enum class Stage {

    @SerialName("setup")
    Setup,

    @SerialName("playing")
    Playing,

    @SerialName("game_over")
    GameOver
}


typealias Board = List<Cell>

@kotlinx.serialization.Serializable
data class Cell(
    val ship: Ship? = null,
    val isHit: Boolean = false
)

@kotlinx.serialization.Serializable
enum class Ship(val size: Int) {

    Destroyer(2),
    Submarine(3),
    Cruiser(3),
    Battleship(4),
    Carrier(5)
}
