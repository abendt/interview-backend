package de.bringmeister.products

import de.bringmeister.domain.PriceUnit
import org.assertj.core.api.KotlinAssertions
import org.junit.Before
import org.junit.Test

class PriceRepositoryTest {

    val serviceUnderTest = PriceRepository()

    @Before
    fun setUp() {
        serviceUnderTest.loadAndNormalizePrices()
    }

    @Test
    fun priceListIsEmptyByDefault() {
        KotlinAssertions.assertThat(serviceUnderTest.findPricesBySku("doesNotExist")).isEmpty()
    }

    @Test
    fun canFindPricesBySku() {
        val result = serviceUnderTest.findPricesBySku("BA-01")

        KotlinAssertions.assertThat(result).extracting("id").containsOnly("BA-01")
        KotlinAssertions.assertThat(result).extracting("cents").contains(245, 1099)
        KotlinAssertions.assertThat(result).extracting("unit").contains(PriceUnit.PACKAGE, PriceUnit.PIECE)
    }

    @Test
    fun canFindPriceBySkuAndUnit() {
        KotlinAssertions.assertThat(serviceUnderTest.findPriceBySkuAndUnit("BA-01", PriceUnit.PIECE).cents).isEqualTo(245)
        KotlinAssertions.assertThat(serviceUnderTest.findPriceBySkuAndUnit("BA-01", PriceUnit.PACKAGE).cents).isEqualTo(1099)
    }

}