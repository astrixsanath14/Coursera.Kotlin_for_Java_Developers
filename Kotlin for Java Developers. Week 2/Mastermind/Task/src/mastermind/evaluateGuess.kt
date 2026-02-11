package mastermind


//data class Evaluation(val rightPosition: Int, val wrongPosition: Int)
//
//fun evaluateGuess(secret: String, guess: String): Evaluation {
//    var rightPosition = 0
//    var wrongPosition = 0
//    val secretColorCount: HashMap<Char, Int> = HashMap()
//    val guessColorCount: HashMap<Char, Int> = HashMap()
//    for(position in 0 until secret.length) {
//        val secretCode = secret[position]
//        val guessCode = guess[position]
//        if (secret[position] == guess[position]) {
//            rightPosition++
//        } else {
//            secretColorCount[secretCode] = secretColorCount.getOrDefault(secretCode, 0) + 1
//            guessColorCount[guessCode] = guessColorCount.getOrDefault(guessCode, 0) + 1
//        }
//    }
////    println("$secretCode $guessCode ---")
//    for(code in 'A'..'F'){
////        println("$code ${secretColorCount[code]?:0} ${secretColorCount[code]?:0}" )
//        wrongPosition += Integer.min(secretColorCount[code]?:0, guessColorCount[code]?:0)
//    }
//    return Evaluation(rightPosition, wrongPosition)
//}

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { it.first == it.second }

    val commonLetters = "ABCDEF".sumBy { ch ->

        secret.count { it == ch }.coerceAtMost(guess.count { it == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = Evaluation(rightPosition = 1, wrongPosition = 1)
    evaluateGuess("BCDF", "ACEB") eq result
    evaluateGuess("AAAF", "ABCA") eq result
    evaluateGuess("ABCA", "AAAF") eq result
}


infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}