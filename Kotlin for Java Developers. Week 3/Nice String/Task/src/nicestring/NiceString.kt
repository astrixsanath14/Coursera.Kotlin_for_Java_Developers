package nicestring

fun String.isNice(): Boolean {
    val noBadSubstrings = setOf("bu", "ba", "be").none { this.contains(it) }

    val hasThreeVowels = count { it in "aeiou" } >= 3

    val hasDoubleLetter = zipWithNext().any { it.first == it.second }

    return listOf(noBadSubstrings, hasThreeVowels, hasDoubleLetter).count{it} >= 2
}

fun main(){
    println("aza".isNice())
}