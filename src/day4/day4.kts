val validPasswords: ArrayList<Int> = ArrayList<Int>()
val start: Int = 271973
val end: Int = 785961

fun isSixDigets(current: Int): Boolean = current.toString().length == 6

fun isWithinRange(current: Int): Boolean = current >= start && current <= end

fun twoAdjacentDigitsAreTheSame(current: Int): Boolean {
    val currentString = current.toString()

    for (index in 1..currentString.length-1) {
        if (currentString.get(index - 1) == currentString.get(index))
            return true
    }

    return false
}

fun digitsAreIncreasing(current: Int): Boolean {
    val currentString = current.toString()

    for (index in 1..currentString.length-1) {
        if (currentString.get(index - 1) > currentString.get(index))
            return false
    }
    return true
}


// Puzzle 1
val validRange = start..end

for (current in validRange) {
    if (isSixDigets(current) &&
            isWithinRange(current)
            && twoAdjacentDigitsAreTheSame(current)
            && digitsAreIncreasing(current))
        validPasswords.add(current)
}


println("Number of valid Password: ${validPasswords.size}")


