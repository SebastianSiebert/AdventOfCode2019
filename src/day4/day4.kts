val validPasswords: ArrayList<Int> = ArrayList<Int>()
val validPasswords2: ArrayList<Int> = ArrayList<Int>()
val start: Int = 271973
val end: Int = 785961
val validRange = start..end

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

fun exactlyTwoAdjacentDigits(current: Int): Boolean {
    val currentString = current.toString()
    var number: Int = -1
    var count: Int = 0

    for (index in 0..currentString.length-1) {
        val curNumber = currentString.get(index).toInt()
        if (curNumber != number) {
            if (count == 2)
                return true
            number = curNumber
            count = 1
        }
        else {
            count++
        }

    }

    return count == 2
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
for (current in validRange) {
    if (isSixDigets(current) &&
            isWithinRange(current)
            && twoAdjacentDigitsAreTheSame(current)
            && digitsAreIncreasing(current))
        validPasswords.add(current)
}

println("Number of valid Passwords: ${validPasswords.size}")


// Puzzle 2
for (current in validRange) {
    if (isSixDigets(current) &&
            isWithinRange(current)
            && exactlyTwoAdjacentDigits(current)
            && digitsAreIncreasing(current))
        validPasswords2.add(current)
}

println("Number of valid Passwords: ${validPasswords2.size}")
