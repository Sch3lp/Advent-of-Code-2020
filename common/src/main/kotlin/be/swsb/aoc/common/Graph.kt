package be.swsb.aoc.common

// https://www.geeksforgeeks.org/graph-implementation-using-stl-for-competitive-programming-set-2-weighted-graph/
// Weighted Graph
open class Graph<T, W>(private val _vector: HashMap<T,Pair<T, W?>?> = HashMap()): Map<T, Pair<T,W?>?> by _vector {

    fun addEdge(from: T, to: T? = null, weight: W? = null) = _vector.put(from, to?.let { it to weight })

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Graph<*, *>

        if (_vector != other._vector) return false

        return true
    }

    override fun hashCode(): Int {
        return _vector.hashCode()
    }
}
