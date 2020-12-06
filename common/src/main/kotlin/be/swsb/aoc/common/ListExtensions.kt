package be.swsb.aoc.common

fun <T> List<T?>.filterNotNull() = filterNot { it == null }
