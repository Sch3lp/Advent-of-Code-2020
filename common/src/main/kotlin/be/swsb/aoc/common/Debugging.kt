package be.swsb.aoc.common

private object Debugging {
    var debug = false
}

fun enableDebugging() {
    Debugging.debug = true
}

fun <T, R> T.debugln(block: (it: T) -> R) = if (Debugging.debug) {
    println(block(this))
    this
} else {
    this
}

fun <T> T.debug(block: (it: T) -> String) = if (Debugging.debug) {
    print(block(this))
    this
} else {
    this
}


fun disableDebugging() {
    Debugging.debug = false
}
