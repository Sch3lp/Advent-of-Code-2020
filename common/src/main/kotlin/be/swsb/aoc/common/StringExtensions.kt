package be.swsb.aoc.common

fun String.removeBlanks() = this.replace(" ", "")
fun List<String>.filterNotEmpty() = this.filterNot { it.isEmpty() }