package be.swsb.aoc2020

import be.swsb.aoc.common.lowerHalf
import be.swsb.aoc.common.upperHalf
import java.lang.RuntimeException

fun findMissingSeat(seatIds: List<Int>) =
    seatIds.sorted().zipWithNext().first { (a, b) -> (b - a) != 1 }.first + 1


fun findSeat(boardingPass: String): Seat {
    data class PossibleSeat(val rows: IntRange, val columns: IntRange)
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
