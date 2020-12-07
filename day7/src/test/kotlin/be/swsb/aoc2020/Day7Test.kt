package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day7Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val rules = Files.readLinesAs("input.txt") { Rule.fromString(it) }
            assertThat(solve(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 1`() {
            val rules = Files.readLinesAs("actualInput.txt") { Rule.fromString(it) }
            assertThat(solve(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val rules = Files.readLinesAs("input.txt") { Rule.fromString(it) }
            assertThat(solve2(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 2`() {
            val rules = Files.readLinesAs("actualInput.txt") { Rule.fromString(it) }
            assertThat(solve2(rules)).isEqualTo(4)
        }

    }
}

fun solve(rules: List<Rule>): Int = 0
fun solve2(rules: List<Rule>): Int = 0

data class Rule(private val _ruleString: String) {
    companion object {
        fun fromString(s: String): Rule = Rule(s)
    }
}