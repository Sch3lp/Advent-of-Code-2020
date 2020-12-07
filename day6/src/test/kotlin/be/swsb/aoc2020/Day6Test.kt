package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import be.swsb.aoc.common.debugln
import be.swsb.aoc.common.enableDebugging
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
            val stuff = Files.readLinesIncludingBlanks("actualInput.txt")
            assertThat(solve1(stuff)).isEqualTo(7110)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val stuff = Files.readLinesIncludingBlanks("input.txt")
            assertThat(solve2(stuff)).isEqualTo(3 + 0 + 1 + 1 + 1).isEqualTo(6)
        }

        @Test
        fun `solve exercise 2`() {
            val stuff = Files.readLinesIncludingBlanks("actualInput.txt")
            assertThat(solve2(stuff)).isLessThan(3963).isEqualTo(3628)
        }
    }

    @Nested
    inner class OneGroupAnswers {
        @Test
        internal fun findSameAnswers() {
            assertThat(findAmountOfSameAnswers(listOf("abc"))).isEqualTo(3)
            assertThat(findAmountOfSameAnswers(listOf("a","b","c"))).isEqualTo(0)
            assertThat(findAmountOfSameAnswers(listOf("ab","ac"))).isEqualTo(1)
            assertThat(findAmountOfSameAnswers(listOf("a","a","a","a"))).isEqualTo(1)
            assertThat(findAmountOfSameAnswers(listOf("b"))).isEqualTo(1)
            assertThat(findAmountOfSameAnswers(listOf("ab","ac","bd"))).isEqualTo(0) // last persons answer did not answer yes to a
            assertThat(findAmountOfSameAnswers(listOf("ab","bac","adb"))).isEqualTo(2) // both a and b were questions that people in this group answered yes to
        }
    }
}

fun solve1(answers: List<String>): Int {
    return answers
        .toStringGroups()
        .debugln { "groups: $it" }
        .map { group -> group.removeBlanks().debugln { "blanksRemoved $it" }.toSet().size }
        .debugln { it }
        .sum()
}

fun solve2(answers: List<String>): Int {
    val answersPerGroup: List<Pair<Int, List<String>>> = answers
        .toStringGroups()
        .debugln { "groups: $it" }
        .mapIndexed { idx, group ->
            val personalAnswers = group.split(" ")
            idx to personalAnswers
        }
        .debugln { "PersonalAnswers per group $it" }
    return answersPerGroup
        .map { (idx, oneGroupsAnswers) ->
            findAmountOfSameAnswers(oneGroupsAnswers)
                .debugln { "same answers per person in the group $idx: $it" }
        }
        .sum()
}

private fun findAmountOfSameAnswers(oneGroupsAnswers: List<String>) : Int {
    return if (oneGroupsAnswers.size == 1) {
        oneGroupsAnswers.flatMap { it.split("") }.filterNotEmpty().size
    } else {
        val answersPerPerson = oneGroupsAnswers
            .flatMap { it.split("") }
            .filterNotEmpty()
        return answersPerPerson.groupBy { it }
            .debugln { "answersPerPerson grouped: $it" }
            .map { (_, values) -> values}
            .sumBy { values -> if (values.size == oneGroupsAnswers.size) 1 else 0 }
    }
}


fun String.removeBlanks() = this.replace(" ", "")
fun List<String>.filterNotEmpty() = this.filterNot { it.isEmpty() }