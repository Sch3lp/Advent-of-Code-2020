package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import be.swsb.aoc.common.Graph
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
            val rules = Files.readLinesAs("input.txt", String::toBagRule)
            assertThat(solve(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 1`() {
            val rules = Files.readLinesAs("actualInput.txt", String::toBagRule)
            assertThat(solve(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val rules = Files.readLinesAs("input.txt", String::toBagRule)
            assertThat(solve2(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 2`() {
            val rules = Files.readLinesAs("actualInput.txt", String::toBagRule)
            assertThat(solve2(rules)).isEqualTo(4)
        }
    }

    @Nested
    inner class BagRuleParsing {
        @Test
        internal fun `parses to lists of BagRules`() {
            assertThat("faded blue bags contain no other bags.".toBagRule())
                .isEqualTo(BagRule("faded blue", null))
            assertThat("light red bags contain 1 bright white bag, 2 muted yellow bags.".toBagRule())
                .isEqualTo(BagRule("light red", listOf("bright white" to 1, "muted yellow" to 2)))
        }

        @Test
        internal fun `parses to a graph of BagRules`() {
            // https://www.geeksforgeeks.org/graph-implementation-using-stl-for-competitive-programming-set-2-weighted-graph/
            val actual = BagRules(listOf(
                "light red bags contain 1 bright white bag, 2 muted yellow bags.",
                "bright white bags contain 1 bold blue bag.",
                "muted yellow bags contain no bags.",
                "bold blue bags contain no bags."
            ))
            val bagRules = BagRules(emptyList())
            bagRules.addEdge("light red", "bright white", 1)
            bagRules.addEdge("light red", "muted yellow", 2)
            bagRules.addEdge("bright white", "bold blue", 1)
            bagRules.addEdge("muted yellow")
            bagRules.addEdge("bold blue")

            assertThat(actual).isEqualTo(bagRules)
        }
    }

}

fun solve(bagRules: List<BagRule>): Int = 0
fun solve2(bagRules: List<BagRule>): Int = 0


