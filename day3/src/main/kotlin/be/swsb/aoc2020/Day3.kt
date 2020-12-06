import be.swsb.aoc.common.Position

typealias DownStep = Int
typealias RightStep = Int
data class Slope(val down: DownStep, val right: RightStep)
fun Slope.toPosition() = Position(this.right, this.down)

fun solve1(map: LocalGeology, slope: Slope): Int {
    println("About to traverse LocalGeology with ${map.size} GeoLines")
    var trees = 0
    map.traverse(Position(0,0), slope) { if (it == GeoThing.Tree) trees++ }
    return trees
}

class LocalGeology(private val _geoLines: List<GeoLine>): List<GeoLine> by _geoLines {
    fun traverse(start: Position, slope: Slope, geoThingCounter: (geoThing: GeoThing) -> Unit) {
        if (start.y + slope.down < _geoLines.size) {
            geoThingCounter(_geoLines[start.y + slope.down].moveRight(start.x + slope.right))
            traverse(start + slope.toPosition(), slope, geoThingCounter)
        }
    }
}


class GeoLine private constructor(private val _geoThings: List<GeoThing>): List<GeoThing> by _geoThings {
    fun moveRight(rightStep: RightStep): GeoThing {
        return if (_geoThings.size <= rightStep) {
            GeoLine(this._geoThings + this._geoThings).moveRight(rightStep)
        } else {
            this[rightStep]
        }
    }

    companion object {
        fun of(string: String) : GeoLine = GeoLine(string.map(GeoThing.Companion::of))
    }
}

sealed class GeoThing {
    object Tree: GeoThing()
    object Open: GeoThing()

    companion object {
        fun of(string: Char) : GeoThing = when(string) {
            '#' -> Tree
            '.' -> Open
            else -> throw RuntimeException("Unknown GeoThing representation: $string")
        }
    }
}