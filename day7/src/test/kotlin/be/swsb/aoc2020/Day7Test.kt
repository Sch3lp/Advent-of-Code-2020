package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import be.swsb.aoc.common.enableDebugging
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
                "light red bags contain 1 bright white bag, 2 muted yellow bags, 5 orange bags.",
                "bright white bags contain 1 bold blue bag.",
                "muted yellow bags contain no bags.",
                "bold blue bags contain no bags.",
                "orange bags contain no bags."
            ))
            val bagRules = BagRules(emptyList())
            bagRules.addEdge("light red", "bright white", 1)
            bagRules.addEdge("light red", "muted yellow", 2)
            bagRules.addEdge("light red", "orange", 5)
            bagRules.addEdge("bright white", "bold blue", 1)
            bagRules.addEdge("muted yellow")
            bagRules.addEdge("bold blue")
            bagRules.addEdge("orange")

            assertThat(actual).isEqualTo(bagRules)
        }
    }

    @Nested
    inner class GraphFind {
        @Test
        internal fun `find path to a certain node`() {
            val bagRules = BagRules(listOf(
                "light red bags contain 1 bright white bag, 2 muted yellow bags, 5 orange bags.",
                "bright white bags contain 1 bold blue bag, 3 orange bags.",
                "muted yellow bags contain no bags.",
                "bold blue bags contain no bags.",
                "orange bags contain no bags."
            ))
            assertThat(bagRules.findPath("light red")).isEmpty()
            enableDebugging()
            assertThat(bagRules.findPath("bright white")).containsExactly("light red" to 1)
            assertThat(bagRules.findPath("muted yellow")).containsExactly("light red" to 2)
            assertThat(bagRules.findPath("bold blue")).containsExactly("bright white" to 1, "light red" to 1)
            assertThat(bagRules.findPath("orange")).containsExactly("light red" to 5, "bright white" to 3, "light red" to 1)
        }

//        @Test
//        internal fun `find paths to a certain node`() {
//            val bagRules = BagRules(listOf(
//                "light red bags contain 1 bright white bag, 2 muted yellow bags, 5 orange bags.",
//                "bright white bags contain 1 bold blue bag, 3 orange bags.",
//                "muted yellow bags contain no bags.",
//                "bold blue bags contain no bags.",
//                "orange bags contain no bags."
//            ))
//            assertThat(bagRules.findPaths("light red")).isEmpty()
//            assertThat(bagRules.findPaths("bright white")).containsExactly(listOf("light red" to 1))
//            assertThat(bagRules.findPaths("muted yellow")).containsExactly(listOf("light red" to 2))
//            assertThat(bagRules.findPaths("bold blue")).containsExactly(listOf("bright white" to 1), listOf("light red" to 1, "bright white" to 1))
//            assertThat(bagRules.findPaths("orange")).containsExactly(listOf("light red" to 5), listOf("bright white" to 3), listOf("light red" to 1, "bright white" to 3))
//        }
    }

}

fun solve(bagRules: List<BagRule>): Int = 0
fun solve2(bagRules: List<BagRule>): Int = 0


