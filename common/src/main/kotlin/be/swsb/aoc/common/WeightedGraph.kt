package be.swsb.aoc.common

// https://www.geeksforgeeks.org/graph-implementation-using-stl-for-competitive-programming-set-2-weighted-graph/
// Weighted Graph
open class WeightedGraph<T, W>(private val _vectors: HashMap<T, ArrayList<Pair<T, W?>?>> = HashMap()) {

    fun addEdge(from: T, to: T? = null, weight: W? = null) {
        val toBeAddedEdge = to?.let { listOf(it to weight) } ?: listOf()
        if (_vectors[from] == null) {
            _vectors[from] = ArrayList(toBeAddedEdge)
        } else {
            _vectors[from]?.addAll(toBeAddedEdge)
        }
    }

    fun findPath(node: T): Set<Pair<T, W?>> {
        val vectorsContainingNode = _vectors
            .debugln { "all the vectors: $it" }
            .filterValues { vector -> (node in vector.map { v -> v?.first }) }
            .debugln { "just the vectors that have an edge with \"$node\": $it" }
        return vectorsContainingNode
            .flatMap { (key, vectors) ->
                vectors
                    .filter { vector -> (node == vector?.first) }
                    .map { vector -> key to vector?.second }
            }
            .toSet()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeightedGraph<*, *>

        if (_vectors != other._vectors) return false

        return true
    }

    override fun hashCode(): Int {
        return _vectors.hashCode()
    }
}
