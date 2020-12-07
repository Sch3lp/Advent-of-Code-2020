package be.swsb.aoc.common

private object Debugging {
    var debug = false
}

fun enableDebugging() {
    Debugging.debug = true
}
fun disableDebugging() {
    Debugging.debug = false
}

fun <T, R> T.debug(block: (it: T) -> R) = if (Debugging.debug) {
    print(block(this))
    this
} else {
    this
}

fun <T, R> T.debugln(block: (it: T) -> R) = if (Debugging.debug) {
    println(block(this))
    this
} else {
    this
}