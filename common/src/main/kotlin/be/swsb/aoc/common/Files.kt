package be.swsb.aoc.common

object Files {
    fun readLines(fileName: String): List<String> = readLinesIncludingBlanks(fileName)
            .filterNot { it.isBlank() }

    fun readLinesIncludingBlanks(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)!!
            .bufferedReader()
            .readLines()

    inline fun <reified R> readLinesAs(fileName: String, lineConverter: (String) -> R) : List<R> =
        readLines(fileName).map(lineConverter)

    fun csvLines(fileName: String): List<String> = object{}.javaClass.classLoader.getResourceAsStream(fileName)!!
            .bufferedReader()
            .readLines()
            .filterNot { it.isBlank() }
            .flatMap { it.split(",") }
            .map { it.trim() }

    fun asCsvLines(strings: String): List<String> = strings
            .split(",")
            .map { it.trim() }

    inline fun <reified R> csvLinesAs(fileName: String, stringConverter: (String) -> R) : List<R> =
        csvLines(fileName).map(stringConverter)
}
