import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class Constants {
    companion object {
        const val OPCODE_ADD = 1
        const val OPCODE_MUL = 2
        const val OPCODE_END = 99
    }
}
var intcodes = ArrayList<Int>()

fun readFile(fileName: String) {
    File(fileName).useLines {
        it.forEach {
            run {
                it.split(",")
                        .map { it.toInt() }
                        .toCollection(intcodes)
            }
        }
    }
}

fun puzzle1() {
    calculateValue()
    System.out.println("Value at pos 0: " + intcodes[0])
}

fun calculateValue() {
    var pos = 0
    while (pos + 3 < intcodes.size) {
        val intcode: Int = intcodes[pos]
        if (intcode == Constants.OPCODE_END) break
        val pos1: Int = intcodes[pos + 1]
        val pos2: Int = intcodes[pos + 2]
        val pos3: Int = intcodes[pos + 3]
        if (pos1 >= intcodes.size || pos2 >= intcodes.size || pos3 >= intcodes.size) break
        if (intcode == Constants.OPCODE_ADD) intcodes[pos3] = intcodes[pos1] + intcodes[pos2]
        if (intcode == Constants.OPCODE_MUL) intcodes[pos3] = intcodes[pos1] * intcodes[pos2]
        pos += 4
    }
}

if (args.size == 0)
    exitProcess(0)
readFile(args[0])
puzzle1()
//puzzle2()
