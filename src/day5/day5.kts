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
        const val OPCODE_JIT = 5
        const val OPCODE_JIF = 6
        const val OPCODE_LT = 7
        const val OPCODE_EQ = 8
        const val OPCODE_END = 99
    }
}

var intcodes = ArrayList<Int>()
var jump: Boolean = false

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

fun opcoodeJumpIfTrue(pos: Int, parameterMode: Int): Int {
    if (pos + 2 < intcodes.size) {
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]

        if (value1 != 0) {
            jump = true
            return value2
        }
    }

    return 3
}

fun opcoodeJumpIfFalse(pos: Int, parameterMode: Int): Int {
    if (pos + 2 < intcodes.size) {
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]

        if (value1 == 0) {
            jump = true
            return value2
        }
    }

    return 3
}

fun opcodeLessThan(pos: Int, parameterMode: Int): Int {
    if (pos + 3 < intcodes.size) {
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]
        val value3: Int = intcodes[pos+3]

        if (value3 < intcodes.size)
            intcodes[value3] = if (value1 < value2) 1 else 0
    }

    return 4
}

fun opcodeEquals(pos: Int, parameterMode: Int): Int {
    if (pos + 3 < intcodes.size) {
        val value1: Int = if ((parameterMode and 1) == 1) intcodes[pos+1] else intcodes[intcodes[pos+1]]
        val value2: Int = if (((parameterMode shr 1) and 1) == 1) intcodes[pos+2] else intcodes[intcodes[pos+2]]
        val value3: Int = intcodes[pos+3]

        if (value3 < intcodes.size)
            intcodes[value3] = if (value1 == value2) 1 else 0
    }

    return 4
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
        val parameterModes = getParameterModeByte(intcodes[pos])

        if (getOpcode(opCode) == Constants.OPCODE_END) break
        val inc = when (getOpcode(opCode)) {
            Constants.OPCODE_ADD -> opcodeAdd(pos, parameterModes)
            Constants.OPCODE_MUL -> opcodeMul(pos, parameterModes)
            Constants.OPCODE_SAVE -> opcodeSave(pos, input)
            Constants.OPCODE_LOAD ->  opcodeLoad(pos)
            Constants.OPCODE_JIF -> opcoodeJumpIfFalse(pos, parameterModes)
            Constants.OPCODE_JIT -> opcoodeJumpIfTrue(pos, parameterModes)
            Constants.OPCODE_LT -> opcodeLessThan(pos, parameterModes)
            Constants.OPCODE_EQ -> opcodeEquals(pos, parameterModes)
            else -> 0
        }

        if (jump) {
            pos = inc
            jump = false
        }
        else
            pos += inc

        if (inc == 0) {
            println(intcodes)
            break
        }
    }
}

if (args.size < 2)
    exitProcess(0)
readFile(args[0])

calculateValue(args[1].toInt())
