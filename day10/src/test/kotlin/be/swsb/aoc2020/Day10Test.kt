package be.swsb.aoc2020

import be.swsb.aoc.common.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day10Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val joltages = Files.readLinesAs("input.txt", String::toInt)
            enableDebugging()
            assertThat(solve1(joltages)).isEqualTo(JoltageDifferences(22, null, 10))
            assertThat(solve1(joltages).multiplied).isEqualTo(220)
        }

        @Test
        fun `solve exercise 1`() {
            val joltages = Files.readLinesAs("actualInput.txt", String::toInt)

            assertThat(solve1(joltages)).isEqualTo(JoltageDifferences(70, null, 33))
            assertThat(solve1(joltages).multiplied).isEqualTo(2310)
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

fun solve1(joltages: List<Int>): JoltageDifferences {
    val initialJoltage = 0
    val bagAdapterJoltage = joltages.max()?.plus(3) ?: 3

    val sortedJoltages = joltages.let {
        val asMutabledList = it.toMutableList()
        asMutabledList.add(initialJoltage)
        asMutabledList.add(bagAdapterJoltage)
        asMutabledList
    }.sorted().debugln { "sortedJoltages: $it" }

    return foldJoltages(JoltageDifferences(), sortedJoltages)
}

fun solve2(joltages: List<Int>): JoltageDifferences = TODO("lol no thanks")

fun foldJoltages(differences: JoltageDifferences, remainingSortedJoltages: List<Int>) : JoltageDifferences {
    val firstJoltage = remainingSortedJoltages.first()
    val nextJoltage = remainingSortedJoltages.firstOrNull { joltage -> joltage in ((firstJoltage+1)..(firstJoltage+3)).debugln { "Checking joltage [$joltage] is in [$it]" } }
    return if (nextJoltage == null) differences
    else {
        val diff = nextJoltage - firstJoltage
        foldJoltages(differences.add(diff.debugln { "found diff: $it" }), remainingSortedJoltages.dropWhile { it != nextJoltage })
    }
}

data class JoltageDifferences(
    private val diffOf1: JoltageDiff? = null,
    private val diffOf2: JoltageDiff? = null,
    private val diffOf3: JoltageDiff? = null
) {
    val multiplied: Int?
        get() = diffOf1 * diffOf2 * diffOf3

    fun add(joltageDiff: JoltageDiff) : JoltageDifferences {
        return when(joltageDiff) {
            1 -> this.copy(diffOf1 = 1 + (this.diffOf1 ?: 0))
            2 -> this.copy(diffOf2 = 1 + (this.diffOf2 ?: 0))
            3 -> this.copy(diffOf3 = 1 + (this.diffOf3 ?: 0))
            else -> throw UnsupportedOperationException("We're not keeping Joltage Differences of $joltageDiff")
        }
    }
}

typealias JoltageDiff = Int




