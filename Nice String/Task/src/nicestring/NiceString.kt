package nicestring

fun String.isNice(): Boolean {
    val isConditionPerformed = mutableListOf<Boolean>()
    val subString = listOf("bu", "ba", "be")
    isConditionPerformed.add(!subString.any { it in this })

    val vowelConsonant: (Char) -> Boolean =
        { char -> char == 'a' || char == 'e' || char == 'i' || char == 'o' || char == 'u' }
    isConditionPerformed.add(this.filter { vowelConsonant(it) }.length > 2)

    isConditionPerformed.add(this.zipWithNext { a, b -> a == b }.any { it })

    return isConditionPerformed.filter { it }.size > 1
}