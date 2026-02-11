package nicestring

fun String.containsAtLeastThreeVowels(): Boolean {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    return count { c -> c in vowels } >= 3
}

fun String.containsAtLeastOneDoubleLetter(): Boolean {
    if(length < 2) return false
    for(index in 1 until length) {
        if(this[index] == this[index - 1]) return true
    }
    return false
}

fun String.doesNotContainRestrictedSubstrings(): Boolean {
    return !(contains("bu") || contains("ba") || contains("be"))
}

fun Boolean.toInt() = if(this) 1 else 0

fun String.isNice(): Boolean {
    return (containsAtLeastThreeVowels().toInt() + containsAtLeastOneDoubleLetter().toInt() + doesNotContainRestrictedSubstrings().toInt()) >=2
}