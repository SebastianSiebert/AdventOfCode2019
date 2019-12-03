import java.io.File
import kotlin.system.exitProcess

data class Point(val x: Int, val y: Int)
enum class Direction {
    UP, DOWN, LEFT, RIGHT, INVALID
}

val wire1: ArrayList<Point> = ArrayList<Point>()
val wire2: ArrayList<Point> = ArrayList<Point>()

fun getDirection(direction: String) = when (direction.substring(0, 1)) {
    "U" -> Direction.UP
    "D" -> Direction.DOWN
    "L" -> Direction.LEFT
    "R" -> Direction.RIGHT
    else -> Direction.INVALID
}

fun getDistance(direction: String): Int = direction.substring(1).toInt()

fun getCurrentList(number: Int): ArrayList<Point> = when(number) {
    0 -> wire1
    1 -> wire2
    else -> ArrayList<Point>()
}

fun fillWire(number: Int, directions: List<String>) {
    var x = 0
    var y = 0

    val wire = getCurrentList(number)
    wire.add(Point(0,0))

    directions.forEach({
        val dir = getDirection(it)
        val distance = getDistance(it)

        val range = when(dir) {
            Direction.UP -> y+1..y+distance
            Direction.DOWN -> y-1 downTo y-distance
            Direction.LEFT -> x-1 downTo x-distance
            Direction.RIGHT -> x+1..x+distance
            Direction.INVALID -> 0..0
        }
        when (dir) {
            Direction.UP, Direction.DOWN -> for (i in range) {
                wire.add(Point(x, i))
                y = i
            }
            Direction.RIGHT, Direction.LEFT -> for (i in range) {
                wire.add(Point(i, y))
                x = i
            }
            Direction.INVALID -> {}
        }
    })
}

fun readInput(fileName: String) {
    File(fileName).useLines {
        it.forEachIndexed { index, s ->
            run {
                fillWire(index, s.split(","))
            }
        }
    }
}

fun manhattanDistance(point1: Point, point2: Point) = Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y)

if (args.size == 0)
    exitProcess(0)

readInput(args[0])

// Puzzle1
val wireIntersection = wire1.intersect(wire2)

val crossing = wireIntersection.filter { it != Point(0,0) }.minBy { manhattanDistance(Point(0,0), it) }
if (crossing != null) {
    print("Distance to shortest Intersection: ")
    println(manhattanDistance(Point(0, 0), crossing))
}
