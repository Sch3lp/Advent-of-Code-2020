package be.swsb.aoc.common

object Debugging {
    var debug = false
}

fun enableDebugging() {
    Debugging.debug = true
}
fun disableDebugging() {
    Debugging.debug = false
}

fun <T> T.debug(block: (it: T) -> Unit) = if (Debugging.debug) {
    block(this)
    this
} else {
    this
}