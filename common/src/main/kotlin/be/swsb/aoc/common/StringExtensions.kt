package be.swsb.aoc.common

fun String.removeBlanks() = this.replace(" ", "")
fun List<String>.filterNotEmpty() = this.filterNot { it.isEmpty() }
fun List<String>.toStringGroups() = this.joinToString(separator = " ") { if (it.isEmpty()) "[newGroup]" else it }
    .split("[newGroup]")
    .map(String::trim)