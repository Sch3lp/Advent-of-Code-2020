package be.swsb.aoc2020

import be.swsb.aoc.common.filterNotNull
import java.lang.RuntimeException


fun List<String>.toPassports() = this.toStringGroups().map(Passport.Companion::fromString)
fun List<String>.toValidatablePassports() = this.toStringGroups().map(ValidatablePassport.Companion::fromString)

data class Passport(
    private val _passportID: String? = null, //pid
    private val _issueYear: String? = null, //iyr
    private val _expirationYear: String? = null, //eyr
    private val _birthYear: String? = null, //byr
    private val _eyeColor: String? = null, //ecl
    private val _hairColor: String? = null, //hcl
    private val _height: String? = null, //hgt
    private val _countryID: String? = null //cid
) {

    companion object {
        fun fromString(inputString: String): Passport {
            return inputString.split(" ").fold(Passport()) { passportBuilder, part ->
                val (key, value) = part.split(":")
                when(key) {
                    "pid" -> passportBuilder.copy(_passportID = value)
                    "iyr" -> passportBuilder.copy(_issueYear = value)
                    "eyr" -> passportBuilder.copy(_expirationYear = value)
                    "byr" -> passportBuilder.copy(_birthYear = value)
                    "ecl" -> passportBuilder.copy(_eyeColor = value)
                    "hcl" -> passportBuilder.copy(_hairColor = value)
                    "hgt" -> passportBuilder.copy(_height = value)
                    "cid" -> passportBuilder.copy(_countryID = value)
                    else -> throw RuntimeException("Unknown property: $key")
                }
            }
        }
    }

    fun isValid() = listOf(_passportID, _issueYear, _expirationYear, _birthYear, _eyeColor, _hairColor, _height).areFilledIn()
}

// solve 2
data class ValidatablePassport(
    private val _passportID: String? = null, //pid
    private val _issueYear: String? = null, //iyr
    private val _expirationYear: String? = null, //eyr
    private val _birthYear: String? = null, //byr
    private val _eyeColor: String? = null, //ecl
    private val _hairColor: String? = null, //hcl
    private val _height: String? = null, //hgt
    private val _countryID: String? = null //cid
) {
    private val passportID = PassportId(_passportID)
    private val issueYear = IssueYear(_issueYear)
    private val expirationYear = ExpirationYear(_expirationYear)
    private val birthYear = BirthYear(_birthYear)
    private val eyeColor = EyeColor(_eyeColor)
    private val hairColor = HairColor(_hairColor)
    private val height = Height.of(_height)
    private val countryID: String? = _countryID

    companion object {
        fun fromString(inputString: String): ValidatablePassport {
            return inputString.split(" ").fold(ValidatablePassport()) { passportBuilder, part ->
                val (key, value) = part.split(":")
                when(key) {
                    "pid" -> passportBuilder.copy(_passportID = value)
                    "iyr" -> passportBuilder.copy(_issueYear = value)
                    "eyr" -> passportBuilder.copy(_expirationYear = value)
                    "byr" -> passportBuilder.copy(_birthYear = value)
                    "ecl" -> passportBuilder.copy(_eyeColor = value)
                    "hcl" -> passportBuilder.copy(_hairColor = value)
                    "hgt" -> passportBuilder.copy(_height = value)
                    "cid" -> passportBuilder.copy(_countryID = value)
                    else -> throw RuntimeException("Unknown property: $key")
                }
            }
        }
    }

    fun isValid() = listOf(passportID, issueYear, expirationYear, birthYear, eyeColor, hairColor, height).all { it.isValid() }
}

interface Validatable {
    fun isValid(): Boolean
}

data class PassportId(private val stringVal: String?): Validatable {
    override fun isValid() = stringVal?.matches("\\d{9}".toRegex()) ?: false
}
data class BirthYear(private val stringVal: String?): ValidatableYear(stringVal, 1920, 2002)
data class IssueYear(private val stringVal: String?): ValidatableYear(stringVal, 2010, 2020)
data class ExpirationYear(private val stringVal: String?): ValidatableYear(stringVal, 2020, 2030)
data class EyeColor(private val stringVal: String?) : Validatable {
    private val validEyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    override fun isValid() = stringVal in validEyeColors
}
data class HairColor(private val stringVal: String?): Validatable {
    override fun isValid() = stringVal?.matches("#[0-9a-f]{6}".toRegex()) ?: false
}
sealed class Height(private val stringVal: String, private val minHeight: Int, private val maxHeight: Int): Validatable {

    override fun isValid(): Boolean {
        val measurement = stringVal.dropLast(2)
        return measurement
            .matches("(\\d)*".toRegex())
                && measurement.isNotBlank()
                && measurement.toInt() in minHeight..maxHeight
    }

    data class Inches(private val stringVal: String): Height(stringVal, 59, 76)
    data class Centimeters(private val stringVal: String): Height(stringVal, 150,193)
    object EmptyHeight: Height("",0,0) {
        override fun isValid() = false
    }
    companion object {
        fun of(stringVal: String?): Height {
            return when (stringVal?.takeLast(2)) {
                "in" -> Inches(stringVal)
                "cm" -> Centimeters(stringVal)
                else -> EmptyHeight
            }
        }
    }
}

open class ValidatableYear(private val stringVal: String?, val startYearIncl: Int, val endYearIncl: Int) : Validatable {
    override fun isValid() = stringVal?.isAYearBetween(startYearIncl, endYearIncl) ?: false
}


fun String.isAYearBetween(startYearIncl: Int, endYearIncl: Int) = this.matches("\\d{4}".toRegex()) && this.toInt() in startYearIncl..endYearIncl

fun <T> List<T?>.areFilledIn() = this.filterNotNull().size == this.size
