package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day1Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val expenses = Files.readLinesAs("input.txt", String::toInt)

            assertThat(solve1(expenses)).isEqualTo(514579)
        }

        @Test
        fun `solve exercise 1`() {
            val expenses = Files.readLinesAs("actualInput.txt", String::toInt)

            assertThat(solve1(expenses)).isEqualTo(514579)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val expenses = Files.readLinesAs("actualInput.txt", String::toInt)

            assertThat(solve2(expenses)).isEqualTo(241861950)
        }
    }

    private fun solve1(expenses: List<Int>): Int {
        expenses.forEach {
            val complement = 2020 - it
            if (expenses.contains(complement)) {
                return@solve1 complement * it
            }
        }
        return 0
    }

    private fun solve2(expenses: List<Int>): Int {
        val possibleComplements = expenses.filter { it < 2020 }
        possibleComplements.forEach { a ->
            possibleComplements.forEach { b ->
                val complement = 2020 - a - b
                if (a != b && possibleComplements.contains(complement)) {
                    return@solve2 complement * a * b
                }
            }
        }
        return 0
    }

}