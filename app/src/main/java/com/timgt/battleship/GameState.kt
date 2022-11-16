package com.timgt.battleship

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val stage: Stage,
    val myBoard: Board
)

@Serializable
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

@Serializable
data class Cell(
    val ship: Ship? = null,
    val isHit: Boolean = false
)

@Serializable
enum class Ship(val size: Int) {

    Destroyer(2),
    Submarine(3),
    Cruiser(3),
    Battleship(4),
    Carrier(5)
}
