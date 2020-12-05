package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day2Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val passwordCombos = Files.readLinesAs("input.txt", PatternPasswordCombo.Companion::toPatternPasswordCombo)

            assertThat(solve1(passwordCombos)).isEqualTo(2)
        }

        private fun solve1(passwordCombos: List<PatternPasswordCombo>): Int {
            return passwordCombos.count(PatternPasswordCombo::passwordMatches)
        }

        @Test
        fun `solve exercise 1`() {
            val passwordCombos = Files.readLinesAs("actualInput.txt", PatternPasswordCombo.Companion::toPatternPasswordCombo)

            assertThat(solve1(passwordCombos)).isEqualTo(4)
        }
    }

    @Nested
    inner class PatternPasswordComboTest {
        @Test
        internal fun passwordMatches() {
            assertThat(PatternPasswordCombo.toPatternPasswordCombo("1-3 a: abcde").passwordMatches()).isTrue()
            assertThat(PatternPasswordCombo.toPatternPasswordCombo("1-3 b: cdefg").passwordMatches()).isFalse()
            assertThat(PatternPasswordCombo.toPatternPasswordCombo("2-9 c: ccccccccc").passwordMatches()).isTrue()
        }
    }

    @Nested
    inner class IntRangeTest {
        @Test
        internal fun toIntRange() {
            assertThat("1-3".toIntRange()).isEqualTo(IntRange(1,3))
            assertThat("2-9".toIntRange()).isEqualTo(IntRange(2,9))
        }
    }
}

typealias Password = String
data class PatternPasswordCombo(private val pattern: String, private val password: Password) {
    fun passwordMatches(): Boolean {
        val split = pattern.split(" ")
        val range = split[0]
        val letter = split[1]
        return password.count { "$it" == letter } in range.toIntRange()
    }

    companion object {
        fun toPatternPasswordCombo(inputString: String): PatternPasswordCombo {
            val split = inputString.split(": ")
            return PatternPasswordCombo(split[0], split[1])
        }
    }
}

fun String.toIntRange(): IntRange {
    val a = this.split("-")[0].toInt()
    val b = this.split("-")[1].toInt()
    return IntRange(a,b)
}