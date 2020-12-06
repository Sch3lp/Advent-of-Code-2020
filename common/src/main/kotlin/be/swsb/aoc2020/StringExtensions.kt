package be.swsb.aoc2020

fun List<String>.toStringGroups() = this.joinToString(separator = " ") { if (it.isEmpty()) "[newGroup]" else it }
    .split("[newGroup]")
    .map(String::trim)