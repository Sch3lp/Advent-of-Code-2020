package be.swsb.aoc.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IntExtensionsTest {
    @Test
    internal fun `optional multiplication`() {
        assertThat(null as Int? * 2).isEqualTo(2)
        assertThat(2 * null as Int?).isEqualTo(2)
        assertThat(null as Int? * null as Int?).isNull()
        assertThat(2 * null as Int? * 3).isEqualTo(6)
    }
}