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

            assertThat(solve1(stuff)).isEqualTo(260)
        }

        @Test
        fun `solve exercise 2`() {
            val stuff = Files.readLinesIncludingBlanks("actualInput.txt")

            assertThat(solve2(stuff)).isEqualTo(153)
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
                .isEqualTo(Passport(_eyeColor = "gry", _passportID = "860033327", _expirationYear = "2020", _hairColor = "#fffffd", _birthYear = "1937", _issueYear = "2017", _countryID = "147", _height = "183cm"))
            assertThat(Passport.fromString("iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929"))
                .isEqualTo(Passport(_issueYear = "2013", _eyeColor = "amb", _countryID = "350", _expirationYear = "2023", _passportID = "028048884", _hairColor = "#cfa07d", _birthYear = "1929"))
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

        @Test
        internal fun `Passport ID Validity`() {
            assertThat(PassportId("012345678").isValid()).isTrue()

            assertThat(PassportId(null).isValid()).isFalse()
            assertThat(PassportId("0123456789").isValid()).isFalse()
        }

        @Test
        internal fun `IssueYear Validity`() {
            assertThat(IssueYear("2010").isValid()).isTrue()
            assertThat(IssueYear("2020").isValid()).isTrue()

            assertThat(IssueYear(null).isValid()).isFalse()
            assertThat(IssueYear("2021").isValid()).isFalse()
            assertThat(IssueYear("2009").isValid()).isFalse()
            assertThat(IssueYear("201").isValid()).isFalse()
            assertThat(IssueYear("ABCD").isValid()).isFalse()
        }

        @Test
        internal fun `ExpirationYear Validity`() {
            assertThat(ExpirationYear("2020").isValid()).isTrue()
            assertThat(ExpirationYear("2030").isValid()).isTrue()

            assertThat(ExpirationYear(null).isValid()).isFalse()
            assertThat(ExpirationYear("2019").isValid()).isFalse()
            assertThat(ExpirationYear("2031").isValid()).isFalse()
            assertThat(ExpirationYear("201").isValid()).isFalse()
            assertThat(ExpirationYear("ABCD").isValid()).isFalse()
        }

        @Test
        internal fun `BirthYear Validity`() {
            assertThat(BirthYear("1920").isValid()).isTrue()
            assertThat(BirthYear("2002").isValid()).isTrue()

            assertThat(BirthYear(null).isValid()).isFalse()
            assertThat(BirthYear("1919").isValid()).isFalse()
            assertThat(BirthYear("2003").isValid()).isFalse()
            assertThat(BirthYear("201").isValid()).isFalse()
            assertThat(BirthYear("ABCD").isValid()).isFalse()
        }

        @Test
        internal fun `EyeColor Validity`() {
            assertThat(EyeColor("amb").isValid()).isTrue()
            assertThat(EyeColor("blu").isValid()).isTrue()
            assertThat(EyeColor("brn").isValid()).isTrue()
            assertThat(EyeColor("gry").isValid()).isTrue()
            assertThat(EyeColor("grn").isValid()).isTrue()
            assertThat(EyeColor("hzl").isValid()).isTrue()
            assertThat(EyeColor("oth").isValid()).isTrue()

            assertThat(EyeColor(null).isValid()).isFalse()
            assertThat(EyeColor("AMB").isValid()).isFalse()
            assertThat(EyeColor("snarf").isValid()).isFalse()
            assertThat(EyeColor("sna").isValid()).isFalse()
            assertThat(EyeColor("").isValid()).isFalse()
        }

        @Test
        internal fun `HairColor Validity`() {
            assertThat(HairColor("#123abc").isValid()).isTrue()

            assertThat(HairColor(null).isValid()).isFalse()
            assertThat(HairColor("#123abz").isValid()).isFalse()
            assertThat(HairColor("!123abc").isValid()).isFalse()
            assertThat(HairColor("123abc").isValid()).isFalse()
        }

        @Test
        internal fun `Height Validity`() {
            assertThat(Height.of("150cm").isValid()).isTrue()
            assertThat(Height.of("193cm").isValid()).isTrue()
            assertThat(Height.of("59in").isValid()).isTrue()
            assertThat(Height.of("76in").isValid()).isTrue()

            assertThat(Height.of(null).isValid()).isFalse()
            assertThat(Height.of("149cm").isValid()).isFalse()
            assertThat(Height.of("194cm").isValid()).isFalse()
            assertThat(Height.of("58in").isValid()).isFalse()
            assertThat(Height.of("77in").isValid()).isFalse()
            assertThat(Height.of("77").isValid()).isFalse()
            assertThat(Height.of("abin").isValid()).isFalse()
            assertThat(Height.of("abcm").isValid()).isFalse()
            assertThat(Height.of("cm").isValid()).isFalse()
            assertThat(Height.of("in").isValid()).isFalse()
        }
    }
}

fun solve1(stuff: List<String>): Int = stuff.toPassports().count { passport ->
    passport.also { print("Passport $it is ") }
        .isValid().also { isValid -> println(if (isValid) "valid" else "invalid") }
}

fun solve2(stuff: List<String>): Int = stuff.toValidatablePassports().count { passport ->
    passport.isValid()
}
