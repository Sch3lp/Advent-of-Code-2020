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
            val rules = Files.readLines("input.txt")
            assertThat(solve(rules)).isEqualTo(4)
        }

        @Test
        fun `solve exercise 1`() {
            val rules = Files.readLines("actualInput.txt")
            assertThat(solve(rules)).isEqualTo(287)
        }

        @Test
        internal fun `solve exercise 2 - second test input`() {
            val rules = listOf(
                "shiny gold bags contain 2 dark red bags.",
                "dark red bags contain 2 dark orange bags.",
                "dark orange bags contain 2 dark yellow bags.",
                "dark yellow bags contain 2 dark green bags.",
                "dark green bags contain 2 dark blue bags.",
                "dark blue bags contain 2 dark violet bags.",
                "dark violet bags contain no other bags.",
            )
            assertThat(solve2(rules)).isEqualTo(126)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val rules = Files.readLines("input.txt")
            assertThat(solve2(rules)).isEqualTo(32)
        }

        @Test
        fun `solve exercise 2`() {
            val rules = Files.readLines("actualInput.txt")
            assertThat(solve2(rules)).isEqualTo(48160)
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
            val actual = BagRules(
                listOf(
                    "light red bags contain 1 bright white bag, 2 muted yellow bags, 5 orange bags.",
                    "bright white bags contain 1 bold blue bag.",
                    "muted yellow bags contain no bags.",
                    "bold blue bags contain no bags.",
                    "orange bags contain no bags."
                )
            )
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
        internal fun `find nodes to given node`() {
            val bagRules = BagRules(
                listOf(
                    "light red bags contain 1 bright white bag, 2 muted yellow bags, 5 orange bags.",
                    "bright white bags contain 1 bold blue bag, 3 orange bags.",
                    "muted yellow bags contain no bags.",
                    "bold blue bags contain no bags.",
                    "orange bags contain no bags."
                )
            )
            assertThat(bagRules.findNodesToGivenNode("light red")).isEmpty()
            assertThat(bagRules.findNodesToGivenNode("bright white")).containsExactly("light red")
            assertThat(bagRules.findNodesToGivenNode("muted yellow")).containsExactly("light red")
            assertThat(bagRules.findNodesToGivenNode("bold blue")).containsExactly("bright white", "light red")
            assertThat(bagRules.findNodesToGivenNode("orange")).containsExactly("light red", "bright white")
        }

        @Test
        internal fun `traverse down from given node`() {
            val bagRules = BagRules(
                listOf(
                    "shiny gold bags contain 2 dark red bags.",
                    "dark red bags contain 2 dark orange bags.",
                    "dark orange bags contain 2 dark yellow bags.",
                    "dark yellow bags contain 2 dark green bags.",
                    "dark green bags contain 2 dark blue bags.",
                    "dark blue bags contain 2 dark violet bags.",
                    "dark violet bags contain no other bags.",
                )
            )
            assertThat(bagRules.traverseDown("dark violet")).isEmpty()
            assertThat(bagRules.traverseDown("dark blue")).containsExactly("dark violet" to 2)
            assertThat(bagRules.traverseDown("dark green")).containsExactly("dark blue" to 2, "dark violet" to 4)
            assertThat(bagRules.traverseDown("dark yellow")).containsExactly("dark green" to 2, "dark blue" to 4, "dark violet" to 8)
            assertThat(bagRules.traverseDown("dark orange")).containsExactly("dark yellow" to 2, "dark green" to 4, "dark blue" to 8, "dark violet" to 16)
            assertThat(bagRules.traverseDown("dark red")).containsExactly("dark orange" to 2, "dark yellow" to 4, "dark green" to 8, "dark blue" to 16, "dark violet" to 32)
            assertThat(bagRules.traverseDown("shiny gold")).containsExactly("dark red" to 2, "dark orange" to 4, "dark yellow" to 8, "dark green" to 16, "dark blue" to 32, "dark violet" to 64)
        }
    }
}

fun solve(bagRules: List<String>): Int = BagRules(bagRules).findNodesToGivenNode("shiny gold").size
fun solve2(bagRules: List<String>): Int = BagRules(bagRules).traverseDown("shiny gold").sumBy(Pair<Color,Amount>::second)


