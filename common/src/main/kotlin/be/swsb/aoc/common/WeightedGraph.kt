package be.swsb.aoc.common

// https://www.geeksforgeeks.org/graph-implementation-using-stl-for-competitive-programming-set-2-weighted-graph/
// Weighted Graph
open class WeightedGraph<NODE, WEIGHT>(private val _nodes: MutableMap<NODE, MutableList<Pair<NODE, WEIGHT?>>> = emptyMap<NODE, MutableList<Pair<NODE, WEIGHT?>>>().toMutableMap()) {

    fun addEdge(from: NODE, to: NODE? = null, weight: WEIGHT? = null) {
        val toBeAddedEdge = to?.let { listOf(it to weight) } ?: listOf()
        val edges = _nodes[from]
        if (edges == null) {
            _nodes[from] = toBeAddedEdge.toMutableList()
        } else {
            edges.addAll(toBeAddedEdge)
        }
    }

    fun findPath(node: NODE): Set<Pair<NODE, WEIGHT?>> {
        val nodesToGivenNode = findNodesToGivenNode(node)
        return nodesToGivenNode.flatMap { n ->
            _nodes[n]
                ?.filter { edge -> (node == edge.first) }
                ?.map { edge -> n to edge.second }
                ?: emptyList()
        }.toSet()
    }

    fun findNodesToGivenNode(node: NODE): Set<NODE> {
        fun findRecursive(acc: MutableSet<NODE>,
                          visitedNodes: MutableMap<NODE, Boolean>,
                          nodesToVisit: MutableSet<NODE>): Set<NODE> {
            if (nodesToVisit.isEmpty() || visitedNodes.all { (_,visited) -> visited }) return acc
            val nodeToFind = nodesToVisit.first()

            if (visitedNodes[nodeToFind] != null && visitedNodes[nodeToFind] == false) {
                val nodesContainingGivenNode = nodesContaining(nodeToFind).keys
                nodesToVisit.addAll(nodesContainingGivenNode)
                visitedNodes[nodeToFind] = true
            }

            nodesToVisit.remove(nodeToFind)
            acc.add(nodeToFind)
            return findRecursive(acc, visitedNodes, nodesToVisit)
        }
        val initiallyVisitedNodes = _nodes.keys.associateWith { it == node }.toMutableMap()
        val initiallyFoundNodes = nodesContaining(node).keys

        return findRecursive(initiallyFoundNodes.toMutableSet(), initiallyVisitedNodes, initiallyFoundNodes.toMutableSet())
    }

    private fun nodesContaining(nodeToFind: NODE) = _nodes.filterValues { n -> (nodeToFind in n.map { v -> v.first }) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeightedGraph<*, *>

        if (_nodes != other._nodes) return false

        return true
    }

    override fun hashCode(): Int {
        return _nodes.hashCode()
    }
}
