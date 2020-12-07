package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import be.swsb.aoc.common.debug
import be.swsb.aoc.common.debugln
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
                .isEqualTo(BagRule("faded blue", emptyList()))
            assertThat("light red bags contain 1 bright white bag, 2 muted yellow bags.".toBagRule())
                .isEqualTo(
                    BagRule(
                        "light red",
                        listOf(
                            BagRule("bright white", emptyList()),
                            BagRule("muted yellow", emptyList()),
                            BagRule("muted yellow", emptyList())
                        )
                    )
                )
        }

        @Test
        internal fun `parses to list of nestedBagRule`() {
            enableDebugging()
            assertThat(BagRule.nestedBagRule("1 bright white bag, 2 muted yellow bags."))
                .isEqualTo(
                    listOf(
                        BagRule("bright white", emptyList()),
                        BagRule("muted yellow", emptyList()),
                        BagRule("muted yellow", emptyList())
                    )
                )
        }
    }
}

fun solve(bagRules: List<BagRule>): Int = 0
fun solve2(bagRules: List<BagRule>): Int = 0

data class BagRule(val color: String, val nestedRules: List<BagRule>) {
    companion object {
        fun fromString(s: String): BagRule {
            val (name, nestedBagsString) = s.split("bags contain")
            val nestedBags = if (nestedBagsString.trim().startsWith("no")) {
                emptyList()
            } else {
                nestedBagsString.split(", ").flatMap { nestedBagRule(it) }
            }
            return BagRule(name.trim(), nestedBags)
        }

        fun nestedBagRule(nestedBagRuleString: String): List<BagRule> {
            return nestedBagRuleString.split(",")
                .flatMap { oneBagRuleString ->
                    val (_, amountOfBags, bagName) = "(\\d+) (.+) bag".toRegex().findAll(oneBagRuleString).toList()
                        .flatMap { match -> match.groupValues }
                    (1..amountOfBags.toInt()).map { BagRule(bagName, emptyList()) }
                }
        }
    }
}

fun String.toBagRule(): BagRule = BagRule.fromString(this)