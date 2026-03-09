package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import java.util.Collections.swap

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        var index = 0
        for (row in 1..board.width) {
            for (col in 1..board.width) {
                board[Cell(row, col)] = initializer.initialPermutation[index++]
                if (index == initializer.initialPermutation.size) {
                    break
                }
            }
        }
    }

    override fun canMove() = true

    override fun hasWon() =
        board.getAllCells().map { board[it] }.subList(0, board.width * board.width - 1) == (1..15).toList()

    override fun processMove(direction: Direction) {
        board.moveValue(direction)
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

fun GameBoard<Int?>.moveValue(direction: Direction) {

    val emptyCell: Cell? = this.find { it == null }
    if (emptyCell == null) {
        return
    }
    when (direction) {
        Direction.UP -> {
            val neighbourCell = emptyCell.getNeighbour(Direction.DOWN) ?: return
            val neighbourValue = this[neighbourCell]
            this[neighbourCell] = null
            this[emptyCell] = neighbourValue
        }

        Direction.DOWN -> {
            val neighbourCell = emptyCell.getNeighbour(Direction.UP) ?: return
            val neighbourValue = this[neighbourCell]
            this[neighbourCell] = null
            this[emptyCell] = neighbourValue
        }

        Direction.LEFT -> {
            val neighbourCell = emptyCell.getNeighbour(Direction.RIGHT) ?: return
            val neighbourValue = this[neighbourCell]
            this[neighbourCell] = null
            this[emptyCell] = neighbourValue
        }

        Direction.RIGHT -> {
            val neighbourCell = emptyCell.getNeighbour(Direction.LEFT) ?: return
            val neighbourValue = this[neighbourCell]
            this[neighbourCell] = null
            this[emptyCell] = neighbourValue
        }
    }
}
