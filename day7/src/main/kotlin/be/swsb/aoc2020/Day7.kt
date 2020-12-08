package be.swsb.aoc2020

import be.swsb.aoc.common.WeightedGraph

class BagRules(stringList: List<String>) : WeightedGraph<Color, Amount>() {
    init {
        stringList.map(String::toBagRule)
            .associateBy { it.color }
            .mapValues { (_, value) -> value.containedBags }
            .forEach { (color, vertices) ->
                vertices?.forEach { v -> addEdge(color, v.first, v.second) } ?: addEdge(color)
            }
    }


    fun traverseDown(node: Color): List<Pair<Color, Amount>> {
        val edges = _nodes[node]

        return if (edges == null) {
            emptyList()
        } else {
            edges
                .fold(emptyList()) { acc, edge ->
                    val mutableAcc = acc.toMutableList()
                    mutableAcc.add(edge)
                    mutableAcc.addAll(traverseDown(edge.first).map { (n, w) -> n to edge.second.times(w) })
                    mutableAcc
                }
        }
    }
}

typealias Amount = Int
typealias Color = String


fun String.toBagRule(): BagRule = BagRule.fromString(this)
data class BagRule(
    val color: Color,
    val containedBags: List<Pair<Color, Amount>>?
) {

    companion object {
        fun fromString(s: String): BagRule {
            val (color, nestedBagsString) = s.split("bags contain")
            val nestedBags = if (nestedBagsString.trim().startsWith("no")) {
                null
            } else {
                nestedBagsString.split(", ").flatMap { nestedBagRule(it) }
            }
            return BagRule(color.trim(), nestedBags)
        }

        private fun nestedBagRule(nestedBagRuleString: String): List<Pair<Color, Amount>> {
            return nestedBagRuleString.split(",")
                .map { oneBagRuleString ->
                    val (_, amountOfBags, bagColor) = "(\\d+) (.+) bag".toRegex().findAll(oneBagRuleString).toList()
                        .flatMap { match -> match.groupValues }
                    bagColor to amountOfBags.toInt()
                }
        }
    }
}
