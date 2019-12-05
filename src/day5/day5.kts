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
        const val OPCODE_SAVE = 3
        const val OPCODE_LOAD = 4
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

fun getOpcode(opcode: Int): Int = opcode % 100

fun getParameterModeByte(opcode: Int): Int {
    val parameterMode: String = opcode.toString().padStart(5, '0')
    var modes: Int = 0

    modes = modes or (parameterMode.substring(0,1).toInt() shl 2)
    modes = modes or (parameterMode.substring(1,2).toInt() shl 1)
    modes = modes or parameterMode.substring(2,3).toInt()

    return modes
}

fun opcodeAdd(pos: Int, parameterMode: Int): Int {
    if (pos + 3 < intcodes.size){
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]
        val value3: Int = intcodes[pos+3]

        if (value3 < intcodes.size)
            intcodes[value3] = value1 + value2
    }

    return 4
}

fun opcodeMul(pos: Int, parameterMode: Int): Int {
    if (pos + 3 < intcodes.size) {
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]
        val value3: Int = intcodes[pos+3]

        if (value3 < intcodes.size)
            intcodes[value3] = value1 * value2
    }

    return 4
}

fun opcodeSave(pos: Int, input: Int): Int {
    if (pos + 1 < intcodes.size) {
        val savePos: Int = intcodes[pos+1]
        if (savePos < intcodes.size) {
            intcodes[savePos] = input
        }
    }

    return 2
}

fun opcodeLoad(pos: Int): Int {
    if (pos + 1 < intcodes.size) {
        val loadPos: Int = intcodes[pos+1]
        if (loadPos < intcodes.size) {
            println("Load: ${intcodes[loadPos]}")
        }
    }

    return 2
}

fun calculateValue(input: Int) {
    var pos = 0
    while (pos < intcodes.size) {
        val opCode = intcodes[pos]

        if (getOpcode(opCode) == Constants.OPCODE_END) break
        val inc = when (getOpcode(opCode)) {
            Constants.OPCODE_ADD -> opcodeAdd(pos, getParameterModeByte(intcodes[pos]))
            Constants.OPCODE_MUL -> opcodeMul(pos, getParameterModeByte(intcodes[pos]))
            Constants.OPCODE_SAVE -> opcodeSave(pos, input)
            Constants.OPCODE_LOAD ->  opcodeLoad(pos)
            else -> 0
        }
        pos += inc
        if (inc == 0) {
            println(intcodes)
            break
        }
/*
        val intcode: Int = intcodes[pos]
        if (intcode == Constants.OPCODE_END) break
        val pos1: Int = intcodes[pos + 1]
        val pos2: Int = intcodes[pos + 2]
        val pos3: Int = intcodes[pos + 3]
        if (pos1 >= intcodes.size || pos2 >= intcodes.size || pos3 >= intcodes.size) break
        if (intcode == Constants.OPCODE_ADD) intcodes[pos3] = intcodes[pos1] + intcodes[pos2]
        if (intcode == Constants.OPCODE_MUL) intcodes[pos3] = intcodes[pos1] * intcodes[pos2]
        pos += 4
        */
    }
}

if (args.size < 2)
    exitProcess(0)
readFile(args[0])

// Puzzle 1
calculateValue(args[1].toInt())
