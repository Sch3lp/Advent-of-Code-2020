package be.swsb.aoc2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day1Test {

    @Nested
    inner class Exercise1 {
        @Test
        fun `solve exercise 1`() {
            assertThat(true).isFalse()
        }
    }
}