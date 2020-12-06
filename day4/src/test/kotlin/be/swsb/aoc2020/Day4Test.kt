package be.swsb.aoc2020

import be.swsb.aoc.common.Files
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.RuntimeException
import java.lang.StringBuilder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Day4Test {

    @Nested
    inner class Exercise1 {

        @Test
        fun `solve exercise 1 - test input`() {
            val stuff = Files.readLinesIncludingBlanks("input.txt")

            assertThat(solve1(stuff)).isEqualTo(2)
        }

        @Test
        fun `solve exercise 1`() {
            val stuff = Files.readLinesIncludingBlanks("actualInput.txt")

            assertThat(solve1(stuff)).isEqualTo(2)
        }

        @Test
        fun `solve exercise 2 - test input`() {
            val stuff = Files.readLinesAs("input.txt", String::toInt)

            assertThat(true).isFalse()
        }

        @Test
        fun `solve exercise 2`() {
            val stuff = Files.readLinesAs("actualInput.txt", String::toInt)

            assertThat(true).isFalse()
        }
    }

    @Nested
    inner class ParsingPassports {
        @Test
        internal fun `toPassportStrings creates one single passport string line to make it easier to parse the whole string into a Passport`() {
            val lines = """
ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929
            """.trimIndent().lines().toPassportStrings()

            assertThat(lines).isEqualTo(
                listOf(
                    "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm",
                    "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929"
                )
            )
        }
        @Test
        internal fun `toPassportStrings from inputFile`() {
            val lines = Files.readLinesIncludingBlanks("input.txt").toPassportStrings()

            assertThat(lines).isEqualTo(
                listOf(
"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm",
"iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929",
"hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm",
"hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in"
                )
            )
        }

        @Test
        internal fun `blank line starts new passport`() {
            val twoPassports: List<Passport> = """
ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929
            """.trimIndent().lines().toPassports()

            assertThat(twoPassports).hasSize(2)
        }

        @Test
        internal fun `parsing one line to a Passport`() {
            assertThat(Passport.fromString("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm"))
                .isEqualTo(Passport(eyeColor = "gry", passportID = "860033327", expirationYear = "2020", hairColor = "#fffffd", birthYear = "1937", issueYear = "2017", countryID = "147", height = "183cm"))
            assertThat(Passport.fromString("iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929"))
                .isEqualTo(Passport(issueYear = "2013", eyeColor = "amb", countryID = "350", expirationYear = "2023", passportID = "028048884", hairColor = "#cfa07d", birthYear = "1929"))
        }
    }

    @Nested
    inner class ValidatingPassports {
        @Test
        internal fun `all fields except country Id are optional`() {
            assertThat(Passport.fromString("pid:860033327 iyr:2017 eyr:2020 byr:1937 ecl:gry hcl:#fffffd hgt:183cm cid:147").isValid()).isTrue()
            assertThat(Passport.fromString("pid:860033327 iyr:2017 eyr:2020 byr:1937 ecl:gry hcl:#fffffd hgt:183cm").isValid()).isTrue()
            assertThat(Passport.fromString("pid:860033327 iyr:2017 eyr:2020 byr:1937 ecl:gry hcl:#fffffd hgt:183cm cid:").isValid()).isTrue()
            assertThat(Passport.fromString("pid:028048884 iyr:2013 eyr:2023 byr:1929 ecl:amb hcl:#cfa07d cid:350").isValid()).isFalse()
        }
    }
}

fun solve1(stuff: List<String>): Int = stuff.toPassports().count { passport ->
    passport.also { print("Passport $it is ") }
        .isValid().also { isValid -> println(if (isValid) "valid" else "invalid") }
}

fun List<String>.toPassportStrings() = this.joinToString(separator = " ") { if (it.isEmpty()) "[newpassport]" else it }
    .split("[newpassport]")
    .map(String::trim)

fun List<String>.toPassports() = this.toPassportStrings().map(Passport.Companion::fromString)

data class Passport(
    private val passportID: String? = null, //pid
    private val issueYear: String? = null, //iyr
    private val expirationYear: String? = null, //eyr
    private val birthYear: String? = null, //byr
    private val eyeColor: String? = null, //ecl
    private val hairColor: String? = null, //hcl
    private val height: String? = null, //hgt
    private val countryID: String? = null, //cid
) {
    companion object {
        fun fromString(inputString: String): Passport {
            return inputString.split(" ").fold(Passport()) { passportBuilder, part ->
                val (key, value) = part.split(":")
                when(key) {
                    "pid" -> passportBuilder.copy(passportID = value)
                    "iyr" -> passportBuilder.copy(issueYear = value)
                    "eyr" -> passportBuilder.copy(expirationYear = value)
                    "byr" -> passportBuilder.copy(birthYear = value)
                    "ecl" -> passportBuilder.copy(eyeColor = value)
                    "hcl" -> passportBuilder.copy(hairColor = value)
                    "hgt" -> passportBuilder.copy(height = value)
                    "cid" -> passportBuilder.copy(countryID = value)
                    else -> throw RuntimeException("Unknown property: $key")
                }
            }
        }
    }

    fun isValid() = listOf(passportID, issueYear, expirationYear, birthYear, eyeColor, hairColor, height).areFilledIn()
}

fun <T> List<T?>.filterNotNull() = filterNot { it == null }
fun <T> List<T?>.areFilledIn() = this.filterNotNull().size == this.size
