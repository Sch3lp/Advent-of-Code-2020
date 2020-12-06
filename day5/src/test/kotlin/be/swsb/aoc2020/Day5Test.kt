package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import be.swsb.aoc.common.lowerHalf
import be.swsb.aoc.common.upperHalf
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

