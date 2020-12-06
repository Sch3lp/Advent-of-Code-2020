package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.RuntimeException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day5Test {

    @Nested
    inner class Exercise1 {
        @Test
        fun `solve exercise 1 - test input`() {
            val stuff = Files.readLines("input.txt")
            assertThat(solve1(stuff)).isEqualTo(820)
        }

        @Test
        fun `solve exercise 1`() {
            val stuff = Files.readLines("actualInput.txt")
            assertThat(solve1(stuff)).isEqualTo(935)
        }

        @Test
        fun `solve exercise 2`() {
            val stuff = Files.readLines("actualInput.txt")
            assertThat(solve2(stuff)).isEqualTo(743)
        }
    }

    @Nested
    inner class SeatIdCalculator {
        @Test
        internal fun `findSeat tests`() {
            assertThat(findSeat("FBFBBFFRLR")).isEqualTo(Seat(row = 44, column = 5))
            assertThat(findSeat("BFFFBBFRRR")).isEqualTo(Seat(row = 70, column = 7))
            assertThat(findSeat("FFFBBBFRRR")).isEqualTo(Seat(row = 14, column = 7))
            assertThat(findSeat("BBFFBBFRLL")).isEqualTo(Seat(row = 102, column = 4))
        }

        @Test
        internal fun `seatId tests`() {
            assertThat(findSeat("FBFBBFFRLR").seatId).isEqualTo(357)
            assertThat(findSeat("BFFFBBFRRR").seatId).isEqualTo(567)
            assertThat(findSeat("FFFBBBFRRR").seatId).isEqualTo(119)
            assertThat(findSeat("BBFFBBFRLL").seatId).isEqualTo(820)
        }
    }
}

fun solve1(paths: List<String>): Int {
    val seatIds = paths.map { findSeat(it).seatId }
    return seatIds.max() ?: 0
}

fun solve2(paths: List<String>): Int {
    val seatIds = paths.map { findSeat(it).seatId }
    return findMissingSeat(seatIds)
}

private fun findMissingSeat(seatIds: List<Int>) =
    seatIds.sorted().zipWithNext().first { (a, b) -> (b - a) != 1 }.first + 1

data class PossibleSeat(val rows: IntRange, val columns: IntRange)

fun findSeat(boardingPass: String): Seat {
    val possibleSeat = boardingPass.fold(PossibleSeat(0..127, 0..7)) { (rows, columns), c ->
//        print("Applying: $c ")
        when (c) {
            'F' -> PossibleSeat(rows.lowerHalf(), columns)
            'B' -> PossibleSeat(rows.upperHalf(), columns)
            'L' -> PossibleSeat(rows, columns.lowerHalf())
            'R' -> PossibleSeat(rows, columns.upperHalf())
            else -> throw RuntimeException("Unknown instruction: $c")
        }
//            .also { println("means keeping rows ${it.rows} and columns ${it.columns}") }
    }
    return Seat(possibleSeat.rows.first, possibleSeat.columns.first)
}

data class Seat(val row: Int, val column: Int) {
    val seatId: Int
        get() = row * 8 + column
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

fun Int.divUp(other: Int) = this.toBigDecimal().div(other.toBigDecimal()).toInt()
