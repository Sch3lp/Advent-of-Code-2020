package be.swsb.aoc.common

infix fun Int.range(other: Int): Iterable<Int> {
    return if (this < other) {
        (this + 1)..other
    } else {
        (this - 1).downTo(other)
    }
}

fun IntRange.lowerHalf(): IntRange =
    if (this.first == this.last) {
        this
    } else {
        val half = (this.last - this.first).div(2)
        this.first .. (this.first + half)
    }
fun IntRange.upperHalf(): IntRange =
    if (this.first == this.last) {
        this
    } else {
        val half = (this.last - this.first).div(2)
        (this.last - half) .. this.last
    }