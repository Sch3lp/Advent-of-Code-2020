package be.swsb.aoc2020

import GeoLine
import LocalGeology
import Slope
import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import solve1
import solve2

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DayTest {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val geolines : List<GeoLine> = Files.readLinesAs("input.txt", String::toGeoLine)
            val localMap = LocalGeology(geolines)
            assertThat(solve1(localMap, Slope(1, 3))).isEqualTo(7)
        }

        @Test
        fun `solve exercise 1`() {
            val geolines : List<GeoLine> = Files.readLinesAs("actualInput.txt", String::toGeoLine)
            val localMap = LocalGeology(geolines)
            assertThat(solve1(localMap, Slope(1, 3))).isEqualTo(7)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val geolines : List<GeoLine> = Files.readLinesAs("input.txt", String::toGeoLine)
            val localMap = LocalGeology(geolines)
            val slopes = listOf(
                Slope(1, 1),
                Slope(1, 3),
                Slope(1, 5),
                Slope(1, 7),
                Slope(2, 1)
            )
            assertThat(solve2(localMap, slopes)).isEqualTo(336)
        }

        @Test
        fun `solve exercise 2`() {
            val geolines : List<GeoLine> = Files.readLinesAs("actualInput.txt", String::toGeoLine)
            val localMap = LocalGeology(geolines)
            val slopes = listOf(
                Slope(1, 1),
                Slope(1, 3),
                Slope(1, 5),
                Slope(1, 7),
                Slope(2, 1)
            )
            assertThat(solve2(localMap, slopes)).isEqualTo(336)
        }

    }
}

fun String.toGeoLine() = GeoLine.of(this)