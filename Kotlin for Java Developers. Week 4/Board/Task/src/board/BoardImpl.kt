package board

import board.Direction.*

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    var boardCells: Array<Array<Cell>> = emptyArray()
    init {
        boardCells = Array(width) { Array(width) { Cell(0, 0) } }
        for(row in 1..width){
            for(col in 1..width){
                boardCells[row-1][col-1] = Cell(row, col)
            }
        }
    }
    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i in 1..width && j in 1..width)
            return getCell(i, j)
        return null
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i in 1..width && j in 1..width)
            return boardCells[i-1][j-1]
        throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> =
        boardCells.flatten()


    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return when {
            jRange.last > width -> IntRange(jRange.first, width).map { j: Int -> getCell(i, j) }.toList()
            else -> jRange.map { j: Int -> getCell(i, j) }.toList()
        }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return when {
            iRange.last > width -> IntRange(iRange.first, width).map { i: Int -> getCell(i, j) }.toList()
            else -> iRange.map { i: Int -> getCell(i, j) }.toList()
        }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i + 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    val board = mutableMapOf<Cell, T?>()

    init {
        boardCells.forEach { unit -> unit.forEach { cell -> board[cell] = null } }
    }

    override fun get(cell: Cell): T? {
        return board[cell]
    }

    override fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return board.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return board.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return board.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return board.values.all(predicate)
    }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

