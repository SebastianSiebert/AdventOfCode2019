import java.io.File
import java.util.function.Function
import kotlin.system.exitProcess

data class ObjectInSpace(val name: String)
data class Orbit(val objectInSpace: ObjectInSpace) {
    var isInOrbitOf: Orbit? = null
    var orbitsInOrbit: ArrayList<Orbit> = ArrayList<Orbit>()
}

val orbitMap: HashMap<ObjectInSpace, Orbit> = HashMap<ObjectInSpace, Orbit>()
val com: ObjectInSpace = ObjectInSpace("COM")

fun readFile(fileName: String) {
    File(fileName).useLines {
        it.forEach {
            run {
                val values = it.split(")")
                if (values.size == 2)
                    createOrFillOrbit(values[0], values[1])
            }
        }
    }
}

fun createOrFillOrbit(obj1: String, obj2: String) {
    val objInSpace = ObjectInSpace(obj1)
    val orbitsObj = ObjectInSpace(obj2)

    val orbit1 = orbitMap.getOrPut(objInSpace, { Orbit(objInSpace) })
    val orbit2 = orbitMap.getOrPut(orbitsObj, { Orbit(orbitsObj) })

    orbit1.orbitsInOrbit.add(orbit2)
    orbit2.isInOrbitOf = orbit1
}

fun countOrbits(): Int {
    var count = 0

    orbitMap.forEach { obj, orbit -> run {
            var curOrbit: Orbit? = orbit
            while (curOrbit != null && curOrbit.isInOrbitOf != null) {
                curOrbit = curOrbit.isInOrbitOf
                count++
            }
        }
    }

    return count
}

if (args.size < 1)
    exitProcess(0)
readFile(args[0])

val orbits = countOrbits()
println("Direct and Indirect Orbits: $orbits")
