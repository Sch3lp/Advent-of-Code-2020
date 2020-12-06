package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day6Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val stuff = Files.readLinesIncludingBlanks("input.txt")

            assertThat(solve1(stuff)).isEqualTo(3 + 3 + 3 + 1 + 1).isEqualTo(11)
        }

        @Test
        fun `solve exercise 1`() {
            assertThat(true).isFalse()
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val stuff = Files.readLinesAs("input.txt", String::toInt)

            assertThat(true).isFalse()
        }

        @Test
        fun `solve exercise 2`() {
            val stuff = Files.readLinesAs("actualInput.txt", String::toInt)

            assertThat(true).isFalse()
        }

    }
}

fun solve1(answers: List<String>): Int {
    return 0
}