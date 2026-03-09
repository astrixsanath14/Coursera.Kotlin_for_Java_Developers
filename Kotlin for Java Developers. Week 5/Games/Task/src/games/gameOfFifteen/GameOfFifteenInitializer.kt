package games.gameOfFifteen

import java.util.Collections.shuffle
import java.util.Collections.swap

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val list = (1..15).toMutableList()
        list.shuffle()

        // Check if the number of inversions is even
        if (!isEven(list)) {
            swap(list, 0, 1)
        }
        return@lazy list
    }
}

