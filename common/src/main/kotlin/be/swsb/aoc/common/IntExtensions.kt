package be.swsb.aoc.common

infix fun Int.range(other: Int): Iterable<Int> {
    return if (this < other) {
        (this + 1)..other
    } else {
        (this - 1).downTo(other)
    }
}
